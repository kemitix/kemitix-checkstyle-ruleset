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

import com.speedment.common.mapstream.MapStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Writes the Checkstyle XML files.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Slf4j
@Component
@RequiredArgsConstructor
class CheckstyleWriter implements CommandLineRunner {

    private static final String NEWLINE = System.getProperty("line.separator");

    private final OutputProperties outputProperties;

    private final TemplateProperties templateProperties;

    private final RulesProperties rulesProperties;

    private final RuleClassLocator ruleClassLocator;

    @Override
    public void run(final String... args) {
        Stream.of(RuleLevel.values())
              .filter(level -> !level.equals(RuleLevel.UNSPECIFIED))
              .forEach(this::writeCheckstyleFile);
    }

    private void writeCheckstyleFile(final RuleLevel ruleLevel) {
        val xmlFile = outputProperties.getDirectory()
                                      .resolve(outputProperties.getRulesetFiles()
                                                               .get(ruleLevel));
        val checkerRules = rulesProperties.getRules()
                                          .stream()
                                          .filter(Rule::isEnabled)
                                          .filter(rule -> RuleParent.CHECKER.equals(rule.getParent()))
                                          .filter(rule -> ruleLevel.compareTo(rule.getLevel()) >= 0)
                                          .map(this::formatRuleAsModule)
                                          .collect(Collectors.joining(NEWLINE));
        val treeWalkerRules = rulesProperties.getRules()
                                             .stream()
                                             .filter(Rule::isEnabled)
                                             .filter(rule -> RuleParent.TREEWALKER.equals(rule.getParent()))
                                             .filter(rule -> ruleLevel.compareTo(rule.getLevel()) >= 0)
                                             .map(this::formatRuleAsModule)
                                             .collect(Collectors.joining(NEWLINE));
        try {
            val checkstyleXmlTemplate = templateProperties.getCheckstyleXml();
            if (checkstyleXmlTemplate.toFile()
                                     .exists()) {
                val bytes = Files.readAllBytes(checkstyleXmlTemplate);
                val template = new String(bytes, StandardCharsets.UTF_8);
                val output = Arrays.asList(String.format(template, checkerRules, treeWalkerRules)
                                                 .split(NEWLINE));
                log.info("Writing xmlFile: {}", xmlFile);
                Files.write(xmlFile, output, StandardCharsets.UTF_8);
            } else {
                throw new TemplateNotFoundException(checkstyleXmlTemplate);
            }
        } catch (IOException e) {
            throw new CheckstyleWriterException(e);
        }
    }

    private String formatRuleAsModule(final Rule rule) {
        if (rule.getProperties()
                .isEmpty()) {
            return String.format("<module name=\"%s\"/>", ruleClassLocator.apply(rule));
        }
        return String.format("<module name=\"%s\">%n    %s%n</module>", ruleClassLocator.apply(rule),
                             formatProperties(rule.getProperties())
                            );
    }

    private String formatProperties(final Map<String, String> properties) {
        return MapStream.of(properties)
                        .map((k, v) -> String.format("<property name=\"%s\" value=\"%s\"/>", k, v))
                        .collect(Collectors.joining(NEWLINE));
    }
}
