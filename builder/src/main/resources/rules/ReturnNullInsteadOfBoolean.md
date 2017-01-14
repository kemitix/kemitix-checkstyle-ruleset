
The `Boolean` type is meant to only represent a binary state: TRUE or FALSE. It is not a ternary value: TRUE, FALSE, null.

Invalid:
````
Boolean isEnabled() {
    if (level > 0) {
        return True;
    }
    if (level < 0) {
        return False;
    }
    return null;
}
````