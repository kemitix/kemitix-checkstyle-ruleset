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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Default implementation of {@link RuleClassLocator}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
@Service
@RequiredArgsConstructor
public class DefaultRuleClassLocator implements RuleClassLocator {

    private final PackageScanner packageScanner;

    private Map<RuleSource, List<String>> checkClasses;

    /**
     * Initialise class lists for {@link RuleSource}s.
     */
    @PostConstruct
    public final void init() {
        checkClasses = Stream.of(RuleSource.values())
                             .map(source -> new AbstractMap.SimpleEntry<>(
                                     source, packageScanner.apply(source)
                                                           .collect(Collectors.toList())))
                             .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public final String apply(final Rule rule) {
        return getCanonicalClassName(rule.getSource(), rule.getName());
    }

    private String getCanonicalClassName(final RuleSource source, final String name) {
        Objects.requireNonNull(checkClasses, "init() method not called");
        return checkClasses.get(source)
                           .stream()
                           .sorted()
                           .filter(classname -> byRuleName(classname, name))
                           .findFirst()
                           .orElseThrow(() -> new CheckstyleClassNotFoundException(name));
    }

    private boolean byRuleName(final String classname, final String name) {
        final String classNameWithDelimiter = "." + name;
        return classname.endsWith(classNameWithDelimiter) || classname.endsWith(classNameWithDelimiter + "Check");
    }
}
