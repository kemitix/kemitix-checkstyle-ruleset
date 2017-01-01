
Restricts methods to have at most 2 `return` statements in non-void methods, and at most 1 in void methods.

Valid:
````
int getNumber(int a) {
    if (a > 1) {
        return a;
    }
    return 0;
}

void getName(int a) {
    String name = "default";
    if (a > 1) {
        name = "Bob";
    }
    return name;
}
````

Invalid:
````
int getNumber(int a) {
    if (a > 1) {
        return a;
    }
    if (a < 2) {
        return a * a;
    }
    return 0;
}

void getName(int a) {
    if (a > 1) {
        return "Bob";
    }
    return "default";
}
````
