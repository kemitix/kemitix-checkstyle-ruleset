package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Default Rule Index Builder.
 *
 * @author Paul Campbell (paul.campbell@hubio.com)
 */
@Component
@RequiredArgsConstructor
public class DefaultReadmeIndexBuilder implements ReadmeIndexBuilder {

    private static final String NEWLINE = "\n";

    private static final String HEADERROW = "Rule|Level|Source|Enabled|Suppressable\n";

    private static final String SEPARATOR = "----|-----|------|-------|------------\n";

    private static final Locale LOCALE = Locale.ENGLISH;

    private final RulesProperties rulesProperties;

    @Override
    public String build() {
        return HEADERROW + SEPARATOR + rulesProperties.getRules()
                                                      .stream()
                                                      .sorted(Comparator.comparing(lowerCaseRuleName()))
                                                      .map(this::formatRuleRow)
                                                      .collect(Collectors.joining(NEWLINE));
    }

    private Function<Rule, String> lowerCaseRuleName() {
        return this::getLowerCaseRuleName;
    }

    private String getLowerCaseRuleName(final Rule rule) {
        return rule.getName()
                   .toLowerCase(LOCALE);
    }

    private String formatRuleRow(final Rule rule) {
        return String.format(
                "%s|%s|%s|%s|%s", link(rule), level(rule), source(rule), enabled(rule), suppressible(rule));
    }

    private String suppressible(final Rule rule) {
        return rule.isInsuppressible() ? "No" : "";
    }

    private String enabled(final Rule rule) {
        return rule.isEnabled() ? "Yes" : "";
    }

    private String source(final Rule rule) {
        return rule.getSource()
                   .toString()
                   .toLowerCase(LOCALE);
    }

    private String link(final Rule rule) {
        return String.format("[%s](#%s)", rule.getName(), getLowerCaseRuleName(rule));
    }

    private String level(final Rule rule) {
        return Optional.ofNullable(rule.getLevel())
                       .orElse(RuleLevel.UNSPECIFIED)
                       .toString()
                       .toLowerCase(LOCALE);
    }
}
