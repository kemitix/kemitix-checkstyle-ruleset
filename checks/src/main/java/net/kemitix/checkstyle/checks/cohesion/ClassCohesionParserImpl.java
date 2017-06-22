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
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of Class Cohesion Parser.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ClassCohesionParserImpl implements ClassCohesionParser {

    @Override
    public final Results parse(final String fileName, final String className) {
        final CtModel model = modelForFile(fileName);
        final Set<String> nonPrivateMethods = getNonPrivateMethods(model, className);
        final Map<String, Set<String>> usedByMethod = getUsedByMethod(model, className);
        return new Results(usedByMethod, nonPrivateMethods);
    }

    private Map<String, Set<String>> getUsedByMethod(final CtModel model, final String className) {
        final Map<String, Set<String>> usedByMethod = new HashMap<>();
        getClassFromModel(model, className).forEach(ctClass -> {
            ctClass.filterChildren((Filter<CtMethod>) method -> true)
                   .list(CtMethod.class)
                   .forEach(method -> {
                       final String signature = method.getSignature();
                       final Set<String> methodsCalled = method.filterChildren(
                               (Filter<CtInvocation>) element -> element.getParent(CtClass.class)
                                                                        .getQualifiedName()
                                                                        .equals(element.getExecutable()
                                                                                       .getDeclaringType()
                                                                                       .toString()))
                                                               .list(CtInvocation.class)
                                                               .stream()
                                                               .map(CtInvocation::getExecutable)
                                                               .map(CtExecutableReference::getDeclaration)
                                                               .map(CtExecutable::getSignature)
                                                               .collect(Collectors.toSet());
                       final Set<String> fieldsUsed = method.filterChildren(e -> true)
                                                            .list(CtFieldAccess.class)
                                                            .stream()
                                                            .map(CtFieldAccess::getVariable)
                                                            .map(CtReference::getSimpleName)
                                                            .distinct()
                                                            .collect(Collectors.toSet());
                       usedByMethod.put(signature, Sets.union(methodsCalled, fieldsUsed));
                   });
        });
        return usedByMethod;
    }

    private Set<String> getNonPrivateMethods(final CtModel model, final String className) {
        final Set<String> methods = new HashSet<>();
        getClassFromModel(model, className).forEach(ctClass -> {
            ctClass.filterChildren((Filter<CtMethod>) method -> true)
                   .list(CtMethod.class)
                   .forEach(method -> {
                       if (!method.hasModifier(ModifierKind.PRIVATE)) {
                           methods.add(method.getSignature());
                       }
                   });
        });
        return methods;
    }

    private List<CtClass> getClassFromModel(final CtModel model, final String className) {
        return model.getElements(new Filter<CtClass>() {
            @Override
            public boolean matches(final CtClass element) {
                final String name = element.getQualifiedName();
                final boolean passes = name.equals(className);
                return passes;
            }
        });
    }

    private CtModel modelForFile(final String file) {
        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource(file);
        spoon.buildModel();
        return spoon.getModel();
    }
}
