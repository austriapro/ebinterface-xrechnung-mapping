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
package at.austriapro.ebinterface.xrechnung;

import static org.junit.Assert.assertFalse;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.bdve.en16931.EN16931Validation;
import com.helger.bdve.execute.ValidationExecutionManager;
import com.helger.bdve.executorset.IValidationExecutorSet;
import com.helger.bdve.executorset.ValidationExecutorSetRegistry;
import com.helger.bdve.result.ValidationResultList;
import com.helger.bdve.source.ValidationSource;
import com.helger.bdve.xrechnung.XRechnungValidation;
import com.helger.commons.io.file.FileSystemIterator;
import com.helger.commons.io.file.IFileFilter;
import com.helger.commons.io.resource.FileSystemResource;

public final class ValidateTestFilesFuncTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (ValidateTestFilesFuncTest.class);

  private static final ValidationExecutorSetRegistry VES_REGISTRY = new ValidationExecutorSetRegistry ();
  static
  {
    EN16931Validation.initEN16931 (VES_REGISTRY);
    XRechnungValidation.initXRechnung (VES_REGISTRY);
  }

  @Test
  public void testXRechnungUBL ()
  {
    final IValidationExecutorSet aVES = VES_REGISTRY.getOfID (XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_121);
    final ValidationExecutionManager aVEM = aVES.createExecutionManager ();

    for (final File f : new FileSystemIterator (new File ("src/test/resources/xrechnung/ubl")).withFilter (IFileFilter.filenameEndsWith (".xml")))
    {
      LOGGER.info ("Validating " + f.getName ());

      // What to validate?
      final ValidationSource aValidationSource = ValidationSource.createXMLSource (new FileSystemResource (f));
      final ValidationResultList aValidationResult = aVEM.executeValidation (aValidationSource);
      assertFalse (aValidationResult.getAllErrors ().toString (), aValidationResult.containsAtLeastOneError ());
    }
  }

  @Test
  public void testXRechnungCII ()
  {
    final IValidationExecutorSet aVES = VES_REGISTRY.getOfID (XRechnungValidation.VID_XRECHNUNG_CII_121);
    final ValidationExecutionManager aVEM = aVES.createExecutionManager ();

    for (final File f : new FileSystemIterator (new File ("src/test/resources/xrechnung/cii")).withFilter (IFileFilter.filenameEndsWith (".xml")))
    {
      LOGGER.info ("Validating " + f.getName ());

      // What to validate?
      final ValidationSource aValidationSource = ValidationSource.createXMLSource (new FileSystemResource (f));
      final ValidationResultList aValidationResult = aVEM.executeValidation (aValidationSource);
      assertFalse (aValidationResult.getAllErrors ().toString (), aValidationResult.containsAtLeastOneError ());
    }
  }
}
