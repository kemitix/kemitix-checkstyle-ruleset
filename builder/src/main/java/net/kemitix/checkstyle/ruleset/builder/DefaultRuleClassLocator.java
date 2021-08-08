package net.kemitix.checkstyle.ruleset.builder;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Default implementation of {@link RuleClassLocator}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
@Service
public class DefaultRuleClassLocator implements RuleClassLocator {

    private final transient Map<RuleSource, List<String>> checkClasses;
    private final transient SourcesProperties sourcesProperties = new SourcesProperties();

    /**
     * Creates a new instance of the class.
     *
     * @param checkClasses The list of check classes grouped by their source
     * @param sourcesProperties the source properties
     */
    public DefaultRuleClassLocator(
            final Map<RuleSource, List<String>> checkClasses,
            final SourcesProperties sourcesProperties
    ) {
        this.checkClasses = new HashMap<>(checkClasses);
        this.sourcesProperties.setSources(sourcesProperties.getSources());
    }

    @Override
    public final String apply(final Rule rule) {
        return getCanonicalClassName(rule.getSource(), rule.getName());
    }

    private String getCanonicalClassName(
            final String sourceName,
            final String name
    ) {
        Objects.requireNonNull(checkClasses, "init() method not called");
        return sourcesProperties.findSource(sourceName.toLowerCase(Locale.ENGLISH))
                .map(checkClasses::get)
                .flatMap(classes ->
                        classes.stream()
                                .sorted()
                                .filter(className -> byRuleName(className, name))
                                .findFirst())
                .orElseThrow(() -> new CheckstyleClassNotFoundException(name));
    }

    private boolean byRuleName(
            final String classname,
            final String name
    ) {
        final String delimitedName = "." + name;
        return classname.endsWith(delimitedName)
                || classname.endsWith(delimitedName + "Check");
    }
}
