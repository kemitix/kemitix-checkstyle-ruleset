
Checks that classes which have only private constructors are also declared as `final`. These classes can't be extended by a subclass as they can't call `super()` from their constructors.

Valid:
````
final class Valid {

    private Valid() {}
}
````

Invalid:
````
class Invalid {

    private Invalid() {}
}
````
