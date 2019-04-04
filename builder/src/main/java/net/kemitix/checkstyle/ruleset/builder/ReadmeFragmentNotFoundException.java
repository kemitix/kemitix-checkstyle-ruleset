package net.kemitix.checkstyle.ruleset.builder;

/**
 * Exception raised when a README documentation fragment can't be found for a rule.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
class ReadmeFragmentNotFoundException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param ruleName The name of the Rule.
     * @param cause    The cause.
     */
    ReadmeFragmentNotFoundException(final String ruleName, final Throwable cause) {
        super(ruleName, cause);
    }
}
