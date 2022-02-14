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
package at.austriapro.ebinterface.xrechnung.from.ubl;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.error.list.ErrorList;
import com.helger.ebinterface.EEbInterfaceVersion;
import com.helger.ebinterface.v41.Ebi41InvoiceType;

import at.austriapro.ebinterface.ubl.from.invoice.ICustomInvoiceToEbInterface41Converter;
import at.austriapro.ebinterface.ubl.from.invoice.InvoiceToEbInterface41Converter;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Convert XRechnung UBL to ebInterface 4.1
 *
 * @author Philip Helger
 */
public class XRechnungUBLInvoiceToEbInterface41Converter extends AbstractXRechnungUBLToEbInterfaceConverter
{
  private static final Logger LOGGER = LoggerFactory.getLogger (XRechnungUBLInvoiceToEbInterface41Converter.class);
  private static final EEbInterfaceVersion VERSION = EEbInterfaceVersion.V41;
  private static final String VERSION_STR = "ebInterface " + VERSION.getVersion ().getAsStringMajorMinor ();

  private ICustomInvoiceToEbInterface41Converter m_aCustomizer;

  public XRechnungUBLInvoiceToEbInterface41Converter (@Nonnull final Locale aDisplayLocale, @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  @Nonnull
  public XRechnungUBLInvoiceToEbInterface41Converter setCustomizer (@Nullable final ICustomInvoiceToEbInterface41Converter aCustomizer)
  {
    m_aCustomizer = aCustomizer;
    return this;
  }

  @Nullable
  public Ebi41InvoiceType convert (@Nonnull final InvoiceType aUBLInvoice, @Nonnull final ErrorList aTransformErrorList)
  {
    final int nErrorsBefore = aTransformErrorList.getErrorCount ();
    final int nWarnsBefore = countWarnings (aTransformErrorList);
    final Ebi41InvoiceType ret = new InvoiceToEbInterface41Converter (m_aDisplayLocale,
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
