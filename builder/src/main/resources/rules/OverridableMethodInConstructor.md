
Prevents calls to overridable methods from constuctors including other methods that perform the same functions. (i.e. `Cloneable.clone()` and `Serializable.readObject()`)

Invalid:
````
abstract class Base {
    Base() {
        overrideMe();
    }
}
class Child extends Base {
    final int x;
    Child(int x) {
        this.x = x;
    }
    void overrideMe() {
        System.out.println(x);
    }
}
new Child(42); // prints "0"
````
