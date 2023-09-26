# ebinterface-xrechnung-mapping

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/at.austriapro/ebinterface-xrechnung-mapping/badge.svg)](https://maven-badges.herokuapp.com/maven-central/at.austriapro/ebinterface-xrechnung-mapping) 
[![javadoc](https://javadoc.io/badge2/at.austriapro/ebinterface-xrechnung-mapping/javadoc.svg)](https://javadoc.io/doc/at.austriapro/ebinterface-xrechnung-mapping)
[![CodeCov](https://codecov.io/gh/austriapro/ebinterface-xrechnung-mapping/branch/master/graph/badge.svg)](https://codecov.io/gh/austriapro/ebinterface-xrechnung-mapping)

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
* `XRechnungUBLInvoiceToEbInterface60Converter` - convert to ebInterface v6.0
* `XRechnungUBLInvoiceToEbInterface61Converter` - convert to ebInterface v6.1

## XRechnung CII to ebInterface

To convert an XRechnung CII to ebInterface use the following classes:
* `XRechnungCIIInvoiceToEbInterface40Converter` - convert to ebInterface v4.0
* `XRechnungCIIInvoiceToEbInterface41Converter` - convert to ebInterface v4.1
* `XRechnungCIIInvoiceToEbInterface42Converter` - convert to ebInterface v4.2
* `XRechnungCIIInvoiceToEbInterface43Converter` - convert to ebInterface v4.3
* `XRechnungCIIInvoiceToEbInterface50Converter` - convert to ebInterface v5.0
* `XRechnungCIIInvoiceToEbInterface60Converter` - convert to ebInterface v6.0
* `XRechnungCIIInvoiceToEbInterface61Converter` - convert to ebInterface v6.1

## ebInterface to XRechnung UBL

To convert an ebInterface to an XRechnung UBL use the following classes:
* `EbInterface40ToXRechnungUBLConverter` - convert from ebInterface v4.0
* `EbInterface41ToXRechnungUBLConverter` - convert from ebInterface v4.1
* `EbInterface42ToXRechnungUBLConverter` - convert from ebInterface v4.2
* `EbInterface43ToXRechnungUBLConverter` - convert from ebInterface v4.3
* `EbInterface50ToXRechnungUBLConverter` - convert from ebInterface v5.0
* `EbInterface60ToXRechnungUBLConverter` - convert from ebInterface v6.0
* `EbInterface61ToXRechnungUBLConverter` - convert from ebInterface v6.1

The current supported XRechnung target versions are 1.2.2, 2.0.1, 2.1.1 and 2.2.0.

# Maven usage

Add the following to your pom.xml to use this artifact, replacing `x.y.z` with the effective version number:

```xml
<dependency>
  <groupId>at.austriapro</groupId>
  <artifactId>ebinterface-xrechnung-mapping</artifactId>
  <version>x.y.z</version>
</dependency>
```

# News and noteworthy

* v2.2.0 - 2023-09-26
    * Updated to phive 9.x
    * Updated to ebinterface-ubl-mapping 5.1.1
    * Setting UBL XRechnung Profile ID to a default value
    * Added support for XRechnung 3.0
* v2.1.0 - 2023-04-30
    * Updated to ph-ubl 8.x
* v2.0.0 - 2023-03-03
    * Using Java 11 as the baseline
    * Updated to ph-commons 11
    * Using JAXB 4.0 as the baseline
    * Added support for XRechnung 2.3
* v1.4.0 - 2022-10-12
    * Added support for XRechnung 2.2
    * Added support for ebInterface 6.1
* v1.3.0 - 2021-05-02
    * Updated to ph-commons 10.1
* v1.2.0 - 2021-03-22
    * Updated to ph-commons 10
* v1.1.2 - 2021-01-11
    * Updated to conform to EN16931 rules 1.3.3
    * Updated to en16931-cii2ubl 1.3.0
* v1.1.1 - 2020-11-26
    * Version updates
* v1.1.0 - 2020-11-06
    * Added support for creating XRechnung 2.0.0 documents
* v1.0.9 - 2020-09-17
    * Updated to Jakarta JAXB 2.3.3
* v1.0.8 - 2020-08-30
    * Updated to ph-ubl 6.4.0
    * Updated to ph-ebinterface 6.2.0
    * Updated to ebinterface-ubl-mapping 4.6.0
* v1.0.7 - 2020-06-08
    * Updated to ph-bdve 6.0.0
* v1.0.6 - 2020-05-27
    * Updated to new Maven groupIds
* v1.0.5 - 2020-05-14
    * Added support for ebInterface 6.0
    * Made ebInterface customizer customizable
* v1.0.4 - 2020-04-01
    * Updated to ebinterface-ubl-mapping 4.5.4
    * Updated to en16931-cii2ubl 1.2.0
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
