
Check that the `default` is after all the `case`s in a `switch` statement.

Valid:
````
switch (a) {
    case 1:
        break;
    case 2:
        break;
    default:
        break;
}
````

Invalid:
````
switch (a) {
    case 1:
        break;
    default:
        break;
    case 2:
        break;
}
````
