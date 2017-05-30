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

package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A single Checkstyle Check.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Setter
@Getter
public class Rule {

    private static final Locale LOCALE = Locale.ENGLISH;

    /**
     * Configuration properties.
     */
    private final Map<String, String> properties = new HashMap<>();

    /**
     * The name of the rule's Check class.
     */
    private String name;

    /**
     * The parent module.
     */
    private RuleParent parent;

    /**
     * The first level the rule is enabled on.
     */
    private RuleLevel level;

    /**
     * Whether the rule is enabled.
     */
    private boolean enabled;

    /**
     * The source of the rule.
     */
    private RuleSource source;

    /**
     * URI for full official documentation.
     */
    private URI uri;

    /**
     * Flag to indicate rules that can not be suppressed (via {@code @SuppressWarnings}.
     */
    private boolean insuppressible;

    /**
     * The reason a rule has been disabled.
     */
    private String reason;

    /**
     * Compare two Rules lexicographically for sorting by rule name, ignoring case.
     *
     * @param left  the first rule
     * @param right the second rule
     *
     * @return the value 0 if the rules are equal; a value less than 0 if the left string is lexicographically less than
     * the right string; and a value greater than 0 if the left string is lexicographically greater than the right
     * string.
     */
    protected static int sortByName(final Rule left, final Rule right) {
        return left.getLowerCaseRuleName()
                   .compareTo(right.getLowerCaseRuleName());
    }

    private String getLowerCaseRuleName() {
        return getName().toLowerCase(LOCALE);
    }
}
