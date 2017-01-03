
Checks that `if` blocks are not nested more than 1 deep.

Valid:
````
if (isValid()) { // depth 0
    if (isExpected()) { // depth 1
        doIt();
    }
}
````

Invalid:
````
if (isValid()) { // depth 0
    if (isExpected()) { // depth 1
        if (isNecessary()) { // depth 2!
            doIt();
        }
    }
}
````
