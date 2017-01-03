
Classes that only have static fields or methods should not have a public constructor. This includes the default constructor.

Valid:
````
final class StringUtils {

    private Utils() {}

    private static int count(chat c, String s) {}
}

class StringUtils {

    protected Utils() {
        throw new UnsupportedOperationException();
    }

    private static int count(chat c, String s) {}
}
````

Invalid:
````
class StringUtils {

    private static int count(chat c, String s) {}
}
````
