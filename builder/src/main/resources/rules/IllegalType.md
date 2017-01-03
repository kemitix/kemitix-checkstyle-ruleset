
Prevents use of implementation classes as variables, parameters or method returns. Use the interfaces instead.

Prevents variables, parameters and method returns from being any of the following:

* java.util.ArrayDeque
* java.util.ArrayList
* java.util.EnumMap
* java.util.EnumSet
* java.util.HashMap
* java.util.HashSet
* java.util.IdentityHashMap
* java.util.LinkedHashMap
* java.util.LinkedHashSet
* java.util.LinkedList
* java.util.PriorityQueue
* java.util.TreeMap
* java.util.TreeSet

Valid:
````
Set<String> getNames();
````

Invalid:
````
HashSet<String> getNames();
````
