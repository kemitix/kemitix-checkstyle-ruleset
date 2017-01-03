
Checks that there is no padding in an empty `for` loop **iterator**.

Valid:
````
for (Iterator i = list.getIterator(); i.hasNext() ;) {}
````

Invalid:
````
for (Iterator i = list.getIterator(); i.hasNext() ; ) {}
````
