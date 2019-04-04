package net.kemitix.checkstyle.ruleset.builder;

import java.util.function.Function;

/**
 * Returns the canonical name of the class that implements a {@link Rule}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
interface RuleClassLocator extends Function<Rule, String> {

}
