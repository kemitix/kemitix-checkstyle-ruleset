
Checks that there is at least one whitespace after the leading asterisk.
Although spaces after asterisks are optional in the Javadoc comments, their
absence makes the documentation difficult to read. It is the de facto standard
to put at least one whitespace after the leading asterisk.

Valid:
````java
/** This is valid single-line Javadoc. */
class TestClass {
  /**
   * This is valid Javadoc.
   */
  void validJavaDocMethod() {
  }
  /** This is valid single-line Javadoc. */
  void validSingleLineJavaDocMethod() {
  }
}
````

Invalid:
````java
class TestClass {
  /**
   *This is invalid Javadoc.
   */
  int invalidJavaDoc;
  /**This is invalid single-line Javadoc. */
  void invalidSingleLineJavaDocMethod() {
  }
}
````