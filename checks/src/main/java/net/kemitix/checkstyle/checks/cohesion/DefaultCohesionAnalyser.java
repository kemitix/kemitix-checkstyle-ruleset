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

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link CohesionAnalyser}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RequiredArgsConstructor
class DefaultCohesionAnalyser implements CohesionAnalyser {


    @Override
    public void analyse(
            final Map<String, Set<String>> fieldsAccessed, final Map<String, Set<String>> methodsInvoked,
            final Set<String> nonPrivateMethods, final Consumer<CohesionAnalysisResult> resultConsumer
                       ) {
        methodsInvoked.entrySet()
                      .stream()
                      .filter(ignoreBeanMethods(fieldsAccessed))
                      .forEach(e -> mergeFieldsInInvokedMethods(e.getKey(), e.getValue(), fieldsAccessed,
                                                                methodsInvoked
                                                               ));
        final CohesionAnalysisResult result = new CohesionAnalysisResult();
        result.addNonBeanMethods(nonPrivateMethods.stream()
                                       .filter(m -> isNotBeanMethod(m, fieldsAccessed.get(m)))
                                       .collect(Collectors.toSet()));
        resultConsumer.accept(result);
    }

    private void mergeFieldsInInvokedMethods(
            final String method, final Set<String> invocations, final Map<String, Set<String>> fieldsAccessed,
            final Map<String, Set<String>> methodsInvoked
                                            ) {
        invocations.forEach(i -> fieldsAccessed.get(method)
                                               .addAll(fieldsAccessed.get(i)));
        invocations.forEach(
                i -> mergeFieldsInInvokedMethods(method, methodsInvoked.get(i), fieldsAccessed, methodsInvoked));
    }

    private Predicate<? super Map.Entry<String, Set<String>>> ignoreBeanMethods(
            final Map<String, Set<String>> fieldsAccessed
                                                                               ) {
        return f -> {
            final String method = f.getKey();
            final Set<String> fields = fieldsAccessed.get(method);
            return isNotBeanMethod(method, fields);
        };
    }

    private boolean isNotBeanMethod(final String method, final Set<String> fields) {
        return !isBeanMethod(method, fields);
    }

    private boolean isBeanMethod(final String method, final Set<String> fields) {
        final String methodName = method.toLowerCase();
        if (fields.size() == 1) {
            final String fieldAccessed = fields.toArray(new String[1])[0];
            if (isBeanMethod(methodName, fieldAccessed)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isBeanMethod(final String method, final String field) {
        return isSetter(method, field) || isGetter(method, field);
    }

    private boolean isGetter(final String method, final String field) {
        final boolean isPlainGetter = method.endsWith(" get" + field + "()");
        final boolean isBooleanGetter = method.equals("java.lang.boolean is" + field + "()");
        final boolean isPrimitiveBooleanGetter = method.equals("boolean is" + field + "()");
        return isPlainGetter || isBooleanGetter || isPrimitiveBooleanGetter;
    }

    private boolean isSetter(final String method, final String field) {
        return method.contains(" set" + field + "(");
    }

}
