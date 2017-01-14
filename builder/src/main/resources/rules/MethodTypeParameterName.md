
Restricts method generics parameters to be a single uppercase letter.

Valid:
````
List<A> getItems() {}
````

Invalid:
````
List<a> getItems() {}
List<BB> getItems() {}
List<C3> getItems() {}
````
