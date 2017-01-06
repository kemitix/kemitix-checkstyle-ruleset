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
