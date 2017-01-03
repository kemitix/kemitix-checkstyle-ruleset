
Enums are considered to be of two distinct types: 'Class' or 'Value' enumerations. The distinction being that Class Enumerations have methods (other than `toString()`) defined.

The values defined in the `enum` must match the appropriate pattern:

* Class: `^[A-Z][a-zA-Z0-9]*$`
* Value: `^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$`

The difference being that Class enumerations can't contain underscores but can include lowercase letters (after the first initial capital). Value enumerations can include underscores, but not as the first or second character.

Valid:
````
enum ValidConstants {

    ALPHA, BETA;
}

enum ValidClassLike {

    Alpha("a"),
    Beta("b");

    private String name;

    ValidClassLike(String name) {
        this.name = name;
    }
}
````

Invalid:
````
enum InvalidConstants {

    alpha, Beta, GAMMA_RAY;
}

enum InvalidClassLike {

    alpha("a"),
    beta("b");

    private String name;

    InvalidClassLike(String name) {
        this.name = name;
    }
}
````
