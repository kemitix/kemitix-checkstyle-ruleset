package net.kemitix.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

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
public class CohesionCheck extends AbstractCheck {

    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }
}
