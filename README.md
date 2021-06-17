# DBA Projekt Buchladen

## Tools
* [Dummy data generator](https://migano.de/testdaten.php)
* [RegEx Tester](https://regex101.com/)

## RegEx
### Ortsname
* `/^[a-zA-Z_äÄöÖüÜß]+(?:[\s-][a-zA-Z]+)*$`
  * Alle Wörter mit Umlauten.
  * Keine Leerzeichen am Anfang & Ende
  * Kein Bindestrich/anderes Sonderzeichen am Ende

### Passwort
* `^[a-zA-Z0-9]{8,}$`
  * Minimum 8 Zeichen
  * Klein- & Großbuchstaben & Zahlen

### Lieferdatum
* `^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\s*$`
  * Form dd.mm.yyyy
  * Jahr von 1900 bis 2000  
