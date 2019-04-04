package net.kemitix.checkstyle.ruleset.builder;

import java.util.List;
import java.util.function.Function;

/**
 * Scans a package for all classes.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
interface PackageScanner extends Function<RuleSource, List<String>> {

}
