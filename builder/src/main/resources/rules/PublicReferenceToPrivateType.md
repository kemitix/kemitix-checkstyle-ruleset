
Checks that a type is not exposed outside its declared scope.

Invalid:
````
public class OuterClass {
    public InnerClass inner = new InnerClass();
    public SiblingClass sibling = new SiblingClass();
    public InnerClass getValue() { return new InnerClass(); }
    public SiblingClass getSibling() { return new SiblingClass(); }
    private class InnerClass {}
}
class SiblingClass {}
````
