
Checks that a local variable or parameter in a method doesn't have the same name as a field. Doesn't apply in constructors or setters.

Valid:
````
class Foo {

    private int a;

    Foo(int a) {
        this.a = a;
    }

    setA(int a) {
        this.a = a;
    }
}
````

Invalid:
````
class Bar {

    private int b;

    void baz(int b) {
        // ...
    }
}
````
