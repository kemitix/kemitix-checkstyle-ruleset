
Ensures that an exception is re-thrown properly and is not swallowed by a `catch` block.

Valid:
````
try {
    doSomething();
} catch (MyException e) {
    throw new MyOtherException(e);
}
````

Invalid:
````
try {
    doSomething();
} catch (MyException e) {
    throw new MyOtherException();
}
````
