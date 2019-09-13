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

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.helger.commons.error.list.ErrorList;
import com.helger.ebinterface.EEbInterfaceVersion;
import com.helger.ebinterface.ubl.to.EbInterface40ToInvoiceConverter;
import com.helger.ebinterface.v40.Ebi40InvoiceType;
import com.helger.ebinterface.xrechnung.validator.XRechnungValidator;
import com.helger.jaxb.validation.WrappedCollectingValidationEventHandler;
import com.helger.ubl21.UBL21Writer;

import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Convert an ebInterface 4.0 invoice to an XRechnung UBL.
 *
 * @author Philip Helger
 */
public class EbInterface40ToXRechnungUBLConverter extends AbstractEbInterfaceToXRechnungUBLConverter
{
  private static final Logger LOGGER = LoggerFactory.getLogger (EbInterface40ToXRechnungUBLConverter.class);
  private static final EEbInterfaceVersion VERSION = EEbInterfaceVersion.V40;
  private static final String VERSION_STR = "ebInterface " + VERSION.getVersion ().getAsStringMajorMinor ();

  public EbInterface40ToXRechnungUBLConverter (@Nonnull final Locale aDisplayLocale,
                                               @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
  }

  /**
   * Convert the passed ebInterface invoice to an XRechnung. This converts the
   * ebInterface first to generic UBL, than adds some XRechnung specific
   * elements and finally validates the XRechnung according to the official
   * Schematrons.
   *
   * @param aEbiInvoice
   *        The ebInterface invoice to be converted. May not be
   *        <code>null</code>.
   * @param aTransformErrorList
   *        The error list that will contain the errors afterwards. May not be
   *        <code>null</code>.
   * @return <code>null</code> only if the basic conversion to UBL fails.
   */
  @Nullable
  public InvoiceType convert (@Nonnull final Ebi40InvoiceType aEbiInvoice, @Nonnull final ErrorList aTransformErrorList)
  {
    // Convert ebInterface to UBL
    final InvoiceType aUBLInvoice = new EbInterface40ToInvoiceConverter (m_aDisplayLocale,
                                                                         m_aContentLocale).convertInvoice (aEbiInvoice);
    assert aUBLInvoice != null;

    // set XRechnung specific values
    modifyDefaultUBLInvoice (aUBLInvoice);

    // Check if UBL matches the XSD
    int nErrorsBefore = aTransformErrorList.getErrorCount ();
    int nWarnsBefore = countWarnings (aTransformErrorList);
    final Document aUBLDoc = UBL21Writer.invoice ()
                                        .setValidationEventHandler (new WrappedCollectingValidationEventHandler (aTransformErrorList))
                                        .getAsDocument (aUBLInvoice);
    if (aUBLDoc == null)
      return null;

    int nErrorsAfters = aTransformErrorList.getErrorCount ();
    int nWarnsAfters = countWarnings (aTransformErrorList);
    if (nWarnsAfters > nWarnsBefore)
      LOGGER.warn ("The conversion from " + VERSION_STR + " to UBL found " + warningText (nWarnsAfters - nWarnsBefore));
    if (nErrorsAfters > nErrorsBefore)
    {
      LOGGER.warn ("The conversion from " + VERSION_STR + " to UBL found " + errorText (nErrorsAfters - nErrorsBefore));
      return null;
    }

    // Validate the XRechnung to the Schematron rules
    nErrorsBefore = nErrorsAfters;
    nWarnsBefore = nWarnsAfters;

    XRechnungValidator.validateXRechnungUBLInvoice (aUBLDoc, aTransformErrorList);

    nErrorsAfters = aTransformErrorList.getErrorCount ();
    nWarnsAfters = countWarnings (aTransformErrorList);
    if (false)
    {
      // A lot of warnings are "unexpected element"
      if (nWarnsAfters > nWarnsBefore)
        LOGGER.warn ("The validation of the XRechnung found " + warningText (nWarnsAfters - nWarnsBefore));
    }
    if (nErrorsAfters > nErrorsBefore)
      LOGGER.warn ("The validation of the XRechnung found " + errorText (nErrorsAfters - nErrorsBefore));

    return aUBLInvoice;
  }
}