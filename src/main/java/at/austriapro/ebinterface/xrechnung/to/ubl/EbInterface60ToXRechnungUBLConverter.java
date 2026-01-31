/*
 * Copyright (c) 2019-2026 AUSTRIAPRO - www.austriapro.at
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

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.helger.diagnostics.error.list.ErrorList;
import com.helger.ebinterface.EEbInterfaceVersion;
import com.helger.ebinterface.v60.Ebi60InvoiceType;
import com.helger.jaxb.validation.WrappedCollectingValidationEventHandler;
import com.helger.ubl21.UBL21Marshaller;

import at.austriapro.ebinterface.ubl.to.EbInterface60ToInvoiceConverter;
import at.austriapro.ebinterface.xrechnung.EXRechnungVersion;
import at.austriapro.ebinterface.xrechnung.validator.XRechnungValidator;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Convert an ebInterface 6.0 invoice to an XRechnung UBL.
 *
 * @author Philip Helger
 */
public class EbInterface60ToXRechnungUBLConverter extends
                                                  AbstractEbInterfaceToXRechnungUBLConverter <EbInterface60ToXRechnungUBLConverter>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (EbInterface60ToXRechnungUBLConverter.class);
  private static final EEbInterfaceVersion VERSION = EEbInterfaceVersion.V60;
  private static final String VERSION_STR = "ebInterface " + VERSION.getVersion ().getAsStringMajorMinor ();

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
  public EbInterface60ToXRechnungUBLConverter (@NonNull final Locale aDisplayLocale,
                                               @NonNull final Locale aContentLocale,
                                               @NonNull final EXRechnungVersion eXRechnungVersion)
  {
    super (aDisplayLocale, aContentLocale, eXRechnungVersion);
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
  public InvoiceType convert (@NonNull final Ebi60InvoiceType aEbiInvoice, @NonNull final ErrorList aTransformErrorList)
  {
    // Convert ebInterface to UBL
    final InvoiceType aUBLInvoice = new EbInterface60ToInvoiceConverter (m_aDisplayLocale,
                                                                         m_aContentLocale).convertInvoice (aEbiInvoice);
    assert aUBLInvoice != null;

    // set XRechnung specific values
    modifyDefaultUBLInvoice (aUBLInvoice);

    // Check if UBL matches the XSD
    int nErrorsBefore = aTransformErrorList.getErrorCount ();
    int nWarnsBefore = countWarnings (aTransformErrorList);
    final Document aUBLDoc = UBL21Marshaller.invoice ()
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

    XRechnungValidator.validateXRechnung (m_eXRechnungVersion.getVESID_UBLInvoice (), aUBLDoc, aTransformErrorList);

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
