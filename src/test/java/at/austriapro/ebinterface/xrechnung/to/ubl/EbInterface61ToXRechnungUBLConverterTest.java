/*
 * Copyright (c) 2019-2023 AUSTRIAPRO - www.austriapro.at
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.error.IError;
import com.helger.commons.error.list.ErrorList;
import com.helger.commons.io.file.FileSystemIterator;
import com.helger.commons.io.file.IFileFilter;
import com.helger.commons.locale.LocaleCache;
import com.helger.ebinterface.EbInterface61Marshaller;
import com.helger.ebinterface.v61.Ebi61InvoiceType;
import com.helger.ubl21.UBL21Marshaller;

import at.austriapro.ebinterface.xrechnung.EXRechnungVersion;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Test class for class {@link EbInterface61ToXRechnungUBLConverter}.
 *
 * @author Philip Helger
 */
public final class EbInterface61ToXRechnungUBLConverterTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (EbInterface61ToXRechnungUBLConverterTest.class);
  private static final Locale LOC = LocaleCache.getInstance ().getLocale ("de", "AT");

  private static final ICommonsSet <String> IGNORE_FILES = new CommonsHashSet <> ("ebinterface_6p1_sample_dokumentation.xml",
                                                                                  "ebinterface_6p1_sample_ecosio.xml",
                                                                                  "ebinterface_6p1_sample_more_consistent.xml",
                                                                                  "ebinterface_6p1_sample_ph2-below-the-line.xml");

  @Test
  public void testBasic ()
  {
    for (final EXRechnungVersion eXRechnungVersion : EXRechnungVersion.values ())
    {
      final EbInterface61ToXRechnungUBLConverter aToXRechnung = new EbInterface61ToXRechnungUBLConverter (LOC,
                                                                                                          LOC,
                                                                                                          eXRechnungVersion);

      for (final File aFile : new FileSystemIterator (new File ("src/test/resources/external/ebinterface/ebi61")).withFilter (IFileFilter.filenameEndsWith (".xml")))
        if (!IGNORE_FILES.contains (aFile.getName ()))
        {
          LOGGER.info ("Reading '" + aFile.getName () + "' for conversion to " + eXRechnungVersion);

          final Ebi61InvoiceType aEbi = new EbInterface61Marshaller ().read (aFile);
          assertNotNull (aEbi);

          // To UBL
          final ErrorList aErrorList = new ErrorList ();
          final InvoiceType aInvoice = aToXRechnung.convert (aEbi, aErrorList);
          assertNotNull (aInvoice);

          if (aErrorList.containsAtLeastOneError ())
            LOGGER.info (UBL21Marshaller.invoice ().setFormattedOutput (true).getAsString (aInvoice));

          aErrorList.findAll (IError::isError, x -> LOGGER.info (x.getAsString (LOC)));
          assertTrue (aErrorList.containsNoError ());
        }
    }
  }
}
