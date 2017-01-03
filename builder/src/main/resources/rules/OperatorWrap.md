
Checks that when wrapping a line on an operator that the operator appears on the new line.

Valid:
````
int answer = getTheAnswerToLife() + getTheAnswerToTheUniverse()
    + getTheAnswerToEverything();
````

Invalid:
````
int answer = getTheAnswerToLife() + getTheAnswerToTheUniverse() +
    getTheAnswerToEverything();
````
