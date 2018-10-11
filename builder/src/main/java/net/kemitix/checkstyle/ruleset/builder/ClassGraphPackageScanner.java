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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link PackageScanner} using ClassGraph.
 *
 * @see <a href="https://github.com/classgraph/classgraph/">ClassGraph</a>
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
@Service
public class ClassGraphPackageScanner implements PackageScanner {

    private final ClassGraph classGraph = new ClassGraph();

    @Override
    public final List<String> apply(final RuleSource ruleSource) {
        final String basePackage = ruleSource.getBasePackage();
        try (ScanResult scanResult = scanPackage(classGraph, basePackage)) {
            return scanResult
                    .getAllStandardClasses()
                    .getNames()
                    .stream()
                    .filter(packageName -> packageName.startsWith(basePackage))
                    .collect(Collectors.toList());
        }
    }

    private static ScanResult scanPackage(final ClassGraph classGraph, final String basePackage) {
        return classGraph
                .whitelistPackages(basePackage)
                .scan();
    }

}
