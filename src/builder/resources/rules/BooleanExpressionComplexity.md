
Restrict the number of number of &&, ||, &, | and ^ in an expression to 2.

Valid:
````
if (a || (b && c)) {}
````

Invalid:
````
if (a > b || b > c || c == a || d > a) {}
````
