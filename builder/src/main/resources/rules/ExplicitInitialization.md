
Checks that fields are not being explicitly initialised to their already default value.

Valid:
````
class Valid {

    private int foo;

    private Object bar;
}
````

Invalid:
````
class Invalid {

    private int foo = 0;

    private Object bar = null;
}
````
