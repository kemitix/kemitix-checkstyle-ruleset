
Checks the line wrapping around separators.

* The comma separator (',') should be at the end of the line.
* The dot separator ('.') should be on the new line.

Valid:
````
doSomething(alpha, beta,
    gamma);
doSomethingElse().stream()
                 .forEach(System.out::println);
````

Invalid:
````
doSomething(alpha, beta
    , gamma);
doSomethingElse().stream().
                  forEach(System.out::println);
````
