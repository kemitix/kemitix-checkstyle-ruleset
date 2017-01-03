
Checks that non-whitespace characters on the same line are separated by no more than one whitespace.

Valid:
````
if (a < 0) {}
public long toNanos(long d) { return d; };
````

Invalid:
````
if  (a < 0) {}
public long toNanos(long d)  { return d;             };
````
