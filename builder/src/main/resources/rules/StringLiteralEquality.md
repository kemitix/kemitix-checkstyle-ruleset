
Checks that string literals are not used with `==` or `!=`.

Valid:
````
if ("something".equals(x)) {}
````

Invalid:
````
if (x == "something") {}
````
