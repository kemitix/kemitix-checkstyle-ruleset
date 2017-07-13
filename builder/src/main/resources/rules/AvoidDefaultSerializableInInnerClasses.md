
Prevent the use of default serialization methods on inner classes. If an inner class needs to implement the Serializable interface, then it *must* implement both `writeObject()` and `readObject()` methods.
