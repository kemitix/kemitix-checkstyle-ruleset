
This checks enforces whitespace before array initializer.

Valid:
````java
int[] ints = new int[] {
    0, 1, 2, 3
};

int[] tab = new int[]
                {0, 1, 2, 3};
````

Invalid:
````java
int[] ints = new int[]{0, 1, 2, 3};
````
