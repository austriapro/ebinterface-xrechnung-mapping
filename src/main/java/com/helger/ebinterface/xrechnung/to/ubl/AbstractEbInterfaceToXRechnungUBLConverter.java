/**
 * Copyright (c) 2019 AUSTRIAPRO - www.austriapro.at
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
package com.helger.ebinterface.xrechnung.to.ubl;

import java.math.BigDecimal;
import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.commons.math.MathHelper;
import com.helger.ebinterface.xrechnung.to.AbstractEbInterfaceToXRechnungConverter;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyLegalEntityType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Base class for converting an ebInterface invoice to an XRechnung UBL.
 *
 * @author Philip Helger
 */
public abstract class AbstractEbInterfaceToXRechnungUBLConverter extends AbstractEbInterfaceToXRechnungConverter
{
  public AbstractEbInterfaceToXRechnungUBLConverter (@Nonnull final Locale aDisplayLocale,
                                                     @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  public static void modifyDefaultUBLInvoice (@Nonnull final InvoiceType aInvoice)
  {
    if (aInvoice.getCustomizationID () == null)
      aInvoice.setCustomizationID ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2");

    if (aInvoice.getBuyerReference () == null)
      if (aInvoice.getOrderReference () != null)
        aInvoice.setBuyerReference (aInvoice.getOrderReference ().getIDValue ());

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
  }
}