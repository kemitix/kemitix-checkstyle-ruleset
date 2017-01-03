
Checks that there is at most one ternary statments (`?:`) within an expression.

Invalid:
````
String x = value != null ? "A" : "B" + value == null ? "C" : "D"
````
