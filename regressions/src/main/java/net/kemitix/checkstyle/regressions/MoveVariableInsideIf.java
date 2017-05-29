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

package net.kemitix.checkstyle.regressions;

/**
 * Regression demo for {@code MoveVariableInsideIfCheck}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
class MoveVariableInsideIf {

    private String input = "1";

    private boolean condition;

    private String method(final String variable) {
        return "value";
    }

    /**
     * Fails if not suppressed.
     *
     * @return value
     */
    @SuppressWarnings("movevariableinsideif")
    String invalid() {
        String variable = input.substring(1);
        if (condition) {
            return method(variable);
        }
        return "";
    }

    /**
     * Rewrite {@link #invalid()} as this to pass.
     *
     * @return value
     */
    String valid() {
        if (condition) {
            String variable = input.substring(1);
            return method(variable);
        }
        return "";
    }
}