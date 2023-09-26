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

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.diver.api.version.VESID;
import com.helger.phive.xrechnung.XRechnungValidation;

/**
 * XRechnung version specifics.
 *
 * @author Philip Helger
 */
@SuppressWarnings ("deprecation")
public enum EXRechnungVersion
{
  // Valid from 1.7.2020 - 31.12.2020
  V12 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2",
       XRechnungValidation.VID_XRECHNUNG_CII_122,
       XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_122,
       XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_122),
  // Valid from 01.01.2021
  V20 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0",
       XRechnungValidation.VID_XRECHNUNG_CII_201,
       XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_201,
       XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_201),
  // Valid from 01.02.2022
  V21 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1",
       XRechnungValidation.VID_XRECHNUNG_CII_211,
       XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_211,
       XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_211),
  // Valid from 01.08.2022
  V22 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2",
       XRechnungValidation.VID_XRECHNUNG_CII_220,
       XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_220,
       XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_220),
  // Valid from 01.08.2023
  V23 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3",
       XRechnungValidation.VID_XRECHNUNG_CII_231,
       XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_231,
       XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_231),
  // Valid from 01.02.2024
  V30 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_3.0",
       XRechnungValidation.VID_XRECHNUNG_CII_301,
       XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_301,
       XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_301);

  private final String m_sCustomizationID;
  private final VESID m_aCII;
  private final VESID m_aUBLInvoice;
  private final VESID m_aUBLCreditNote;

  EXRechnungVersion (@Nonnull @Nonempty final String sCustomizationID,
                     @Nonnull final VESID aCII,
                     @Nonnull final VESID aUBLInvoice,
                     @Nonnull final VESID aUBLCreditNote)
  {
    m_sCustomizationID = sCustomizationID;
    m_aCII = aCII;
    m_aUBLInvoice = aUBLInvoice;
    m_aUBLCreditNote = aUBLCreditNote;
  }

  @Nonnull
  @Nonempty
  public String getCustomizationID ()
  {
    return m_sCustomizationID;
  }

  @Nonnull
  @Nonempty
  public String getProfileID ()
  {
    return "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0";
  }

  @Nonnull
  public VESID getVESID_CII ()
  {
    return m_aCII;
  }

  @Nonnull
  public VESID getVESID_UBLInvoice ()
  {
    return m_aUBLInvoice;
  }

  @Nonnull
  public VESID getVESID_UBLCreditNote ()
  {
    return m_aUBLCreditNote;
  }
}
