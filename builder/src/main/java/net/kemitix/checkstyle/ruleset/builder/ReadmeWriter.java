package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Writes the README file.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
class ReadmeWriter implements CommandLineRunner {

    private static final String NEWLINE = "\n";

    private final TemplateProperties templateProperties;

    private final OutputProperties outputProperties;

    private final RulesProperties rulesProperties;

    private final RuleReadmeLoader ruleReadmeLoader;

    private final ReadmeIndexBuilder indexBuilder;

    @Override
    public void run(final String... args) throws Exception {
        final String readmeTemplate = readFile(templateProperties.getReadmeTemplate());
        final String enabledCheckstyle = readmeRules(this::isEnabledCheckstyleRule);
        final String enabledSevntu = readmeRules(this::isEnabledSevntuRule);
        final String disabledCheckstyle = readmeRules(this::isDisabledCheckstyleRule);
        final String disabledSevntu = readmeRules(this::isDisabledSevntuRule);
        final byte[] readme = String.format(readmeTemplate, indexBuilder.build(), enabledCheckstyle, enabledSevntu,
                                            disabledCheckstyle, disabledSevntu
                                           )
                                    .getBytes(StandardCharsets.UTF_8);
        Files.write(outputProperties.getReadme(), readme, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private boolean isEnabledSevntuRule(final Rule rule) {
        return rule.isEnabled() && RuleSource.SEVNTU.equals(rule.getSource());
    }

    private boolean isEnabledCheckstyleRule(final Rule rule) {
        return rule.isEnabled() && RuleSource.CHECKSTYLE.equals(rule.getSource());
    }

    private boolean isDisabledSevntuRule(final Rule rule) {
        return !rule.isEnabled() && RuleSource.SEVNTU.equals(rule.getSource());
    }

    private boolean isDisabledCheckstyleRule(final Rule rule) {
        return !rule.isEnabled() && RuleSource.CHECKSTYLE.equals(rule.getSource());
    }

    private String readmeRules(final Predicate<Rule> predicate) {
        return rulesProperties.getRules()
                              .stream()
                              .filter(predicate)
                              .sorted(Rule::sortByName)
                              .flatMap(ruleReadmeLoader::load)
                              .collect(Collectors.joining(NEWLINE));
    }

    private String readFile(final Path file) throws IOException {
        return String.join(NEWLINE, Files.readAllLines(file, StandardCharsets.UTF_8));
    }

}
