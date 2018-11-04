
Checks for catch blocks that are useless. i.e. that catch all exceptions and then just rethrow them.

Invalid:
````
try {
    doSomething();
} catch (Exception e) {
    throw e;
}
````
