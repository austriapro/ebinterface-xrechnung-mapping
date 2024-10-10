/*
 * Copyright (c) 2019-2024 AUSTRIAPRO - www.austriapro.at
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
package at.austriapro.ebinterface.xrechnung.validator;

import javax.annotation.Nonnull;

import org.w3c.dom.Node;

import com.helger.commons.error.list.ErrorList;
import com.helger.diver.api.coord.DVRCoordinate;
import com.helger.phive.api.execute.ValidationExecutionManager;
import com.helger.phive.api.executorset.IValidationExecutorSet;
import com.helger.phive.api.executorset.ValidationExecutorSetRegistry;
import com.helger.phive.api.result.ValidationResultList;
import com.helger.phive.api.validity.IValidityDeterminator;
import com.helger.phive.en16931.EN16931Validation;
import com.helger.phive.xml.source.IValidationSourceXML;
import com.helger.phive.xml.source.ValidationSourceXML;
import com.helger.phive.xrechnung.XRechnungValidation;

/**
 * Validate created XRechnung elements.
 *
 * @author Philip Helger
 */
public final class XRechnungValidator
{
  private static final ValidationExecutorSetRegistry <IValidationSourceXML> VES_REGISTRY = new ValidationExecutorSetRegistry <> ();
  static
  {
    EN16931Validation.initEN16931 (VES_REGISTRY);
    XRechnungValidation.initXRechnung (VES_REGISTRY);
  }

  private XRechnungValidator ()
  {}

  public static void validateXRechnung (@Nonnull final DVRCoordinate aVESID,
                                        @Nonnull final Node aNode,
                                        @Nonnull final ErrorList aErrorList)
  {
    final IValidationExecutorSet <IValidationSourceXML> aVES = VES_REGISTRY.getOfID (aVESID);
    if (aVES == null)
      throw new IllegalStateException ("Failed to resolve VESID " + aVESID);
    // What to validate?
    final IValidationSourceXML aValidationSource = ValidationSourceXML.create (null, aNode);
    // Main validation
    final ValidationResultList aValidationResult = ValidationExecutionManager.executeValidation (IValidityDeterminator.createDefault (),
                                                                                                 aVES,
                                                                                                 aValidationSource);
    aValidationResult.forEachFlattened (aErrorList::add);
  }
}
