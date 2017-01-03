
An `interface` must define methods, not just constants.

Valid:
````
interface Foo {

    static final String "Foo!!";

    getBar();
}
````

Invalid:
````
interface Foo {

    static final String "Foo!!";
}
````
