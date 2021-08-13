
Checks if the javadoc has leading asterisks on each line.

The check does not require asterisks on the first line, nor on the last line if
it is blank. All other lines in a Javadoc should start with *, including blank
lines and code blocks. 

Valid:
````
/**
 * Valid Java-style comment.
 *
 * <pre>
 *   int value = 0;
 * </pre>
 */
class JavaStyle {} // ok

/** Valid Scala-style comment.
  * Some description here.
  **/
class ScalaStyle {} // ok

/** **
  * Asterisks on first and last lines are optional.
  * */
class Asterisks {} // ok

/** No asterisks are required for single-line comments. */
class SingleLine {} // ok
````

Invalid:
````
/** // violation on next blank line, javadoc has lines without leading asterisk.

 */
class BlankLine {}

/** Wrapped
    single-line comment */ // violation, javadoc has lines without leading asterisk.
class Wrapped {}

/**
  * <pre>
    int value; // violation, javadoc has lines without leading asterisk.
  * </pre>
  */
class Code {}
````