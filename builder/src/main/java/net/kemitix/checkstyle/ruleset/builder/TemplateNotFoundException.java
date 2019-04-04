package net.kemitix.checkstyle.ruleset.builder;

import java.nio.file.Path;

/**
 * Raised when a rule template is not found.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
class TemplateNotFoundException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param template the missing template
     */
    TemplateNotFoundException(final Path template) {
        super("Missing template: " + template.toString());
    }
}
