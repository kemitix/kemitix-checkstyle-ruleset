
Checks that there is no whitespace after the array init ('{'), prefix increment ('++'), prefix decrement ('--'), bitwise complement ('~'), logical complement ('!'), array declaration ('[' in `int[] a;`) or array index operator ('[' in `a[2]`).

Valid:
````
int[] y = {1, 2};
++i;
--i;
int j = -1;
int k = +1;
int l = ~2;
boolean state = !isReady();
int b = o.getValue();
int[] a;
int d = a[2];
````

Invalid:
````
int[] y = { 1, 2 };
++ i;
-- i;
int j = - 1;
int k = + 1;
int l = ~ 2;
boolean state = ! isReady();
int b = o. getValue();
int[ ] a;
int d = a[ 2];
````
