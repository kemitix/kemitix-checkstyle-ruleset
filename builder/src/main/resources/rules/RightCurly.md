
Checks that the right curly brace ('}') is placed on the same line as the next part of a multi-block statement (e.g. try-catch-finally, if-then-else).

Valid:
````
try {
    //
} catch (Exception e) {
    //
} finally {
    //
}

if (a > 0) {
    //
} else {
    //
}
````

Invalid:
````
try {
    //
}
catch (Exception e) {
    //
}
finally {
    //
}

if (a > 0) {
    //
}
else {
    //
}

if (a > 0) {
    //
} a = 2;

public long getId() {return id;}
````
