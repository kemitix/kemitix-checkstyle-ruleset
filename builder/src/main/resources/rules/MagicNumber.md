
Checks that numeric literals are defined as constants. Being constants they then have a name that aids in making them non-magical.

The numbers -1, 0, 1 and 2 are not considered to be magical.

Valid:
````
static final int SECONDS_PER_DAY = 24 * 60 * 60;
static final Border STANDARD_BORDER = BorderFactory.createEmptyBorder(3, 3, 3, 3);
````

Invalid
````
String item = getItem(200);
````
