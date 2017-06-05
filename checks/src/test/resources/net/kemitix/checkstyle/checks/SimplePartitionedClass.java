package net.kemitix.checkstyle.checks;

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

    public String getFullFormat() {
        return left + format + right;
    }

    public String sayHello(final String name) {
        return String.format(getFullFormat(), name);
    }

}
