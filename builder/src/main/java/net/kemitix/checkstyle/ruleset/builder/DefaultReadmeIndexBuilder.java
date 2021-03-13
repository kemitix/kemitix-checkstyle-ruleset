package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Default Rule Index Builder.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
public class DefaultReadmeIndexBuilder implements ReadmeIndexBuilder {

    private static final String NEWLINE = "\n";

    private static final Locale LOCALE = Locale.ENGLISH;

    private final RulesProperties rulesProperties;

    @Override
    public final String build() {
        return rulesProperties.getRules()
                              .stream()
                              .sorted(Rule::sortByName)
                              .map(this::formatRuleRow)
                              .collect(Collectors.joining(NEWLINE));
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
        if (rule.isInsuppressible()) {
            return "No";
        }
        return "";
    }

    private String enabled(final Rule rule) {
        if (rule.isEnabled()) {
            return "Yes";
        }
        return "";
    }

    private String source(final Rule rule) {
        return rule.getSource()
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
