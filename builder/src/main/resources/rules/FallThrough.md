
Checks that when a `case` in a `switch` statement falls through (i.e. doesn't end with `break;`) that the fall through is documented with a comment.

Valid:
````
switch (i) {
    case 0:
        i++; // fall through

    case 1:
        i++;
        // falls through

    case 2:
    case 3:
    case 4: { i++ } // fallthrough
    case 5:
        i++;
        /* fallthrou */
    case 6:
        i++;
        break;
}
````

Invalid:
````
switch (i) {
    case 0:
        i++;
    case 1:
        i++;
    case 2:
    case 3:
    case 4: { i++ }
    case 5:
        i++;
    case 6:
        i++;
        break;
}
````
