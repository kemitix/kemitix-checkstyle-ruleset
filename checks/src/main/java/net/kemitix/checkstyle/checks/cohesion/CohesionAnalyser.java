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

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Analyses the method invocations of a class to determine the cohesiveness of a class.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
interface CohesionAnalyser {

    /**
     * Analyse the cohesion of a class from the items used by each method.
     *
     * @param usedByMethod      a map of fields and methods used grouped by each method
     * @param nonPrivateMethods a list of methods
     * @param resultConsumer    the consumer to give results to
     */
    void analyse(
            Map<String, Set<String>> usedByMethod, Set<String> nonPrivateMethods,
            Consumer<CohesionAnalysisResult> resultConsumer
                );
}
