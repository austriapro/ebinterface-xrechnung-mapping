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
package at.austriapro.ebinterface.xrechnung;

import java.util.Locale;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.error.level.EErrorLevel;
import com.helger.commons.error.list.IErrorList;

/**
 * Abstract base class for converter between XRechnung to ebInterface (in both
 * directions).
 *
 * @author Philip Helger
 */
public abstract class AbstractEbInterfaceXRechnungConverter
{
  protected final Locale m_aDisplayLocale;
  protected final Locale m_aContentLocale;

  protected AbstractEbInterfaceXRechnungConverter (@Nonnull final Locale aDisplayLocale,
                                                   @Nonnull final Locale aContentLocale)
  {
    ValueEnforcer.notNull (aDisplayLocale, "DisplayLocale");
    ValueEnforcer.notNull (aContentLocale, "ContentLocale");
    m_aDisplayLocale = aDisplayLocale;
    m_aContentLocale = aContentLocale;
  }

  @Nonnegative
  protected static final int countWarnings (@Nonnull final IErrorList aErrorList)
  {
    return aErrorList.getCount (x -> x.getErrorLevel () == EErrorLevel.WARN);
  }

  @Nonnull
  @Nonempty
  protected static final String warningText (@Nonnegative final int nWarn)
  {
    return nWarn == 1 ? "1 warning" : nWarn + " warnings";
  }

  @Nonnull
  @Nonempty
  protected static final String errorText (@Nonnegative final int nWarn)
  {
    return nWarn == 1 ? "1 error" : nWarn + " errors";
  }
}
