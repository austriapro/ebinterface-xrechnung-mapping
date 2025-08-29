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
package at.austriapro.ebinterface.xrechnung.from.cii;

import java.util.Locale;

import com.helger.diagnostics.error.list.ErrorList;
import com.helger.ebinterface.v41.Ebi41InvoiceType;

import at.austriapro.ebinterface.ubl.from.invoice.ICustomInvoiceToEbInterface41Converter;
import at.austriapro.ebinterface.xrechnung.from.ubl.XRechnungUBLInvoiceToEbInterface41Converter;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import un.unece.uncefact.data.standard.crossindustryinvoice._100.CrossIndustryInvoiceType;

/**
 * Convert XRechnung CII to ebInterface 4.1
 *
 * @author Philip Helger
 */
public class XRechnungCIIInvoiceToEbInterface41Converter extends AbstractXRechnungCIIToEbInterfaceConverter
{
  private ICustomInvoiceToEbInterface41Converter m_aCustomizer;

  public XRechnungCIIInvoiceToEbInterface41Converter (@Nonnull final Locale aDisplayLocale, @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  @Nonnull
  public XRechnungCIIInvoiceToEbInterface41Converter setCustomizer (@Nullable final ICustomInvoiceToEbInterface41Converter aCustomizer)
  {
    m_aCustomizer = aCustomizer;
    return this;
  }

  @Nullable
  public Ebi41InvoiceType convert (@Nonnull final CrossIndustryInvoiceType aCIIInvoice, @Nonnull final ErrorList aTransformErrorList)
  {
    final InvoiceType aUBLInvoice = convertCIIToUBL (aCIIInvoice, aTransformErrorList);
    if (aUBLInvoice == null)
      return null;

    aTransformErrorList.clear ();
    return new XRechnungUBLInvoiceToEbInterface41Converter (m_aDisplayLocale, m_aContentLocale).setCustomizer (m_aCustomizer)
                                                                                               .convert (aUBLInvoice, aTransformErrorList);
  }
}
