# ebinterface-xrechnung-mapping

Mapping between ebInterface and the German XRechnung UBL.
This is a library to convert between the two formats.
There is no assigned user interface or the like.
To use the conversion between these formats, checkout https://labs.ebinterface.at/

## XRechnung UBL to ebInterface

To convert an XRechnung UBL to ebInterface use the following classes:
* `XRechnungUBLInvoiceToEbInterface40Converter` - convert to ebInterface v4.0
* `XRechnungUBLInvoiceToEbInterface41Converter` - convert to ebInterface v4.1
* `XRechnungUBLInvoiceToEbInterface42Converter` - convert to ebInterface v4.2
* `XRechnungUBLInvoiceToEbInterface43Converter` - convert to ebInterface v4.3
* `XRechnungUBLInvoiceToEbInterface50Converter` - convert to ebInterface v5.0

## ebInterface to XRechnung UBL

To convert an ebInterface to an XRechnung UBL use the following classes:
* `EbInterface40ToXRechnungUBLConverter` - convert from ebInterface v4.0
* `EbInterface41ToXRechnungUBLConverter` - convert from ebInterface v4.1
* `EbInterface42ToXRechnungUBLConverter` - convert from ebInterface v4.2
* `EbInterface43ToXRechnungUBLConverter` - convert from ebInterface v4.3
* `EbInterface50ToXRechnungUBLConverter` - convert from ebInterface v5.0
