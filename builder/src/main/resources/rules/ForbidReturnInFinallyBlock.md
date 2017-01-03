
Prevent the use of a `return` statement in the `finally` block.

Invalid:
````
try {
    doSomething();
{ catch (IOException e) {
    // log error
} finally (
    return true; // invalid
}
````
