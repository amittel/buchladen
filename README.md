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
