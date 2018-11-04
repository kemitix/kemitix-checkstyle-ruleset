
Ternary statements shouldn't have `Boolean` values as results.

Valid:
````
Boolean set = isSet();
Boolean notReady = !isReady();
````

Invalid:
````
Boolean set = isSet() ? True : False;
Boolean notReady = isReady() ? False : True;
````
