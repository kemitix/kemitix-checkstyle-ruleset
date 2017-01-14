
Checks for catch blocks that are useless. i.e. that catch al exceptions and then just rethrow them.

Invalid:
````
try {
    doSomething();
} catch (Exception e) {
    throw e;
}
````
