
> This check cannot be suppressed.

Requires constants (static, final fields) to be all uppercase. Numbers and numbers are permitted but not as the first character.

Valid:
````
private static final int JACK_CARD = 11;
````

Invalid:
````
private static final int ace_card = 1;
private static final int 12_CARD = 12;
````
