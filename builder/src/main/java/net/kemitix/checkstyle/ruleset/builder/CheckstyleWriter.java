package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Writes the Checkstyle XML files.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Slf4j
@Component
@RequiredArgsConstructor
class CheckstyleWriter implements CommandLineRunner {

    private final OutputProperties outputProperties;

    private final TemplateProperties templateProperties;

    private final RulesProperties rulesProperties;

    private final RuleClassLocator ruleClassLocator;

    private final SourcesProperties sourcesProperties;
    private final Set<String> enabledSourceNames = new HashSet<>();

    @PostConstruct
    public void init() {
        enabledSourceNames.addAll(
                sourcesProperties.getSources()
                        .stream()
                        .filter(RuleSource::isEnabled)
                        .map(RuleSource::getName)
                        .collect(Collectors.toSet()));
    }

    private static Predicate<RuleLevel> excludeUnspecifiedRuleLevel() {
        return level -> !level.equals(RuleLevel.UNSPECIFIED);
    }

    private static void writeRuleset(
            final Path filePath,
            final String content
    ) throws IOException {
        Files.write(filePath, asList(content.split(System.lineSeparator())), StandardCharsets.UTF_8);
    }

    private static String ruleset(
            final String checkerRules,
            final String treeWalkerRules,
            final String template
    ) {
        return String.format(template, checkerRules, treeWalkerRules);
    }

    private static boolean fileExists(final File file) {
        return file.exists();
    }

    private static void writeCheckstyleFileWithException(
            final Path outputPath,
            final String checkerRules,
            final String treeWalkerRules,
            final Path template
    ) throws IOException {
        if (fileExists(template.toFile())) {
            log.info("Writing ruleset: {}", outputPath);
            final String xmlTemplate = new String(Files.readAllBytes(template), StandardCharsets.UTF_8);
            writeRuleset(outputPath, ruleset(checkerRules, treeWalkerRules, xmlTemplate));
        } else {
            throw new TemplateNotFoundException(template);
        }
    }

    @Override
    public void run(final String... args) {
        Stream.of(RuleLevel.values())
                .filter(excludeUnspecifiedRuleLevel())
                .forEach(this::writeCheckstyleFile);
    }

    private void writeCheckstyleFile(final RuleLevel ruleLevel) {
        final Path outputPath = outputPath(ruleLevel);
        final String checkerRules = enabledRules(ruleLevel, RuleParent.CHECKER);
        final String treeWalkerRules = enabledRules(ruleLevel, RuleParent.TREEWALKER);
        final Path template = templateProperties.getCheckstyleXml();
        try {
            writeCheckstyleFileWithException(outputPath, checkerRules, treeWalkerRules, template);
        } catch (IOException e) {
            throw new CheckstyleWriterException(e);
        }
    }

    private Path outputPath(final RuleLevel ruleLevel) {
        return outputProperties.getDirectory()
                .resolve(outputProperties.getRulesetFiles()
                        .get(ruleLevel));
    }

    private String enabledRules(
            final RuleLevel ruleLevel,
            final RuleParent ruleParent
    ) {
        return rulesProperties.getRules()
                .stream()
                .filter(Rule::isEnabled)
                .filter(rule -> enabledSourceNames.contains(rule.getSource()))
                .filter(Rule.hasParent(ruleParent))
                .filter(Rule.isIncludedInLevel(ruleLevel))
                .map(rule -> Rule.asModule(ruleClassLocator.apply(rule), rule))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
