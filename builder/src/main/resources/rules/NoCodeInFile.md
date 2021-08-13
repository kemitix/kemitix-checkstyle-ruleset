
Checks whether file contains code. Files which are considered to have no code:

- File with no text
- File with only single line comment(s)
- File with only a multi line comment(s).

Invalid:
````java
// single line comment // violation
````

Invalid:
````java
/* // violation
 block comment
*/
````
