package net.kemitix.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.visitor.Filter;

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

    private final Filter<CtFieldAccess> fieldsAccessedFilter = new FieldsAccessedFilter();

    private final Filter<CtInvocation> methodsInvokedFilter = new MethodsInvokedFilter();

    private final CohesionAnalyser cohesionAnalyser = new DefaultCohesionAnalyser();

    private final CohesionProcessor cohesionProcessor =
            new CohesionProcessor(this::logger, fieldsAccessedFilter, methodsInvokedFilter, cohesionAnalyser);

    @Override
    protected void processFiltered(final File file, final List<String> lines) throws CheckstyleException {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource(file.getAbsolutePath());
        spoon.buildModel();
        spoon.getFactory()
             .getModel()
             .processWith(cohesionProcessor);
    }

    private void logger(final String fields) {
        log(1, "cohesion.partitioned", fields);
    }
}
