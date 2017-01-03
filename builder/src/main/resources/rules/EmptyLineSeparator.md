
Checks that there are blank lines between header, package, import blocks, field, constructors, methods, nested classes, static initialisers and instance initialisers.

Valid:
````
/**
 * Licence header.
 */

package net.kemitix.foo;

import ...;
import ...;

class Foo {

    private int a;

    private int b;

    Foo() {}

    Foo(int a, int b) {}

    int getA() {}

    int getB() {}

    class Bar {
    }
}
````

Invalid:
````
/**
 * Licence header.
 */
package net.kemitix.foo;
import ...;
import ...;
class Foo {
    private int a;
    private int b;
    Foo() {}
    Foo(int a, int b) {}
    int getA() {}
    int getB() {}
    class Bar {
    }
}
````
