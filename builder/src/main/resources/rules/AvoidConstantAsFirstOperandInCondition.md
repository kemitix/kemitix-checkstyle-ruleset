
Checks that condition expressions don't become less readable by attempting to use a constant on the left-hand-side of a comparison.

Valid:
````
if (a == 12) {}
````

Invalid:
````
if (12 == a) {}
````
