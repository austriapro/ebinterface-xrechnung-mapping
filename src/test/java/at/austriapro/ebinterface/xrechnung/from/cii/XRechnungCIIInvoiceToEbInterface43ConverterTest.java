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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.cii.d16b.CIID16BCrossIndustryInvoiceTypeMarshaller;
import com.helger.commons.error.list.ErrorList;
import com.helger.commons.error.list.IErrorList;
import com.helger.commons.io.file.FileSystemIterator;
import com.helger.commons.io.file.IFileFilter;
import com.helger.ebinterface.EbInterface43Marshaller;
import com.helger.ebinterface.v43.Ebi43InvoiceType;

import un.unece.uncefact.data.standard.crossindustryinvoice._100.CrossIndustryInvoiceType;

public final class XRechnungCIIInvoiceToEbInterface43ConverterTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (XRechnungCIIInvoiceToEbInterface43ConverterTest.class);
  private static final Locale LOC = Locale.GERMAN;

  @Test
  public void testXRechnung122 ()
  {
    for (final File aFile : new FileSystemIterator (new File ("src/test/resources/external/xrechnung/122/cii")).withFilter (IFileFilter.filenameEndsWith (".xml")))
    {
      LOGGER.info ("Reading '" + aFile.getName () + "'");

      // Read as CII
      final CrossIndustryInvoiceType aCIIInvoice = new CIID16BCrossIndustryInvoiceTypeMarshaller ().read (aFile);
      assertNotNull (aCIIInvoice);

      // Convert to ebInterface
      final ErrorList aTransformErrorList = new ErrorList ();
      final Ebi43InvoiceType aEbi = new XRechnungCIIInvoiceToEbInterface43Converter (LOC,
                                                                                     LOC).convert (aCIIInvoice,
                                                                                                   aTransformErrorList);
      assertTrue ("Errors:  " + aTransformErrorList.getAllErrors ().toString (),
                  aTransformErrorList.containsNoError ());
      assertNotNull (aEbi);

      // Validate ebInterface
      final IErrorList aValidationErrors = new EbInterface43Marshaller ().validate (aEbi);
      assertNotNull (aValidationErrors);
      assertTrue (aValidationErrors.getAllErrors ().toString (), aValidationErrors.containsNoError ());
    }
  }
}
