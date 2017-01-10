
Checks that there are no spaces within the typecasting parentheses.

Valid:
````
String s = (String) list.get(2);
````

Invalid:
````
String s = (String ) list.get(2);
String s = ( String) list.get(2);
String s = ( String ) list.get(2);
````
