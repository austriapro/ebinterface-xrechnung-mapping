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
package com.helger.ebinterface.xrechnung.from.cii;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.error.list.ErrorList;
import com.helger.ebinterface.v40.Ebi40InvoiceType;
import com.helger.ebinterface.xrechnung.from.ubl.XRechnungUBLInvoiceToEbInterface40Converter;

import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import un.unece.uncefact.data.standard.crossindustryinvoice._100.CrossIndustryInvoiceType;

/**
 * Convert XRechnung CII to ebInterface 4.0
 *
 * @author Philip Helger
 */
public class XRechnungCIIInvoiceToEbInterface40Converter extends AbstractXRechnungCIIToEbInterfaceConverter
{
  public XRechnungCIIInvoiceToEbInterface40Converter (@Nonnull final Locale aDisplayLocale,
                                                      @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  @Nullable
  public Ebi40InvoiceType convert (@Nonnull final CrossIndustryInvoiceType aCIIInvoice,
                                   @Nonnull final ErrorList aTransformErrorList)
  {
    final InvoiceType aUBLInvoice = convertCIIToUBL (aCIIInvoice, aTransformErrorList);
    if (aUBLInvoice == null)
      return null;

    aTransformErrorList.clear ();
    return new XRechnungUBLInvoiceToEbInterface40Converter (m_aDisplayLocale,
                                                            m_aContentLocale).convert (aUBLInvoice,
                                                                                       aTransformErrorList);
  }
}
