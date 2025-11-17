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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.diagnostics.error.list.ErrorList;
import com.helger.ebinterface.v42.Ebi42InvoiceType;

import at.austriapro.ebinterface.ubl.from.invoice.ICustomInvoiceToEbInterface42Converter;
import at.austriapro.ebinterface.xrechnung.from.ubl.XRechnungUBLInvoiceToEbInterface42Converter;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import un.unece.uncefact.data.standard.crossindustryinvoice._100.CrossIndustryInvoiceType;

/**
 * Convert XRechnung CII to ebInterface 4.2
 *
 * @author Philip Helger
 */
public class XRechnungCIIInvoiceToEbInterface42Converter extends AbstractXRechnungCIIToEbInterfaceConverter
{
  private ICustomInvoiceToEbInterface42Converter m_aCustomizer;

  public XRechnungCIIInvoiceToEbInterface42Converter (@NonNull final Locale aDisplayLocale, @NonNull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  @NonNull
  public XRechnungCIIInvoiceToEbInterface42Converter setCustomizer (@Nullable final ICustomInvoiceToEbInterface42Converter aCustomizer)
  {
    m_aCustomizer = aCustomizer;
    return this;
  }

  @Nullable
  public Ebi42InvoiceType convert (@NonNull final CrossIndustryInvoiceType aCIIInvoice, @NonNull final ErrorList aTransformErrorList)
  {
    final InvoiceType aUBLInvoice = convertCIIToUBL (aCIIInvoice, aTransformErrorList);
    if (aUBLInvoice == null)
      return null;

    aTransformErrorList.clear ();
    return new XRechnungUBLInvoiceToEbInterface42Converter (m_aDisplayLocale, m_aContentLocale).setCustomizer (m_aCustomizer)
                                                                                               .convert (aUBLInvoice, aTransformErrorList);
  }
}
