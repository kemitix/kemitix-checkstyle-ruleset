package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class DefaultRuleClassLocator implements RuleClassLocator {

    private final Map<RuleSource, List<String>> checkClasses;
    private final SourcesProperties sourcesProperties;

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
