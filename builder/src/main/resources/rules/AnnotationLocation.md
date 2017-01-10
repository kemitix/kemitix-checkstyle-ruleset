
Annotations must be on a line by themselves unless annotating a method parameter or among class modifiers.

Valid:
````
@Component
@Qualifier("Red")
class RedStick implements Stick {

    public @NonNull String getLabel(@Value("${stick.length}") final int length) {
        // ...
    }
}
````

Invalid:
````
@Component @Qualifier("Red")
class RedStick implements Stick {}
````
