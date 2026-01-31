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
package at.austriapro.ebinterface.xrechnung;

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.misc.Translatable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.diagnostics.error.level.EErrorLevel;
import com.helger.diagnostics.error.list.IErrorList;
import com.helger.text.IMultilingualText;
import com.helger.text.display.IHasDisplayTextWithArgs;
import com.helger.text.resolve.DefaultTextResolver;
import com.helger.text.util.TextHelper;

/**
 * Abstract base class for converter between XRechnung to ebInterface (in both
 * directions).
 *
 * @author Philip Helger
 */
public abstract class AbstractEbInterfaceXRechnungConverter
{
  @Translatable
  protected enum EText implements IHasDisplayTextWithArgs
  {
    WARNING_1 ("1 Warnung", "1 warning"),
    WARNINGS_N ("{0} Warnungen", "{0} warnings"),
    ERROR_1 ("1 Fehler", "1 error"),
    ERRORS_N ("{0} Fehler", "{0} errors"),
    SUPPLIER_FI ("Rechnungssteller weitere Informationen\n", "Supplier FurtherIdentification\n"),
    CUSTOMER_FI ("Rechnungsempfänger weitere Informationen\n", "Customer FurtherIdentification\n"),
    ALLOWANCE ("Abschlag", "Allowance"),
    CHARGE ("Zuschlag", "Charge"),
    DUE_DATE ("Fällig am {0}", "Due at {0}");

    @NonNull
    private final IMultilingualText m_aTP;

    EText (@NonNull final String sDE, @NonNull final String sEN)
    {
      m_aTP = TextHelper.create_DE_EN (sDE, sEN);
    }

    @Nullable
    public String getDisplayText (@NonNull final Locale aContentLocale)
    {
      return DefaultTextResolver.getTextStatic (this, m_aTP, aContentLocale);
    }
  }

  protected final Locale m_aDisplayLocale;
  protected final Locale m_aContentLocale;

  protected AbstractEbInterfaceXRechnungConverter (@NonNull final Locale aDisplayLocale,
                                                   @NonNull final Locale aContentLocale)
  {
    ValueEnforcer.notNull (aDisplayLocale, "DisplayLocale");
    ValueEnforcer.notNull (aContentLocale, "ContentLocale");
    m_aDisplayLocale = aDisplayLocale;
    m_aContentLocale = aContentLocale;
  }

  @Nonnegative
  protected static final int countWarnings (@NonNull final IErrorList aErrorList)
  {
    return aErrorList.getCount (x -> x.getErrorLevel () == EErrorLevel.WARN);
  }

  @NonNull
  @Nonempty
  protected final String warningText (@Nonnegative final int nWarn)
  {
    return nWarn == 1 ? EText.WARNING_1.getDisplayText (m_aDisplayLocale)
                      : EText.WARNINGS_N.getDisplayTextWithArgs (m_aDisplayLocale, Integer.toString (nWarn));
  }

  @NonNull
  @Nonempty
  protected final String errorText (@Nonnegative final int nErrs)
  {
    return nErrs == 1 ? EText.ERROR_1.getDisplayText (m_aDisplayLocale)
                      : EText.ERRORS_N.getDisplayTextWithArgs (m_aDisplayLocale, Integer.toString (nErrs));
  }
}
