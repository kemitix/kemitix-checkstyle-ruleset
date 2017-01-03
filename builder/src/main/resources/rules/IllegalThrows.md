
Prevent the following types from being `throw`n:

* java.lang.Exception
* java.lang.Throwable
* java.lang.RuntimeException

Valid:
````
throw new SpecificException("error");
````

Invalid:
````
throw new RuntimeException("boom!");
````
