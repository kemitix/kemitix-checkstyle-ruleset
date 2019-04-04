package net.kemitix.checkstyle.ruleset.builder;

/**
 * Raised when there was an error writing a Checkstyle ruleset.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
class CheckstyleWriterException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param cause the cause
     */
    CheckstyleWriterException(final Throwable cause) {
        super(cause);
    }
}
