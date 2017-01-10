
Restricts the cyclomatic complexity of a method to 5. The cyclomatic complexity is a measure of the number of decision points in a method.

A method with no branches has a complexity of 1. For each `if`, `while`, `do`, `for`, `?:`, `catch`, `switch`, `case`, `&&` and `||` the complexity goes up by 1.

Valid:
````
void isValid(int a, int b, int c) {
    // 1
    if (a > b) { // +1 = 2
        switch (c) { // +1 = 3
            case 1: // +1 = 4
                break;
            case 2: // +1 = 5
                break;
        }
    }
}
````

Invalid:
````
void isInvalid(int a, int b, int c) {
    // 1
    if (a > b) { // +1 = 2
        switch (c) { // +1 = 3
            case 1: // +1 = 4
                break;
            case 2: // +1 = 5
                break;
            case 3: // +1 = 6
                break;
        }
    }
}
````
