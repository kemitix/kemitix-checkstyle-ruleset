
Checks that Enum Values match the pattern: `^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$`

Valid:
````
enum Valid {

    ALPHA, BETA, GAMMA_RAY;
}
````

Invalid:
````
enum InvalidConstants {

    alpha, Beta;
}
````
