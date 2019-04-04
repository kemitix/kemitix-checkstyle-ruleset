package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public final String apply(final Rule rule) {
        return getCanonicalClassName(rule.getSource(), rule.getName());
    }

    private String getCanonicalClassName(
            final RuleSource source,
            final String name
                                        ) {
        Objects.requireNonNull(checkClasses, "init() method not called");
        return checkClasses.get(source)
                .stream()
                .sorted()
                .filter(classname -> byRuleName(classname, name))
                .findFirst()
                .orElseThrow(() -> new CheckstyleClassNotFoundException(name));
    }

    private boolean byRuleName(
            final String classname,
            final String name
                              ) {
        final String classNameWithDelimiter = "." + name;
        return classname.endsWith(classNameWithDelimiter) || classname.endsWith(classNameWithDelimiter + "Check");
    }
}
