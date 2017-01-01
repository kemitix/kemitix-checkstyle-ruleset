
Checks for overly complicated boolean `return` statements.

Valid:
````
return !valid();
````

Invalid:
````
if (valid()) {
    return false;
} else {
    return true;
}
````
