
> This check cannot be suppressed.

Checks the visibility of class members to help enforce encapsulation. Only `static final` fields, immutable (see list below) fields or field with special annotation (see list below), may be public.

The following are considered immutable when `final`, and can be `public`:

* java.lang.String
* java.lang.Integer
* java.lang.Byte
* java.lang.Character
* java.lang.Short
* java.lang.Boolean
* java.lang.Long
* java.lang.Double
* java.lang.Float
* java.lang.StackTraceElement
* java.math.BigInteger
* java.math.BigDecimal
* java.io.File
* java.util.Locale
* java.util.UUID
* java.net.URL
* java.net.URI
* java.net.Inet4Address
* java.net.Inet6Address
* java.net.InetSocketAddress

Fields with the following annotations may be `public`:

* org.junit.Rule
* org.junit.ClassRule
* com.google.common.annotations.VisibleForTesting

Valid:
````
class Foo {

    public final Long id;

    public final String name;

    private String description;

    @VisibleForTesting
    public State state;

    Foo(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }
}
````

Invalid:
````
class Foo {

    public Long id;

    public String name;

    private String description;

    public State state;

    Foo(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }
}
````
