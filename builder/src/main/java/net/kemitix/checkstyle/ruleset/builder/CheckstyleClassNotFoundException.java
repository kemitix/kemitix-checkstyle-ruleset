package net.kemitix.checkstyle.ruleset.builder;

/**
 * Raised when a Class to implement a Rule is not found.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class CheckstyleClassNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4752607844086418212L;

    /**
     * Constructor.
     *
     * @param name the name of the unimplemented rule
     */
    public CheckstyleClassNotFoundException(final String name) {
        super("No class found to implement " + name);
    }
}
