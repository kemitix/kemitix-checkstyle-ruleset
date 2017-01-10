
Restricts non-private methods to only `throws` 4 distinct Exception types. Exceptions should be hierarchical to allow catching suitable root Exceptions.

See [Effective Java], 2nd Edition, Chapter 9: Exceptions

Valid:
````
void doSomething() throws IllegalStateException, DowsingServiceException,
    BalancedBudgetException, ManagementInterferanceException {}
````

Invalid:
````
void doSomething() throws IllegalStateException,
    DowsingNotPermittedException, DowsingServiceNotReadyException,
    BalancedBudgetException, ManagementInterferanceException {}
````
