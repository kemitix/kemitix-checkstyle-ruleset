package net.kemitix.checkstyle.ruleset.builder;

/**
 * Creates the Rule Index for README.md in Markdown Format.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public interface ReadmeIndexBuilder {

    /**
     * Builds the Rule Index in Markdown Format.
     *
     * @return The rule index.
     */
    String build();
}
