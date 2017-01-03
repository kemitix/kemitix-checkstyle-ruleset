
Prevent the placement of variables or fields after methods in an expression.

Valid:
````
if (property && getProperty()) {}
````

Invalid:
````
if (getProperty() && property) {}
````
