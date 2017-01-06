package net.kemitix.checkstyle.ruleset.builder;

/**
 * Creates the Rule Index for README.md in Markdown Format.
 *
 * @author Paul Campbell (paul.campbell@hubio.com)
 */
public interface ReadmeIndexBuilder {

    /**
     * Builds the Rule Index in Markdown Format.
     *
     * @return The rule index.
     */
    String build();
}
