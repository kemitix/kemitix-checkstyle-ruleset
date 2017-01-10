
Checks for multiple occurrences of the same string literal within a single file. Does not apply to empty strings ("").

Invalid:
````
String fooFoo = "foo" + "foo";
````
