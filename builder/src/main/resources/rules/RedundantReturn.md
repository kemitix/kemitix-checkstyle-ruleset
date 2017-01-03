
Checks for redundant return statements.

Invalid:
````
HelloWorld() {
    doStuff();
    return;
}
void doStuff() {
    doMoreStuff();
    return;
}
````
