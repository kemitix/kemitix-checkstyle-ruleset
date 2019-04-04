package net.kemitix.checkstyle.ruleset.builder;

import java.util.stream.Stream;

/**
 * Loads README fragment for rule.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@FunctionalInterface
interface RuleReadmeLoader {

    /**
     * Loads the README fragment for rule.
     *
     * @param rule The Rule.
     *
     * @return A Stream of the readme fragment.
     */
    public abstract Stream<String> load(Rule rule);
}
