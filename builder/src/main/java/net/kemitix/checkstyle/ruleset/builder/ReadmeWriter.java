/*
The MIT License (MIT)

Copyright (c) 2016 Paul Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Locale;
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

    @Override
    public void run(final String... args) throws Exception {
        final String readmeTemplate = readFile(templateProperties.getReadmeTemplate());
        final String index = ruleIndex();
        final String enabledCheckstyle = readmeRules(this::isEnabledCheckstyleRule);
        final String enabledSevntu = readmeRules(this::isEnabledSevntuRule);
        final String disabledCheckstyle = readmeRules(this::isDisabledCheckstyleRule);
        final String disabledSevntu = readmeRules(this::isDisabledSevntuRule);
        final byte[] readme = String.format(readmeTemplate, index, enabledCheckstyle, enabledSevntu, disabledCheckstyle,
                                            disabledSevntu
                                           )
                                    .getBytes(StandardCharsets.UTF_8);
        Files.write(outputProperties.getReadme(), readme, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String ruleIndex() {
        return rulesProperties.getRules()
                              .stream()
                              .sorted(Comparator.comparing(rule -> rule.getName().toLowerCase(Locale.ENGLISH)))
                              .map(this::formatRuleIndex)
                              .collect(Collectors.joining(NEWLINE));
    }

    private String formatRuleIndex(final Rule rule) {
        final String ruleLink = rule.getName()
                                    .toLowerCase(Locale.ENGLISH);
        final String source = rule.getSource()
                                  .toString()
                                  .toLowerCase(Locale.ENGLISH);
        final String enabled = rule.isEnabled() ? "enabled" : "disabled";
        final String insuppressible = rule.isInsuppressible() ? " - insuppressible" : "";
        return String.format("* [%s](#%s) - %s - %s%s", rule.getName(), ruleLink, source, enabled, insuppressible);
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
                              .flatMap(ruleReadmeLoader::load)
                              .collect(Collectors.joining(NEWLINE));
    }

    private String readFile(final Path file) throws IOException {
        return Files.lines(file, StandardCharsets.UTF_8)
                    .collect(Collectors.joining(NEWLINE));
    }

}
