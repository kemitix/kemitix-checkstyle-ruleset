
Prevent the following types from being in a `catch` statement:

* java.lang.Exception
* java.lang.Throwable
* java.lang.RuntimeException

Valid:
````
try {
    doSomething();
} catch (SpecificException e) {
    // log
}
````

Invalid:
````
try {
    doSomething();
} catch (Exception e) {
    // log
}
````
