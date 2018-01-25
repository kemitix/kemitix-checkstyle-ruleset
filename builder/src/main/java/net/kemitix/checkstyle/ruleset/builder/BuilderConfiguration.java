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

import com.google.common.reflect.ClassPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Configuration for Builder.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
@Configuration
public class BuilderConfiguration {

    /**
     * Create the ClassPath used to scan packages.
     *
     * @return the ClassPath
     *
     * @throws IOException if there is an error
     */
    @Bean
    public ClassPath classPath() throws IOException {
        return ClassPath.from(getClass().getClassLoader());
    }

    /**
     * A Map of rules for each RuleSource.
     *
     * @param packageScanner the PackageScanner
     *
     * @return a Map with a list of check classes for each rule source
     */
    @Bean
    public Map<RuleSource, List<String>> checkClasses(final PackageScanner packageScanner) {
        return Stream.of(RuleSource.values())
                .map(toRuleSourceEntry(packageScanner))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Function<RuleSource, AbstractMap.SimpleEntry<RuleSource, List<String>>> toRuleSourceEntry(
            final PackageScanner packageScanner
                                                                                                            ) {
        return source -> new AbstractMap.SimpleEntry<>(
                source,
                classesInSource(source, packageScanner)
        );
    }

    private static List<String> classesInSource(
            final RuleSource source,
            final PackageScanner packageScanner
                                               ) {
        return packageScanner.apply(source)
                .collect(Collectors.toList());
    }
}
