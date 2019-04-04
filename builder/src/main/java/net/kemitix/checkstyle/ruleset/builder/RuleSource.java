package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;

/**
 * The origin of the rule.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public enum RuleSource {

    CHECKSTYLE("com.puppycrawl.tools.checkstyle"),
    SEVNTU("com.github.sevntu.checkstyle.checks");

    @Getter
    private final String basePackage;


    /**
     * Constructor.
     *
     * @param basePackage the base package that contains all checks from this source
     */
    RuleSource(final String basePackage) {
        this.basePackage = basePackage;
    }
}
