package at.austriapro.ebinterface.xrechnung;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.phive.api.executorset.VESID;
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
  V122 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2",
        XRechnungValidation.VID_XRECHNUNG_CII_122,
        XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_122,
        XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_122),
  // Valid from 01.01.2021
  V200 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0",
        XRechnungValidation.VID_XRECHNUNG_CII_200,
        XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_200,
        XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_200), // Valid from
                                                               // 01.01.2021
  V201 ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0",
        XRechnungValidation.VID_XRECHNUNG_CII_201,
        XRechnungValidation.VID_XRECHNUNG_UBL_INVOICE_201,
        XRechnungValidation.VID_XRECHNUNG_UBL_CREDITNOTE_201);

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
