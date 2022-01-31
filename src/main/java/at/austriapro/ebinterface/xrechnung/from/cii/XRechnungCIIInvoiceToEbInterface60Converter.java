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
package at.austriapro.ebinterface.xrechnung.from.cii;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.error.list.ErrorList;
import com.helger.ebinterface.v60.Ebi60InvoiceType;

import at.austriapro.ebinterface.ubl.from.invoice.ICustomInvoiceToEbInterface60Converter;
import at.austriapro.ebinterface.xrechnung.from.ubl.XRechnungUBLInvoiceToEbInterface60Converter;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import un.unece.uncefact.data.standard.crossindustryinvoice._100.CrossIndustryInvoiceType;

/**
 * Convert XRechnung CII to ebInterface 6.0
 *
 * @author Philip Helger
 */
public class XRechnungCIIInvoiceToEbInterface60Converter extends AbstractXRechnungCIIToEbInterfaceConverter
{
  private ICustomInvoiceToEbInterface60Converter m_aCustomizer;

  public XRechnungCIIInvoiceToEbInterface60Converter (@Nonnull final Locale aDisplayLocale, @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  @Nonnull
  public XRechnungCIIInvoiceToEbInterface60Converter setCustomizer (@Nullable final ICustomInvoiceToEbInterface60Converter aCustomizer)
  {
    m_aCustomizer = aCustomizer;
    return this;
  }

  @Nullable
  public Ebi60InvoiceType convert (@Nonnull final CrossIndustryInvoiceType aCIIInvoice, @Nonnull final ErrorList aTransformErrorList)
  {
    final InvoiceType aUBLInvoice = convertCIIToUBL (aCIIInvoice, aTransformErrorList);
    if (aUBLInvoice == null)
      return null;

    aTransformErrorList.clear ();
    return new XRechnungUBLInvoiceToEbInterface60Converter (m_aDisplayLocale, m_aContentLocale).setCustomizer (m_aCustomizer)
                                                                                               .convert (aUBLInvoice, aTransformErrorList);
  }
}
