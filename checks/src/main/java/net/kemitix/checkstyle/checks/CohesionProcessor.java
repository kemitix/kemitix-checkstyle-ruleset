package net.kemitix.checkstyle.checks;

import lombok.RequiredArgsConstructor;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtReference;
import spoon.reflect.visitor.Filter;

import java.util.HashMap;
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
@RequiredArgsConstructor
public class CohesionProcessor extends AbstractProcessor<CtClass> {

    private final Consumer<CohesionAnalysisResult> resultConsumer;

    private final Filter<CtFieldAccess> fieldsAccessedFilter;

    private final Filter<CtInvocation> methodsInvokedFilter;

    private final CohesionAnalyser cohesionAnalyser;

    @Override
    public void process(final CtClass theClass) {
        final Map<String, Set<String>> fieldsAccessed = new HashMap<>();
        final Map<String, Set<String>> methodsInvoked = new HashMap<>();
        getMethods(theClass).forEach(method -> {
            final String signature = method.getSignature();
            fieldsAccessed.put(signature, getFieldsAccessed(method));
            methodsInvoked.put(signature, getMethodsInvoked(method));
        });
        final Set<String> nonPrivateMethods = getNonPrivateMethods(theClass);
        cohesionAnalyser.analyse(fieldsAccessed, methodsInvoked, nonPrivateMethods, resultConsumer);
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
        return method.filterChildren(fieldsAccessedFilter)
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
