package net.kemitix.checkstyle.checks;

class SimplePartitionedClass {

    private String left;

    private String right;

    private int counter = 0;

    private String format = "%s Hello, %s! %s";

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

    public String sayHello(final String name) {
        return String.format(format, getLeft(), name, getRight());
    }

}
