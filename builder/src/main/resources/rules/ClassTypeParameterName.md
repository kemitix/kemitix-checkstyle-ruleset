
Restricts class generics parameters to be a single uppercase letter.

Valid:
````
class Deliverator <A> {}
````

Invalid:
````
class Invalidator <a> {}
class Invalidator <BB> {}
class Invalidator <C3> {}
````
