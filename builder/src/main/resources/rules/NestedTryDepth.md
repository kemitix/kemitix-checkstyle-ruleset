
Checks that `try` blocks are not nested.

Valid:
````
try {
    doSomething();
    doSomeOtherThing();
} catch (SomeException se) {
    // handle it
} catch (OtherExceptions oe) {
    // handle it
}
````

Invalid:
````
try {
   doSomething();
   try {
       doSomeOtherThing();
   } catch (OtherExceptions oe) {
       // handle it
   }
} catch (SomeException se) {
   // handle it
}
````
