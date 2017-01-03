
Checks that string literals are on the left side in an `equals()` comparison.

Valid:
````
String nullString = null;
"value".equals(nullString);
````

Invalid:
````
String nullString = null;
nullString.equals("value");
````
