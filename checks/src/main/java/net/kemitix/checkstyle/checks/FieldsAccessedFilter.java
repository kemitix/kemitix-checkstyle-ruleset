package net.kemitix.checkstyle.checks;

import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.visitor.Filter;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class FieldsAccessedFilter implements Filter<CtFieldAccess> {

    @Override
    public boolean matches(final CtFieldAccess access) {
        return true;
    }
}
