
Requires the indentation of comments to match the surrounding code.

Valid:
````
/**
 * This is okay.
 */
int size = 20;

public foo() {
    super();
    // this is okay
}

public void foo11() {
    CheckUtils
        .getFirstNode(new DetailAST())
        .getFirstChild()
        .getNextSibling();
    // this is okay
}
````

Invalid:
````
    /**
     * This is NOT okay.
     */
int size = 20;

public foo() {
    super();
        // this is NOT okay
// this is NOT okay
}

public void foo11() {
    CheckUtils
        .getFirstNode(new DetailAST())
        .getFirstChild()
        .getNextSibling();
            // this is NOT okay
}
````
