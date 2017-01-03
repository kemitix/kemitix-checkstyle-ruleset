
Checks that `long` numeric literal values are marked by an upper-case ell ('L'). The lower-case ell ('l') can be mistaken for the numeral one ('1').

Valid:
````
long id = 12345L;
````

Invalid:
````
long id = 12345l;
````
