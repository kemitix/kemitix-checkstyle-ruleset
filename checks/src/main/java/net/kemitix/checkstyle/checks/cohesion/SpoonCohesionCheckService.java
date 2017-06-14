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

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.processing.Processor;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Filter;

import java.io.File;
import java.util.function.Consumer;

/**
 * Spoon implementation of the {@link CohesionCheckService}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class SpoonCohesionCheckService implements CohesionCheckService {

    private final Filter<CtInvocation> invocationFilter = element -> element.getParent(CtClass.class)
                                                                            .getQualifiedName()
                                                                            .equals(element.getExecutable()
                                                                                           .getDeclaringType()
                                                                                           .toString());

    private final CohesionAnalyser cohesionAnalyser = new DefaultCohesionAnalyser();

    @Override
    public final void check(final File file, final Consumer<CohesionAnalysisResult> resultConsumer) {
        final CtModel model = modelForFile(file);
        final Processor<CtClass> cohesionProcessor = cohesionProcessor(resultConsumer);
        model.processWith(cohesionProcessor);
    }

    private CtModel modelForFile(final File file) {
        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource(file.getAbsolutePath());
        spoon.buildModel();
        return spoon.getFactory()
                    .getModel();
    }

    private Processor<CtClass> cohesionProcessor(final Consumer<CohesionAnalysisResult> resultConsumer) {
        return SpoonCohesionProcessor.create(invocationFilter, cohesionAnalyser, resultConsumer);
    }
}
