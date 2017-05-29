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
import lombok.Getter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The origin of the rule.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public enum RuleSource {

    CHECKSTYLE("com.puppycrawl.tools.checkstyle"),
    SEVNTU("com.github.sevntu.checkstyle.checks");

    @Getter
    private final String basePackage;

    private final List<String> checkClasses;

    /**
     * Constructor.
     *
     * @param basePackage the base package that contains all checks from this source
     */
    RuleSource(final String basePackage) {
        this.basePackage = basePackage;
        try {
            checkClasses = ClassPath.from(getClass().getClassLoader())
                                    .getTopLevelClassesRecursive(basePackage)
                                    .stream()
                                    .map(ClassPath.ClassInfo::getName)
                                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new CheckstyleSourceLoadException(basePackage, e);
        }
    }

    public Stream<String> getCheckClasses() {
        return checkClasses.stream()
                           .sorted();
    }
}
