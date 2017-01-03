
Ensure class elements appear in the correct order.

Valid:
````
class Valid {
    // static
    public static int a;
    protected static int b;
    static int c;
    private static int d;

    // instance
    public int e;
    protected int f;
    int g;
    private int h;

    // constructors
    Valid() {}

    // methods
    void foo() {}
}
````

Invalid:
````
class Invalid {
    protected static int b;
    public static int a;
    private static int d;

    public int e;
    static int c;
    protected int f;
    private int h;

    void foo() {}

    Valid() {}

    int g;
}
````
