
Judicous use of `@SuppressWarnings("designdorextension")` is recommended for this check.

This check is primarily intended for use in library modules rather than applications.

Classes that are deemed by their designer to be 'designed for extension', must take steps to prevent a subclass from breaking the class's behaviour by overriding methods incorrectly. This can be done through a combination of:

* Defining 'hook' methods with empty implementations that subclasses override to add their own behaviour
* Marking methods that are non-private and non-static as abstract or final

> See the official [Checkstyle documentation](http://checkstyle.sourceforge.net/config_design.html#DesignForExtension) for more details and [Effective Java], 2nd Edition by Josh Bloch: Item 17: Design and document for inheritance or else prohibit it.
