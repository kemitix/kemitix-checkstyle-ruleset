package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RuleLoader {

    private static final String NEWLINE = "\n";

    private final RulesProperties rulesProperties;
    private final RuleReadmeLoader ruleReadmeLoader;
    private final SourcesProperties sourcesProperties;

    /**
     * Loads from the source, where the source is enabled, all rules that are
     * themselves enabled.
     *
     * @param sourceName the source to load from
     * @return a formatted string containing the enabled rules.
     */
    public String loadEnabled(final String sourceName) {
        if (!isEnabledRuleSource(sourceName)) {
            return "";
        }
        return readmeRules(rule ->
                rule.isFromSource(sourceName)
                        && rule.isEnabled());
    }

    /**
     * Loads from the source, where either the source is disabled or the rule
     * is itself disabled.
     *
     * @param sourceName the source to load from
     * @return a formatted string containing the disabled rules.
     */
    public String loadDisabled(final String sourceName) {
        final boolean enabledRuleSource = isEnabledRuleSource(sourceName);
        return readmeRules(rule ->
                rule.isFromSource(sourceName)
                        && (!enabledRuleSource || !rule.isEnabled()));
    }

    private boolean isEnabledRuleSource(final String sourceName) {
        return sourcesProperties.getSources().stream()
                .filter(RuleSource::isEnabled)
                .map(RuleSource::getName)
                .anyMatch(sourceName::equals);
    }

    private String readmeRules(final Predicate<Rule> predicate) {
        return rulesProperties.getRules().stream()
                .filter(predicate)
                .sorted(Rule::sortByName)
                .flatMap(ruleReadmeLoader::load)
                .collect(Collectors.joining(NEWLINE));
    }
}
