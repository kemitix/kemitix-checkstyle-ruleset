package net.kemitix.checkstyle.checks;

class SimplePartitionedClass {

    // partition: left

    private String left;

    public String getLeft() {
        return left;
    }

    // partition: right

    private String right;

    public String getRight() {
        return right;
    }

}
