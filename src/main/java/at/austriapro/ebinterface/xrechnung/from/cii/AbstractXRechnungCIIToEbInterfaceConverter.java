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

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.error.list.ErrorList;
import com.helger.en16931.cii2ubl.CIIToUBL21Converter;
import com.helger.en16931.cii2ubl.EUBLCreationMode;

import at.austriapro.ebinterface.xrechnung.from.AbstractXRechnungToEbInterfaceConverter;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import un.unece.uncefact.data.standard.crossindustryinvoice._100.CrossIndustryInvoiceType;

/**
 * Abstract base class to convert XRechnung CII to ebInterface,
 *
 * @author Philip Helger
 */
public abstract class AbstractXRechnungCIIToEbInterfaceConverter extends AbstractXRechnungToEbInterfaceConverter
{
  private static final Logger LOGGER = LoggerFactory.getLogger (AbstractXRechnungCIIToEbInterfaceConverter.class);

  protected AbstractXRechnungCIIToEbInterfaceConverter (@Nonnull final Locale aDisplayLocale,
                                                        @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  @Nullable
  protected InvoiceType convertCIIToUBL (@Nonnull final CrossIndustryInvoiceType aCIIInvoice,
                                         @Nonnull final ErrorList aErrorList)
  {
    final CIIToUBL21Converter aConverter = new CIIToUBL21Converter ();
    aConverter.setUBLCreationMode (EUBLCreationMode.INVOICE);

    final int nErrorsBefore = aErrorList.getErrorCount ();
    final int nWarnsBefore = countWarnings (aErrorList);

    // Main conversion
    final Serializable aUBLInvoice = aConverter.convertCIItoUBL (aCIIInvoice, aErrorList);

    final int nErrorsAfters = aErrorList.getErrorCount ();
    final int nWarnsAfters = countWarnings (aErrorList);
    if (nWarnsAfters > nWarnsBefore)
      LOGGER.warn ("The conversion from CII to UBL found " + warningText (nWarnsAfters - nWarnsBefore));
    if (nErrorsAfters > nErrorsBefore)
    {
      LOGGER.warn ("The conversion from CII to UBL found " + errorText (nErrorsAfters - nErrorsBefore));
      return null;
    }

    return (InvoiceType) aUBLInvoice;
  }
}
