
Both the `@Deprecated` annotation and the Javadoc tag `@deprecated` must be used in pairs.

Valid:
````
/**
  * Foo.
  *
  * @deprecated
  */
@Deprecated
void foo() {}
````

Invalid:
````
/**
  * Foo.
  */
@Deprecated
void foo() {}

/**
  * Bar.
  *
  * @deprecated
  */
void bar() {}
````
