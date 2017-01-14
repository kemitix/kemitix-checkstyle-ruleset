
Checks that the control variable in a `for` loop is not modified inside the loop.

Invalid:
````
for (int i = 0; i < 1; i++) {
    i++;
}
````
