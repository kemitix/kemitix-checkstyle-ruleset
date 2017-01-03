
Checks for comments at the end of lines.

Valid:
````
// comment on line by itself
    // comment after white space
if (a < 1) {
    //
} // comment on closing brace
int[] a = new int[2](
    1, 2
); // comment on closing parenthesis of statement
````

Invalid:
````
int a = 1; // comment in line with statement
if (a < 1) { // comment on line with if statement
    //
}
int[] a = new int[2](
    1, // first value - invalid comment
    2  // second value - also invalid comment
);
````
