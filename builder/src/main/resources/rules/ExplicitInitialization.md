
Checks that object fields are not being explicitly initialised to their already default value.

Does not check primitive field types.

Valid:
````
class Valid {

    private int foo = 0;

    private Object bar;
}
````

Invalid:
````
class Invalid {

    private Integer foo = 0;

    private Object bar = null;
}
````
