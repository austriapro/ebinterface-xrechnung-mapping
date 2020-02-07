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

## XRechnung CII to ebInterface

To convert an XRechnung CII to ebInterface use the following classes:
* `XRechnungCIIInvoiceToEbInterface40Converter` - convert to ebInterface v4.0
* `XRechnungCIIInvoiceToEbInterface41Converter` - convert to ebInterface v4.1
* `XRechnungCIIInvoiceToEbInterface42Converter` - convert to ebInterface v4.2
* `XRechnungCIIInvoiceToEbInterface43Converter` - convert to ebInterface v4.3
* `XRechnungCIIInvoiceToEbInterface50Converter` - convert to ebInterface v5.0

## ebInterface to XRechnung UBL

To convert an ebInterface to an XRechnung UBL use the following classes:
* `EbInterface40ToXRechnungUBLConverter` - convert from ebInterface v4.0
* `EbInterface41ToXRechnungUBLConverter` - convert from ebInterface v4.1
* `EbInterface42ToXRechnungUBLConverter` - convert from ebInterface v4.2
* `EbInterface43ToXRechnungUBLConverter` - convert from ebInterface v4.3
* `EbInterface50ToXRechnungUBLConverter` - convert from ebInterface v5.0

# Maven usage

Add the following to your pom.xml to use this artifact:

```xml
<dependency>
  <groupId>at.austriapro</groupId>
  <artifactId>ebinterface-xrechnung-mapping</artifactId>
  <version>1.0.3</version>
</dependency>
```

# News and noteworthy

* v1.0.3 - 2020-02-07
    * Updated to peppol-commons 8.x
* v1.0.2 - 2020-01-13
    * Updated to support the XRechnung 1.2.2 document type
* v1.0.1 - 2019-10-14
    * Updated to ebinterface-ubl-mapping 4.5.1
* v1.0.0 - 2019-10-01
    * Changed all package names to `at.austriapro`
    * Changed the Maven group `at.austriapro`
* v0.9.0 - 2019-09-18
    * Initial version using ebinterface-ubl-mapping v4.0.0 and cii2ubl v1.1.5

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
On Twitter: <a href="https://twitter.com/philiphelger">@philiphelger</a> |
Kindly supported by [YourKit Java Profiler](https://www.yourkit.com)