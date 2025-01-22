/*
 * Copyright (c) 2019-2025 AUSTRIAPRO - www.austriapro.at
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
import javax.annotation.Nullable;

import com.helger.commons.datetime.XMLOffsetDate;
import com.helger.commons.math.MathHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.traits.IGenericImplTrait;

import at.austriapro.ebinterface.ubl.AbstractConverter;
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
 * @param <IMPLTYPE>
 *        The implementation type.
 */
public abstract class AbstractEbInterfaceToXRechnungUBLConverter <IMPLTYPE extends AbstractEbInterfaceToXRechnungUBLConverter <IMPLTYPE>>
                                                                 extends
                                                                 AbstractEbInterfaceToXRechnungConverter implements
                                                                 IGenericImplTrait <IMPLTYPE>
{
  private String m_sSupplierEndpointIDScheme;
  private String m_sSupplierEndpointID;
  private String m_sCustomerEndpointIDScheme;
  private String m_sCustomerEndpointID;

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
  protected AbstractEbInterfaceToXRechnungUBLConverter (@Nonnull final Locale aDisplayLocale,
                                                        @Nonnull final Locale aContentLocale,
                                                        @Nonnull final EXRechnungVersion eXRechnungVersion)
  {
    super (aDisplayLocale, aContentLocale, eXRechnungVersion);
  }

  /**
   * @return The supplier EndpointID schemeID. May be <code>null</code>.
   * @since 2.1.1
   */
  @Nullable
  public final String getSupplierEndpointIDScheme ()
  {
    return m_sSupplierEndpointIDScheme;
  }

  /**
   * Set the supplier EndpointID schemeID. This is required for XRechnung 3.0
   * onwards.
   *
   * @param s
   *        Endpoint ID scheme ID
   * @return this for chaining
   * @since 2.1.1
   */
  @Nonnull
  public final IMPLTYPE setSupplierEndpointIDScheme (@Nullable final String s)
  {
    m_sSupplierEndpointIDScheme = s;
    return thisAsT ();
  }

  /**
   * @return The supplier EndpointID. May be <code>null</code>.
   * @since 2.1.1
   */
  @Nullable
  public final String getSupplierEndpointID ()
  {
    return m_sSupplierEndpointID;
  }

  /**
   * Set the supplier EndpointID. This is required for XRechnung 3.0 onwards.
   *
   * @param s
   *        Endpoint ID
   * @return this for chaining
   * @since 2.1.1
   */
  @Nonnull
  public final IMPLTYPE setSupplierEndpointID (@Nullable final String s)
  {
    m_sSupplierEndpointID = s;
    return thisAsT ();
  }

  /**
   * @return The customer EndpointID schemeID. May be <code>null</code>.
   * @since 2.1.1
   */
  @Nullable
  public final String getCustomerEndpointIDScheme ()
  {
    return m_sCustomerEndpointIDScheme;
  }

  /**
   * Set the customer EndpointID schemeID. This is required for XRechnung 3.0
   * onwards.
   *
   * @param s
   *        Endpoint ID scheme ID
   * @return this for chaining
   * @since 2.1.1
   */
  @Nonnull
  public final IMPLTYPE setCustomerEndpointIDScheme (@Nullable final String s)
  {
    m_sCustomerEndpointIDScheme = s;
    return thisAsT ();
  }

  /**
   * @return The customer EndpointID. May be <code>null</code>.
   * @since 2.1.1
   */
  @Nullable
  public final String getCustomerEndpointID ()
  {
    return m_sCustomerEndpointID;
  }

  /**
   * Set the customer EndpointID. This is required for XRechnung 3.0 onwards.
   *
   * @param s
   *        Endpoint ID
   * @return this for chaining
   * @since 2.1.1
   */
  @Nonnull
  public final IMPLTYPE setCustomerEndpointID (@Nullable final String s)
  {
    m_sCustomerEndpointID = s;
    return thisAsT ();
  }

  public void modifyDefaultUBLInvoice (@Nonnull final InvoiceType aInvoice)
  {
    if (aInvoice.getCustomizationID () == null)
      aInvoice.setCustomizationID (m_eXRechnungVersion.getCustomizationID ());

    if (aInvoice.getProfileID () == null)
      aInvoice.setProfileID (m_eXRechnungVersion.getProfileID ());

    if (AbstractConverter.INVOICE_TYPE_CODE_FINAL_PAYMENT.equals (aInvoice.getInvoiceTypeCodeValue ()))
    {
      // 218 is not allowed in the EN -> change to 380
      aInvoice.setInvoiceTypeCode (AbstractConverter.INVOICE_TYPE_CODE_INVOICE);
    }

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

        if (StringHelper.hasNoText (aParty.getEndpointIDValue ()))
          aParty.setEndpointID (m_sSupplierEndpointID).setSchemeID (m_sSupplierEndpointIDScheme);
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

        if (StringHelper.hasNoText (aParty.getEndpointIDValue ()))
          aParty.setEndpointID (m_sCustomerEndpointID).setSchemeID (m_sCustomerEndpointIDScheme);
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
