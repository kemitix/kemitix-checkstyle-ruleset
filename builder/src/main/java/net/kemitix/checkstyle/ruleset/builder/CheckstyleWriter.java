/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Paul Campbell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
                .filter(Rule.hasParent(ruleParent))
                .filter(Rule.isIncludedInLevel(ruleLevel))
                .map(rule -> Rule.asModule(ruleClassLocator.apply(rule), rule))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
