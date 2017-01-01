
Checks for unused imports. Does not inspect wildcard imports, which should be blocked by [AvoidStarImport](#avoidstarimport) anyway.

Imports are unused if:

* They are not referenced in the file.
* It duplicates another import.
* It import from the `java.lang` package.
* It imports a class from the same package.
* It is only references from the Javadoc.
