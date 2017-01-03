
Prevents use of obscure escape codes (e.g. `\u221e`). However, non-printable/control characters are still permitted.

Valid:
````
String unitAbbrev = "??s";
String byteOrdered = '\ufeff' = content;
````

Invalid:
````
String unitAbbrev = "\u03bcs";
````
