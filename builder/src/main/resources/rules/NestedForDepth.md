
Checks that `for` loops are not nested more than 1 deep.

Valid:
````
for (int i = 0; i < 1; i++) { // depth 0
    for (int j = 0; j < 1; j++) { // depth 1
        //
    }
}
````

Invalid:
````
for (int i = 0; i < 1; i++) { // depth 0
    for (int j = 0; j < 1; j++) { // depth 1
        for (int k = 0; j < 1; k++) { // depth 2!
            //
        }
    }
}
````
