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

import java.io.File;
import java.util.function.Consumer;

/**
 * Service for performing the {@link net.kemitix.checkstyle.checks.CohesionCheck}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public interface CohesionCheckService {

    /**
     * Creates a new instance of the {@code CohesionCheckService}.
     *
     * @return a new CohesionCheckService instance
     */
    static CohesionCheckService create() {
        return new SpoonCohesionCheckService();
    }

    /**
     * Performs the cohesion check on the given file, passing results to the consumer.
     *
     * @param file           the file to check
     * @param resultConsumer the consumer of the results
     */
    void check(File file, Consumer<CohesionAnalysisResult> resultConsumer);
}
