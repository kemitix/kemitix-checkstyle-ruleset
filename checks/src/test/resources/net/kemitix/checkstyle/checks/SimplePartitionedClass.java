package net.kemitix.checkstyle.checks;

import net.kemitix.checkstyle.checks.SimpleClass;

class SimplePartitionedClass {

    private String left;

    private String right;

    private int counter = 0;

    private String format = "Hello, %s!";

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public int counter() {
        return counter;
    }

    public void increment() {
        counter += 1;
    }

    public String getFullFormat(final String ignored) {
        final String fullFormat = left + format + right;
        return fullFormat;
    }

    public String sayHello(final SimpleClass name) {
        return String.format(getFullFormat("ignore me"), name);
    }

}
