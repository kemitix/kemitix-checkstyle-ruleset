
Checks that the expression with the `if` condition in an `if-then-else` statement is not negated.

Valid:
````
if (isValid()) {
    handleValidCondition();
} else {
    handleInvalidCondition();
}
````

Invalid:
````
if (!isValid()) {
    handleInvalidCondition();
} else {
    handleValidCondition();
}
````
