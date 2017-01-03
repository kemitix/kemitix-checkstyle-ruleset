
Checks that `catch` blocks are not empty, or are commented with the word `expected` or `ignore`.

Valid:
````
try {
    something();
} catch (Exception e) {
    // ignore
}
````

Invalid:
````
try {
    something();
} catch (Exception e) {
    // do nothing
}
````
