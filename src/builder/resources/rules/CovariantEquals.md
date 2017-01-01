
> This check cannot be suppressed.

Checks that classes which define a covariant equals() method also override method equals(Object).

Valid:
````
class Test {
    public boolean equals(Test i) {
        return false;
    }

    public boolean equals(Object i) {
       return false;
    }
}
````

Invalid:
````
class Test {
    public boolean equals(Test i) {
        return false;
    }
}
````
