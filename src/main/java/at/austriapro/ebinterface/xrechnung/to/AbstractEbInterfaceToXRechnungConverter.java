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
package at.austriapro.ebinterface.xrechnung.to;

import java.util.Locale;

import org.jspecify.annotations.NonNull;

import com.helger.base.enforce.ValueEnforcer;

import at.austriapro.ebinterface.xrechnung.AbstractEbInterfaceXRechnungConverter;
import at.austriapro.ebinterface.xrechnung.EXRechnungVersion;

/**
 * Base class for converting an ebInterface invoice to an XRechnung.
 *
 * @author Philip Helger
 */
public abstract class AbstractEbInterfaceToXRechnungConverter extends AbstractEbInterfaceXRechnungConverter
{
  protected final EXRechnungVersion m_eXRechnungVersion;

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
  protected AbstractEbInterfaceToXRechnungConverter (@NonNull final Locale aDisplayLocale,
                                                     @NonNull final Locale aContentLocale,
                                                     @NonNull final EXRechnungVersion eXRechnungVersion)
  {
    super (aDisplayLocale, aContentLocale);
    ValueEnforcer.notNull (eXRechnungVersion, "XRechnungVersion");
    m_eXRechnungVersion = eXRechnungVersion;
  }

  /**
   * @return The target XRechnung version for which the files should be created.
   *         Never <code>null</code>.
   */
  @NonNull
  public final EXRechnungVersion getXRechnungVersion ()
  {
    return m_eXRechnungVersion;
  }
}
