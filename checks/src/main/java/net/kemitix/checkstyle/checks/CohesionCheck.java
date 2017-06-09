package net.kemitix.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
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

    private final CohesionCheckService cohesionCheckService = CohesionCheckService.create();

    @Override
    protected void processFiltered(final File file, final List<String> lines) throws CheckstyleException {
        cohesionCheckService.check(file, this::resultConsumer);
    }

    private void resultConsumer(final CohesionAnalysisResult result) {
        //log any errors:
        //log(1, "cohesion.partitioned", ...);
    }
}
