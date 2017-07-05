
Checks that the `finalize()` implementation doesn't ignore the base class implementation, and doesn't *only* call the base class implementation. 

Valid:
```java
class Valid {
    protected void finalize() {
        try {
            doSomething();
        } finally {
            super.finalize();
        }
    }
}
```

Invalid:
```java
class InvalidNoEffect1 {
    protected void finalize() {
    }
}
class InvalidNoEffect2 {
    protected void finalize() {
        doSomething();
    }
}
class InvalidUseless {
    protected void finalize() {
        super.finalize();
    }
}
class InvalidPublic {
    public void finalize() {
        try {
            doSomething();
        } finally {
            super.finalize();
        }
    }
}
```
