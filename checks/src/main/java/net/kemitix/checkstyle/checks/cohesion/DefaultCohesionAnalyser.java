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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link CohesionAnalyser}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RequiredArgsConstructor
public class DefaultCohesionAnalyser implements CohesionAnalyser {

    @Override
    public final void analyse(
            @NonNull final Map<String, Set<String>> usedByMethod, @NonNull final Set<String> nonPrivateMethods,
            @NonNull final Consumer<CohesionAnalysisResult> resultConsumer
                             ) {
        final CohesionAnalysisResult result = new CohesionAnalysisResult();
        result.addNonBeanMethods(getNonBeanNonPrivateMethods(usedByMethod, nonPrivateMethods));
        result.addComponents(findComponents(usedByMethod));
        resultConsumer.accept(result);
    }

    private Set<String> getNonBeanNonPrivateMethods(
            final Map<String, Set<String>> usedByMethod, final Set<String> nonPrivateMethods
                                                   ) {
        return nonPrivateMethods.stream()
                                .filter(m -> isNotBeanMethod(m, usedByMethod.get(m)))
                                .collect(Collectors.toSet());
    }

    private Set<Component> findComponents(final Map<String, Set<String>> usedByMethod) {
        final Set<Component> components = usedByMethodAsComponents(usedByMethod);
        return mergeComponents(components);
    }

    private Set<Component> usedByMethodAsComponents(final Map<String, Set<String>> usedByMethod) {
        return usedByMethod.entrySet()
                           .stream()
                           .filter(e -> isNotBeanMethod(e.getKey(), usedByMethod.get(e.getKey())))
                           .map(this::componentFromEntry)
                           .collect(Collectors.toSet());
    }

    private Set<Component> mergeComponents(final Set<Component> components) {
        final Set<Component> merged = new HashSet<>();
        components.forEach(component -> {
            final Optional<Component> existing = merged.stream()
                                                       .filter(target -> overlap(component, target))
                                                       .findFirst();
            if (existing.isPresent()) {
                existing.get()
                        .merge(component);
            } else {
                merged.add(component);
            }
        });
        return merged;
    }

    private boolean overlap(final Component a, final Component b) {
        final Set<String> aMembers = a.getMembers();
        final Set<String> bMembers = b.getMembers();
        final boolean hasCommonMembers = Sets.intersection(aMembers, bMembers)
                                             .isEmpty();
        return !hasCommonMembers;
    }

    private Component componentFromEntry(final Map.Entry<String, Set<String>> entry) {
        final Set<String> members = new HashSet<>(entry.getValue());
        members.add(entry.getKey());
        return Component.from(members);
    }

    private boolean isNotBeanMethod(final String method, final Set<String> fields) {
        return !isBeanMethod(method, fields);
    }

    private boolean isBeanMethod(final String method, final Set<String> fields) {
        if (fields != null && fields.size() == 1) {
            final String fieldAccessed = fields.toArray(new String[1])[0];
            final String methodName = method.toLowerCase();
            return isBeanMethod(methodName, fieldAccessed);
        } else {
            return false;
        }
    }

    private boolean isBeanMethod(final String method, final String field) {
        return isSetter(method, field) || isGetter(method, field);
    }

    private boolean isGetter(final String method, final String field) {
        final boolean isPlainGetter = method.endsWith(String.format(" get%s()", field));
        final boolean isBooleanGetter = String.format("java.lang.boolean is%s()", field)
                                              .equals(method);
        final boolean isPrimitiveBooleanGetter = String.format("boolean is%s()", field)
                                                       .equals(method);
        return isPlainGetter || isBooleanGetter || isPrimitiveBooleanGetter;
    }

    private boolean isSetter(final String method, final String field) {
        return method.startsWith("void set" + field + "(");
    }

}
