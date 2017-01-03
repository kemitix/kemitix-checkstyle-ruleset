
Checks that `Exception` classes are immutable. However, you can still call `setStackTrace`.

Classes checked are those whose name ends with the following. Or that the class they extend does.

* `Exception`
* `Error`
* `Throwable`
