package net.kemitix.checkstyle.checks;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtReference;
import spoon.reflect.visitor.Filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class PartitionedProcessor extends AbstractProcessor<CtClass> {

    private final Consumer<String> logger;

    private final Filter<CtFieldAccess> fieldsAccessed;

    private final Filter<CtInvocation> methodsInvoked;

    private final Map<String, Set<String>> fieldsAccessedByMethod = new HashMap<>();

    private final Map<String, Set<String>> methodsInvokedByMethod = new HashMap<>();

    PartitionedProcessor(
            final Consumer<String> logger, final Filter<CtFieldAccess> fieldsAccessed,
            final Filter<CtInvocation> methodsInvoked
                        ) {
        this.logger = logger;
        this.fieldsAccessed = fieldsAccessed;
        this.methodsInvoked = methodsInvoked;
    }

    @Override
    public void process(final CtClass theClass) {
        System.out.println(String.format("class: %s", theClass.getSimpleName()));
        printFieldNames(getFields(theClass));
        printMethodNames(getNonPrivateMethods(theClass));

        getMethods(theClass).forEach(this::recordFieldsUsedBy);

        // collate usage results
        System.out.println("fieldsAccessedByMethod = " + fieldsAccessedByMethod);
        System.out.println("methodsInvokedByMethod = " + methodsInvokedByMethod);

        methodsInvokedByMethod.forEach(this::mergeFieldsInInvokedMethods);

        System.out.println("fieldsAccessedByMethod = " + fieldsAccessedByMethod);
    }

    private Stream<CtMethod<?>> getNonPrivateMethods(final CtClass element) {
        return getMethods(element).filter(this::methodIsNotPrivate);
    }

    private Stream<CtMethod<?>> getMethods(final CtClass element) {
        return element.getMethods()
                      .stream();
    }

    private Stream<CtFieldReference<?>> getFields(final CtClass element) {
        return element.getDeclaredFields()
                      .stream();
    }

    private void printMethodNames(final Stream<CtMethod<?>> methods) {
        methods.map(CtNamedElement::getReference)
               .map(m -> String.format("- method: %s", m))
               .forEach(System.out::println);
    }

    private void printFieldNames(final Stream<CtFieldReference<?>> fields) {
        fields.map(CtFieldReference::getSimpleName)
              .map(f -> String.format("- field: %s", f))
              .forEach(System.out::println);
    }

    private void mergeFieldsInInvokedMethods(final String method, final Set<String> invocations) {
        invocations.forEach(i -> fieldsAccessedByMethod.get(method)
                                                       .addAll(fieldsAccessedByMethod.get(i)));
        invocations.forEach(i -> mergeFieldsInInvokedMethods(method, methodsInvokedByMethod.get(i)));
    }

    private void recordFieldsUsedBy(final CtMethod<?> method) {
        final String signature = method.getSignature();
        fieldsAccessedByMethod.put(signature, getFieldsAccessed(method));
        methodsInvokedByMethod.put(signature, getMethodsInvoked(method));
    }

    private Set<String> getMethodsInvoked(final CtMethod<?> method) {
        return method.filterChildren(methodsInvoked)
                     .list(CtInvocation.class)
                     .stream()
                     .map(CtInvocation::getExecutable)
                     .map(CtExecutableReference::getDeclaration)
                     .map(CtExecutable::getSignature)
                     .collect(Collectors.toSet());
    }

    private Set<String> getFieldsAccessed(final CtMethod<?> method) {
        return method.filterChildren(fieldsAccessed)
                     .list(CtFieldAccess.class)
                     .stream()
                     .map(CtFieldAccess::getVariable)
                     .map(CtReference::getSimpleName)
                     .distinct()
                     .peek(f -> System.out.println(" - " + f))
                     .collect(Collectors.toSet());
    }

    private boolean methodIsNotPrivate(final CtMethod<?> ctMethod) {
        return !methodIsPrivate(ctMethod);
    }

    private boolean methodIsPrivate(final CtMethod<?> ctMethod) {
        return ctMethod.hasModifier(ModifierKind.PRIVATE);
    }
}
