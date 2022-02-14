/*
 * Copyright (c) 2019-2022 AUSTRIAPRO - www.austriapro.at
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.austriapro.ebinterface.xrechnung.to.ubl;

import java.math.BigDecimal;
import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.commons.datetime.XMLOffsetDate;
import com.helger.commons.math.MathHelper;

import at.austriapro.ebinterface.xrechnung.EXRechnungVersion;
import at.austriapro.ebinterface.xrechnung.to.AbstractEbInterfaceToXRechnungConverter;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyLegalEntityType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PaymentTermsType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DocumentTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.NoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Base class for converting an ebInterface invoice to an XRechnung UBL.
 *
 * @author Philip Helger
 */
public abstract class AbstractEbInterfaceToXRechnungUBLConverter extends AbstractEbInterfaceToXRechnungConverter
{
  /**
   * Constructor.
   *
   * @param aDisplayLocale
   *        The display locale, e.g. used for the error message. May not be
   *        <code>null</code>.
   * @param aContentLocale
   *        The content locale of the invoice. May not be <code>null</code>.
   * @param eXRechnungVersion
   *        The target XRechnung version. May not be <code>null</code>.
   */
  public AbstractEbInterfaceToXRechnungUBLConverter (@Nonnull final Locale aDisplayLocale,
                                                     @Nonnull final Locale aContentLocale,
                                                     @Nonnull final EXRechnungVersion eXRechnungVersion)
  {
    super (aDisplayLocale, aContentLocale, eXRechnungVersion);
  }

  public void modifyDefaultUBLInvoice (@Nonnull final InvoiceType aInvoice)
  {
    if (aInvoice.getCustomizationID () == null)
      aInvoice.setCustomizationID (m_eXRechnungVersion.getCustomizationID ());

    if (aInvoice.getBuyerReference () == null)
      if (aInvoice.getOrderReference () != null)
        aInvoice.setBuyerReference (aInvoice.getOrderReference ().getIDValue ());

    // This became an error in EN-16931
    // 130 is allowed for Invoice
    // 130, 50 is allowed for CreditNote
    for (final DocumentReferenceType aDocRef : aInvoice.getAdditionalDocumentReference ())
      if (!"130".equals (aDocRef.getDocumentTypeCodeValue ()))
        aDocRef.setDocumentTypeCode ((DocumentTypeCodeType) null);

    final SupplierPartyType aSupplier = aInvoice.getAccountingSupplierParty ();
    if (aSupplier != null)
    {
      // Party/PartyLegalEntity
      final PartyType aParty = aSupplier.getParty ();
      if (aParty != null)
      {
        if (aParty.hasNoPartyLegalEntityEntries ())
        {
          if (aParty.hasPartyNameEntries ())
          {
            final PartyNameType aPN = aParty.getPartyNameAtIndex (0);
            final PartyLegalEntityType aPLE = new PartyLegalEntityType ();
            aPLE.setRegistrationName (aPN.getNameValue ());
            aParty.addPartyLegalEntity (aPLE);
          }
        }
      }
    }

    final CustomerPartyType aCustomer = aInvoice.getAccountingCustomerParty ();
    if (aCustomer != null)
    {
      // Party/PartyLegalEntity
      final PartyType aParty = aCustomer.getParty ();
      if (aParty != null)
      {
        if (aParty.hasNoPartyLegalEntityEntries ())
        {
          if (aParty.hasPartyNameEntries ())
          {
            final PartyNameType aPN = aParty.getPartyNameAtIndex (0);
            final PartyLegalEntityType aPLE = new PartyLegalEntityType ();
            aPLE.setRegistrationName (aPN.getNameValue ());
            aParty.addPartyLegalEntity (aPLE);
          }
        }
      }
    }

    for (final InvoiceLineType aInvoiceLine : aInvoice.getInvoiceLine ())
    {
      final ItemType aItem = aInvoiceLine.getItem ();
      if (aItem != null)
      {
        // Item/Name from Item/Description[0]
        if (aItem.getName () == null)
        {
          if (aItem.hasDescriptionEntries ())
            aItem.setName (aItem.getDescriptionAtIndex (0).getValue ());
        }
      }

      // If price is negative, switch sign on price and quantity
      final PriceType aPrice = aInvoiceLine.getPrice ();
      if (aPrice != null)
      {
        final BigDecimal aPriceAmount = aPrice.getPriceAmountValue ();
        if (aPriceAmount != null && MathHelper.isLT0 (aPriceAmount))
        {
          aPrice.setPriceAmount (aPriceAmount.negate ());
          aInvoiceLine.setInvoicedQuantity (aInvoiceLine.getInvoicedQuantityValue ().negate ());
        }
      }
    }

    // Work around for error "BR-CO-25" (error in CEN validation artefacts
    // 1.3.0; see issue #84) that was introduced when updating to XRechnung
    // rules 1.2.2
    if (MathHelper.isGT0 (aInvoice.getLegalMonetaryTotal ().getPayableAmountValue ()))
    {
      if (!aInvoice.getPaymentTerms ().isEmpty ())
      {
        final PaymentTermsType aPT = aInvoice.getPaymentTermsAtIndex (0);
        if (aPT.getNote ().isEmpty ())
        {
          // Ensure that a note is present, to work around the wrong Schematron
          final XMLOffsetDate aDueDate = aPT.getPaymentDueDateValue ();
          if (aDueDate != null)
            aPT.addNote (new NoteType ("Due at " + aDueDate.toString ()));
        }
      }
    }
  }
}
