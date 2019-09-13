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
package com.helger.ebinterface.xrechnung.from;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.error.list.ErrorList;
import com.helger.ebinterface.EEbInterfaceVersion;
import com.helger.ebinterface.ubl.from.invoice.ICustomInvoiceToEbInterface43Converter;
import com.helger.ebinterface.ubl.from.invoice.InvoiceToEbInterface43Converter;
import com.helger.ebinterface.v43.Ebi43InvoiceType;

import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Convert XRechnung UBL to ebInterface 4.3
 *
 * @author Philip Helger
 */
public class XRechnungUBLInvoiceToEbInterface43Converter extends AbstractXRechnungUBLToEbInterfaceConverter
{
  private static final Logger LOGGER = LoggerFactory.getLogger (XRechnungUBLInvoiceToEbInterface43Converter.class);
  private static final EEbInterfaceVersion VERSION = EEbInterfaceVersion.V43;
  private static final String VERSION_STR = "ebInterface " + VERSION.getVersion ().getAsStringMajorMinor ();

  private final ICustomInvoiceToEbInterface43Converter m_aCustomizer = null;

  public XRechnungUBLInvoiceToEbInterface43Converter (@Nonnull final Locale aDisplayLocale,
                                                      @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  @Nullable
  public Ebi43InvoiceType convert (@Nonnull final InvoiceType aUBLInvoice, @Nonnull final ErrorList aTransformErrorList)
  {
    final int nErrorsBefore = aTransformErrorList.getErrorCount ();
    final int nWarnsBefore = countWarnings (aTransformErrorList);
    final Ebi43InvoiceType ret = new InvoiceToEbInterface43Converter (m_aDisplayLocale,
                                                                      m_aContentLocale,
                                                                      m_aToEbiSettings).setCustomizer (m_aCustomizer)
                                                                                       .convertToEbInterface (aUBLInvoice,
                                                                                                              aTransformErrorList);
    final int nErrorsAfters = aTransformErrorList.getErrorCount ();
    final int nWarnsAfters = countWarnings (aTransformErrorList);
    if (nWarnsAfters > nWarnsBefore)
      LOGGER.warn ("The conversion from UBL to " + VERSION_STR + " found " + warningText (nWarnsAfters - nWarnsBefore));
    if (nErrorsAfters > nErrorsBefore)
    {
      LOGGER.warn ("The conversion from UBL to " + VERSION_STR + " found " + errorText (nErrorsAfters - nErrorsBefore));
      return null;
    }

    return ret;
  }
}
