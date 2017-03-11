
Checks for the existence of forbidden java file names.

File names are forbidden if they match the pattern `(.sync-conflict-| conflicted copy )`.

N.B. only `*.java` files are checked.

This check is intended to detect Syncthing and Dropbox conflict files.

e.g.
````
DataClass (Bob's conflicted copy 2017-03-11).java
DataClass.sync-conflict-20170311-1648.java
````

