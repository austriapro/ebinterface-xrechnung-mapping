/**
 * Copyright (c) 2019-2020 AUSTRIAPRO - www.austriapro.at
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
import com.helger.ebinterface.builder.EbInterfaceReader;
import com.helger.ebinterface.v50.Ebi50InvoiceType;
import com.helger.ubl21.UBL21Writer;

import at.austriapro.ebinterface.xrechnung.to.ubl.EbInterface50ToXRechnungUBLConverter;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Test class for class {@link EbInterface50ToXRechnungUBLConverter}.
 *
 * @author Philip Helger
 */
public final class EbInterface50ToXRechnungUBLConverterTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (EbInterface50ToXRechnungUBLConverterTest.class);
  private static final Locale LOC = LocaleCache.getInstance ().getLocale ("de", "AT");

  private static final ICommonsSet <String> IGNORE_FILES = new CommonsHashSet <> ("ebinterface_5p0_sample_ecosio.xml");

  @Test
  public void testBasic ()
  {
    final EbInterface50ToXRechnungUBLConverter aToXRechnung = new EbInterface50ToXRechnungUBLConverter (LOC, LOC);

    for (final File aFile : new FileSystemIterator (new File ("src/test/resources/ebinterface/ebi50")).withFilter (IFileFilter.filenameEndsWith (".xml")))
      if (!IGNORE_FILES.contains (aFile.getName ()))
      {
        LOGGER.info ("Reading '" + aFile.getName () + "'");

        final Ebi50InvoiceType aEbi = EbInterfaceReader.ebInterface50 ().read (aFile);
        assertNotNull (aEbi);

        // To UBL
        final ErrorList aErrorList = new ErrorList ();
        final InvoiceType aInvoice = aToXRechnung.convert (aEbi, aErrorList);
        assertNotNull (aInvoice);

        if (aErrorList.containsAtLeastOneError ())
          LOGGER.info (UBL21Writer.invoice ().setFormattedOutput (true).getAsString (aInvoice));

        aErrorList.forEach (x -> LOGGER.info (x.getAsString (LOC)), IError::isError);
        assertTrue (aErrorList.containsNoError ());
      }
  }
}
