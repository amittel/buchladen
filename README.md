# DBA Projekt Buchladen

## Tools
* [Dummy data generator](https://migano.de/testdaten.php)

## RegEx
### Ortsname
* `/^[a-zA-Z_äÄöÖüÜß]+(?:[\s-][a-zA-Z]+)*$`
  * Alle Wörter mit Umlauten.
  * Keine Leerzeichen am Anfang & Ende
  * Kein Bindestrich/anderes Sonderzeichen am Ende
