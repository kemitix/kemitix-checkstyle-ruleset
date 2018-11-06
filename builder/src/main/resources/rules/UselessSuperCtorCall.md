
Checks for useless calls to the `super()` method in constructors.

Invalid:
````
class Dummy {
    Dummy() {
        super();
    }
}
class Derived extends Base {
    Derived() {
        super();
    }
}
````
