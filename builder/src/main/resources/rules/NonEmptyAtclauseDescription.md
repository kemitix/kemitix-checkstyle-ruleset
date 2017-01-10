
Checks that the Javadoc clauses `@param`, `@return`, `@throws` and `@deprecated` all have descriptions.

Valid:
````
/**
 * Foo.
 *
 * @returns the foo
 */
````

Invalid:
````
/**
 * Foo.
 *
 * @returns
 */
````
