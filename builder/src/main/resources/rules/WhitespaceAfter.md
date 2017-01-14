
Checks that commas (','), statement terminators (';') and typecasts are all followed by a space.

Valid:
````
doSomething(1, 2, 3);
if (a > 1) { return true; }
String name = (String) list.get(9);
````

Inalid:
````
doSomething(1,2,3);
if (a > 1) { return true;}
String name = (String)list.get(9);
````
