
Prevents declaring a method from returning a wildcard type as its return value.

Valid:
````
<E> List<E> getList() {}
````

Invalid:
````
<E> List<? extends E> getList() {}
````
