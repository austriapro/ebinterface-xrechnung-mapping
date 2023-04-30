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
package at.austriapro.ebinterface.xrechnung.from;

import java.util.Locale;

import javax.annotation.Nonnull;

import at.austriapro.ebinterface.ubl.from.ToEbinterfaceSettings;
import at.austriapro.ebinterface.xrechnung.AbstractEbInterfaceXRechnungConverter;

/**
 * Abstract base class to convert XRechnung to ebInterface.
 *
 * @author Philip Helger
 */
public abstract class AbstractXRechnungToEbInterfaceConverter extends AbstractEbInterfaceXRechnungConverter
{
  protected final ToEbinterfaceSettings m_aToEbiSettings;

  protected AbstractXRechnungToEbInterfaceConverter (@Nonnull final Locale aDisplayLocale,
                                                     @Nonnull final Locale aContentLocale)
  {
    super (aDisplayLocale, aContentLocale);
    m_aToEbiSettings = new ToEbinterfaceSettings ().setUBLVersionIDMandatory (false)
                                                   .setUBLProfileIDMandatory (false)
                                                   .setOrderReferenceIDMandatory (false)
                                                   .setErrorOnPositionNumber (false);
  }
}
