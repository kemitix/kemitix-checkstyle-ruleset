
Checks if a variable is declared outside an `if` block that is only used within that block.

Valid:
````
if (condition) {
    String variable = input.substring(1);
    return method(variable);
}
return "";
````

Invalid:
````
String variable = input.substring(1);
if (condition) {
    return method(variable);
}
return "";
````
