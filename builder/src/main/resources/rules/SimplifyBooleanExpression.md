
Checks for overly complicated boolean expressions. Checks for code like `b == true`, `b || true`, `!false`, etc.

Valid:
````
if (b) {}
if (true) {}
````

Invalid:
````
if (b == true) {}
if (b || true) {}
if (!false) {}
````
