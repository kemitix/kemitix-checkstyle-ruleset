
Checks that there is no whitespace before the comma operator (','), statement terminator (';'), postfix increment ('++') or postfix decrement ('--').

Valid:
````
int y = {1, 2};
doSomething();
i++;
i--;
````

Invalid:
````
int y = {1 , 2};
doSomething() ;
i ++;
i --;
````
