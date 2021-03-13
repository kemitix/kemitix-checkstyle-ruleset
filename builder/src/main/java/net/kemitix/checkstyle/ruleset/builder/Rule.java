package net.kemitix.checkstyle.ruleset.builder;

import com.speedment.common.mapstream.MapStream;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A single Checkstyle Check.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Setter
@Getter
public class Rule {

    private static final Locale LOCALE = Locale.ENGLISH;

    private static final String NO_PROPERTIES = "<module name=\"%s\"/>";

    private static final String WITH_PROPERTIES = "<module name=\"%s\">%n%s%n</module>";

    /**
     * Configuration properties.
     */
    @SuppressWarnings("PMD.UseConcurrentHashMap")
    private final Map<String, String> properties = new HashMap<>();

    /**
     * The name of the rule's Check class.
     */
    private String name;

    /**
     * The parent module.
     */
    private RuleParent parent;

    /**
     * The first level the rule is enabled on.
     */
    private RuleLevel level;

    /**
     * Whether the rule is enabled.
     */
    private boolean enabled;

    /**
     * The source of the rule.
     */
    private String source;

    /**
     * URI for full official documentation.
     */
    private URI uri;

    /**
     * Flag to indicate rules that can not be suppressed (via {@code @SuppressWarnings}.
     */
    private boolean insuppressible;

    /**
     * The reason a rule has been disabled.
     */
    private String reason;

    /**
     * Compare two Rules lexicographically for sorting by rule name, ignoring case.
     *
     * @param left  the first rule
     * @param right the second rule
     *
     * @return the value 0 if the rules are equal; a value less than 0 if the left string is lexicographically less than
     * the right string; and a value greater than 0 if the left string is lexicographically greater than the right
     * string.
     */
    protected static int sortByName(final Rule left, final Rule right) {
        return left.getName().toLowerCase(LOCALE)
                .compareTo(right.getName().toLowerCase(LOCALE));
    }

    /**
     * Predicate to check that the Rule has the given RuleParent.
     *
     * @param ruleParent the RuleParent to match
     *
     * @return a Predicate to check a Rule
     */
    static Predicate<Rule> hasParent(final RuleParent ruleParent) {
        return rule -> ruleParent == rule.getParent();
    }

    /**
     * Predicate to check that the Rule is included in the given RuleLevel.
     *
     * @param ruleLevel the RuleLevel to match
     *
     * @return a Predicate to check a Rule
     */
    static Predicate<Rule> isIncludedInLevel(final RuleLevel ruleLevel) {
        return rule -> ruleLevel.compareTo(rule.getLevel()) >= 0;
    }

    private static String formatProperties(final Map<String, String> properties) {
        return MapStream.of(properties)
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map((k, v) -> String.format("        <property name=\"%s\" value=\"%s\"/>", k, v))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static boolean hasProperties(final Rule rule) {
        return !rule.getProperties().isEmpty();
    }

    /**
     * Formats the Rule as an XML module fragment.
     *
     * @param rule the Rule to format
     * @param ruleClassname the classname for the Rule
     *
     * @return an XML {@code <module/>} fragment
     */
    static String asModule(
            final String ruleClassname,
            final Rule rule
    ) {
        if (hasProperties(rule)) {
            return moduleWithParameters(rule, ruleClassname);
        } else {
            return moduleNoParameters(ruleClassname);
        }
    }

    private static String moduleNoParameters(
            final String ruleClassname
    ) {
        return String.format(NO_PROPERTIES, ruleClassname);
    }

    private static String moduleWithParameters(
            final Rule rule,
            final String ruleClassname
    ) {
        return String.format(WITH_PROPERTIES, ruleClassname, formatProperties(rule.getProperties()));
    }

    /**
     * Checks that this rule comes from the source named.
     *
     * @param sourceName the name of the source to check against
     * @return true if this rule comes from the source
     */
    public boolean isFromSource(final String sourceName) {
        return getSource()
                .equals(sourceName);
    }

    /**
     * The name of the source for the rule.
     *
     * @return the source name, in lowercase.
     */
    public String getSource() {
        return source.toLowerCase(Locale.ENGLISH);
    }
}
