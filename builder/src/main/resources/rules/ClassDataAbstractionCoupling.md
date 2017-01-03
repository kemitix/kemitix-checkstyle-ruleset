
Restricts to 7 the number of different classes instantiated within a class when that class is instantiated.

Valid:
````
class Valid {
    private final Item i1 = new Item();
    private final Item i2 = new Item();
    private final Item i3 = new Item();
    private final Item i4 = new Item();
    private final Item i5 = new Item();
    private final Item i6 = new Item();
    private final Item i7 = new Item();
    private final Item i8 = new Item();
}
````

Invalid:
````
class Invalid {
    private final ItemA i1 = new ItemA();
    private final ItemB i2 = new ItemB();
    private final ItemC i3 = new ItemC();
    private final ItemD i4 = new ItemD();
    private final ItemE i5 = new ItemE();
    private final ItemF i6 = new ItemF();
    private final ItemG i7 = new ItemG();
    private final ItemH i8 = new ItemH();
}
````
