
Detects double brace initialization.

Rationale: Double brace initialization (set of Instance Initializers in class
body) may look cool, but it is considered as anti-pattern and should be avoided.
This is also can lead to a hard-to-detect memory leak, if the anonymous class
instance is returned outside and other object(s) hold reference to it. Created
anonymous class is not static, it holds an implicit reference to the outer class
instance. See this
[blog post](https://blog.jooq.org/2014/12/08/dont-be-clever-the-double-curly-braces-anti-pattern/)
and
[article](https://www.baeldung.com/java-double-brace-initialization)
for more details. Check ignores any comments and semicolons in class body.

Invalid:
````java
class MyClass {
    List<Integer> list1 = new ArrayList<>() { // violation
        {
            add(1);
        }
    };
    List<String> list2 = new ArrayList<>() { // violation
        ;
        // comments and semicolons are ignored
        {
            add("foo");
        }
    };
}
````
