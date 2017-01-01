
Prevents the use of boolean operators that don't allow short-circuiting the expression. (e.g. '|', '&', '|=' and '&=')

Valid:
````
if ((a < b) || (b > getExpensiveValue())) {}
````

Invalid:
````
if ((a < b) | (b > getExpensiveValue())) {}
````
