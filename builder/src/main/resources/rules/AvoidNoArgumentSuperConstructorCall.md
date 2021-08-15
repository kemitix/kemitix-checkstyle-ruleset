
Checks if call to superclass constructor without arguments is present. Such invocation is redundant
because constructor body implicitly begins with a superclass constructor invocation super();
See
[specification](https://docs.oracle.com/javase/specs/jls/se16/html/jls-8.html#jls-8.8.7)
for detailed information.

Valid:
````java
class MyClass extends SomeOtherClass {
    MyClass(int arg) {
        super(arg); // OK, call with argument have to be explicit
    }
    MyClass(long arg) {
        // OK, call is implicit
    }
}
````

Invalid:
````java
class MyClass extends SomeOtherClass {
    MyClass() {
        super(); // violation
    }
}
````
