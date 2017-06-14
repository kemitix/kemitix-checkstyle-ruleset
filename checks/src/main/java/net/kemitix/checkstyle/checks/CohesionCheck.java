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

package net.kemitix.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import lombok.SneakyThrows;
import net.kemitix.checkstyle.checks.cohesion.CohesionAnalysisResult;
import net.kemitix.checkstyle.checks.cohesion.CohesionCheckService;

import java.io.File;
import java.util.List;

/**
 * Checkstyle Check to ensure appropriate cohesion within a class of its methods and fields.
 *
 * <p>Specifically it measures the ratio of fields used by each public method.</p>
 *
 * <p>If this ratio falls below a threshold, then a violation will be logged.</p>
 *
 * <p>It also tried to detect when there is a partitioning of methods and fields indicating that they should probably be
 * split into a separate class.</p>
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class CohesionCheck extends AbstractFileSetCheck {

    private CohesionCheckService cohesionCheckService;

    @Override
    @SneakyThrows
    protected final void setupChild(final Configuration childConf) throws CheckstyleException {
        final String cohesionCheckServiceClass = childConf.getAttribute("cohesionCheckServiceClass");
            cohesionCheckService = (CohesionCheckService) this.getClass()
                                                              .getClassLoader()
                                                              .loadClass(cohesionCheckServiceClass)
                                                              .newInstance();
    }

    @Override
    protected final void processFiltered(final File file, final List<String> lines) throws CheckstyleException {
        cohesionCheckService.check(file, this::resultConsumer);
    }

    private void resultConsumer(final CohesionAnalysisResult result) {
        final int size = result.getComponents()
                               .size();
        if (size > 1) {
            log(1, "cohesion.partitioned", size, result.getPartitionedNonPrivateMethods());
        }
    }
}
