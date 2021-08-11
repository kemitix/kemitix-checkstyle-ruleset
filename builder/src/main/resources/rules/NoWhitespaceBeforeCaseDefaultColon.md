
Checks that there is no whitespace before the colon in a switch block. .

Valid:
````
switch(1) {
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
switch(2) {
    case 2: // ok
        break;
    case 3, 4
             : break; // violation, whitespace before ':' is not allowed here
    case 4,
          5: break; // ok
    default
          : // violation, whitespace before ':' is not allowed here
        break;
}
````
