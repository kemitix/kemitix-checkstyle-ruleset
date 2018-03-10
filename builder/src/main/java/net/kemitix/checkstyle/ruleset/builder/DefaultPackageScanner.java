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
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import lombok.RequiredArgsConstructor;
import net.kemitix.conditional.Value;
import org.springframework.stereotype.Service;

import java.net.URLClassLoader;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Default implementation of {@link PackageScanner}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
@Service
@RequiredArgsConstructor
public class DefaultPackageScanner implements PackageScanner {

    private final ClassPath classPath;

    @Override
    public final Stream<String> apply(final RuleSource ruleSource) {
        return Value.<Stream<String>>where(isJava8())
                .then(scanPackageByClassPath(ruleSource.getBasePackage()))
                .otherwise(scanPackageByModulePath(ruleSource.getBasePackage()));
    }

    private boolean isJava8() {
        return getClass().getClassLoader() instanceof URLClassLoader;
    }

    private Supplier<Stream<String>> scanPackageByClassPath(final String basePackage) {
        return () -> classPath.getTopLevelClassesRecursive(basePackage)
                .stream()
                .map(ClassPath.ClassInfo::getName);
    }

    private Supplier<Stream<String>> scanPackageByModulePath(final String basePackage) {
        return () -> new FastClasspathScanner(basePackage)
                .scan()
                .getNamesOfAllStandardClasses()
                .stream()
                .filter(packageName -> packageName.startsWith(basePackage));
    }
}
