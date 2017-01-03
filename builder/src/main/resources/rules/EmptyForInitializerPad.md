
Checks that there is no padding in an empty `for` loop **initialiser**.

Valid:
````
for (; i < j ; i++) {}
````

Invalid:
````
for ( ; i < j ; i++) {}
````
