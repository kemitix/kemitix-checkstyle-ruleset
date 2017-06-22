
Checks that all the non-private methods of a class are cohesive.

A class is considered to be cohesive if all of it's non-private methods are
related. A method is related to another method if it calls that method, is
called by that method, they both call the same other method, or they both
access the same field.

Bean methods are excluded from this, e.g. getValue(), setValue(value) or isValue(). This allows data classes, that would otherwise need a separate class
for each field.

Valid:
````java
public class Valid {

    private String field;

    public String getField() {
        return field;
    }

    public String getField(boolean ignored) {
        return field;
    }

    private void dummy() {
        field = "";
    }

}
````

Invalid:

component 1
* counter
* count()
* increment()

component 2
* left
* format
* right
* getFullFormat()
* sayHello(name)

N.B. getLeft() and getRight() are ignored as they are 'bean' methods.

````java
public class Invalid {

    private String left;

    private String right;

    private int counter = 0;

    private String format = "Hello, %s!";

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public int counter() {
        return counter;
    }

    public void increment() {
        counter += 1;
    }

    public String getFullFormat() {
        return left + format + right;
    }

    public String sayHello(final String name) {
        return String.format(getFullFormat(), name);
    }

}
````
