package net.kemitix.checkstyle.ruleset.builder;

import java.io.IOException;

/**
 * Raised when there is an error scanning for check classes.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class CheckstyleSourceLoadException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param basePackage the base package classes were being loaded from
     * @param cause       the cause
     */
    public CheckstyleSourceLoadException(final String basePackage, final IOException cause) {
        super("Error loading checkstyle rules from package: " + basePackage, cause);
    }
}
