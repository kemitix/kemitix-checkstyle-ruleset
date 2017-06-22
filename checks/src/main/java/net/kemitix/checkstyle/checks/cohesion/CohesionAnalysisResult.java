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

package net.kemitix.checkstyle.checks.cohesion;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the results of performing a Cohesive Analysis on a class.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public final class CohesionAnalysisResult {

    public static final String DELIMITER = ", ";

    private Set<String> nonBeanMethods = new HashSet<>();

    private Set<Component> components = new HashSet<>();

    /**
     * Gets the components found within a class.
     *
     * @return a set of components
     */
    public Set<Component> getComponents() {
        return Collections.unmodifiableSet(components);
    }

    /**
     * Gets the non-bean methods of a class.
     *
     * @return a set of method signatures
     */
    Set<String> getNonBeanMethods() {
        return Collections.unmodifiableSet(nonBeanMethods);
    }

    /**
     * Add the methods.
     *
     * @param methods the methods to add
     */
    void addNonBeanMethods(final Set<String> methods) {
        nonBeanMethods.addAll(methods);
    }

    /**
     * Add the components.
     *
     * @param items the components to add
     */
    void addComponents(final Set<Component> items) {
        components.addAll(items);
    }

    /**
     * Get the non-private methods of each found component as a formatted string.
     *
     * @return a string
     */
    public String getPartitionedNonPrivateMethods() {
        return components.stream()
                         .map(component -> "[" + String.join(DELIMITER, getNonPrivateMethods(component)) + "]")
                         .collect(Collectors.joining(DELIMITER));
    }

    private List<String> getNonPrivateMethods(final Component component) {
        return Sets.intersection(component.getMembers(), getNonBeanMethods())
                   .stream()
                   .sorted()
                   .collect(Collectors.toList());
    }
}
