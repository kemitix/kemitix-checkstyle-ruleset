
This Check looks for useless empty public constructors. Class constructor is considered useless by this Check if and only if class has exactly one ctor, which is public, empty(one that has no statements) and default.

Valid:
````java
class ValidPrivateCtor {
    private ValidPrivateCtor() {
    }
}

class ValidOverloadedCtor {
    public ValidOverloadedCtor() {
    }
    public ValidOverloadedCtor(int i) {
    }
}
````

Invalid:
````java
class Invalid {
     public Invalid() {
     }
}
````
