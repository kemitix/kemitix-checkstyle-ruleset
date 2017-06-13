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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import spoon.processing.AbstractProcessor;
import spoon.processing.Processor;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtReference;
import spoon.reflect.visitor.Filter;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processes a class for analysis by a {@link CohesionAnalyser}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class SpoonCohesionProcessor extends AbstractProcessor<CtClass> {

    private final Filter<CtInvocation> methodsInvokedFilter;

    private final CohesionAnalyser cohesionAnalyser;

    private final Consumer<CohesionAnalysisResult> resultConsumer;

    /**
     * Create a new instance of SpoonCohesionProcessor.
     *
     * @param invocationFilter filter to select methods that are invoked
     * @param cohesionAnalyser analyser to examine field usage by each method
     * @param resultConsumer   consumer to process the results
     *
     * @return a Spoon Processor for processing each class file
     */
    static Processor<CtClass> create(
            final Filter<CtInvocation> invocationFilter, final CohesionAnalyser cohesionAnalyser,
            final Consumer<CohesionAnalysisResult> resultConsumer
                                    ) {
        return new SpoonCohesionProcessor(invocationFilter, cohesionAnalyser, resultConsumer);
    }

    @Override
    public void process(final CtClass theClass) {
        final Map<String, Set<String>> usedByMethod = getItemsUsedByEachMethod(theClass);
        final Set<String> nonPrivateMethods = getNonPrivateMethods(theClass);
        cohesionAnalyser.analyse(usedByMethod, nonPrivateMethods, resultConsumer);
    }

    private Map<String, Set<String>> getItemsUsedByEachMethod(final CtClass theClass) {
        return getMethods(theClass).map(this::getItemsUsed)
                                   .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                                                             AbstractMap.SimpleEntry::getValue
                                                            ));
    }

    private AbstractMap.SimpleEntry<String, Set<String>> getItemsUsed(final CtMethod<?> method) {
        return new AbstractMap.SimpleEntry<>(method.getSignature(),
                                             Sets.union(getFieldsAccessed(method), getMethodsInvoked(method))
        );
    }

    private Set<String> getNonPrivateMethods(final CtClass element) {
        return getMethods(element).filter(this::methodIsNotPrivate)
                                  .map(CtMethod::getSignature)
                                  .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private Stream<CtMethod<?>> getMethods(final CtClass element) {
        return element.getMethods()
                      .stream();
    }

    private Set<String> getMethodsInvoked(final CtMethod<?> method) {
        return method.filterChildren(methodsInvokedFilter)
                     .list(CtInvocation.class)
                     .stream()
                     .map(CtInvocation::getExecutable)
                     .map(CtExecutableReference::getDeclaration)
                     .map(CtExecutable::getSignature)
                     .collect(Collectors.toSet());
    }

    private Set<String> getFieldsAccessed(final CtMethod<?> method) {
        return method.filterChildren(element -> true)
                     .list(CtFieldAccess.class)
                     .stream()
                     .map(CtFieldAccess::getVariable)
                     .map(CtReference::getSimpleName)
                     .distinct()
                     .collect(Collectors.toSet());
    }

    private boolean methodIsNotPrivate(final CtMethod<?> ctMethod) {
        return !methodIsPrivate(ctMethod);
    }

    private boolean methodIsPrivate(final CtMethod<?> ctMethod) {
        return ctMethod.hasModifier(ModifierKind.PRIVATE);
    }
}
