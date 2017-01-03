
Checks that `switch` statement has a `default` case.

Valid:
````
switch (foo) {
    case 1:
        //
        break;
    case 2:
        //
        break;
    default:
        throw new IllegalStateExcetion("Foo: " + foo);
}
````

Invalid:
````
switch (foo) {
    case 1:
        //
        break;
    case 2:
        //
        break;
}
````
