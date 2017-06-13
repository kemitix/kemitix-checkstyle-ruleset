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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Prepresents a collections of cohesive methods and fields within a class.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
class Component {

    private Set<String> members = new HashSet<>();

    /**
     * Create a new component consisting of the members.
     *
     * @param members the members
     *
     * @return the new component
     */
    public static Component from(final Collection<String> members) {
        final Component component = new Component();
        component.members.addAll(members);
        return component;
    }

    /**
     * Get the existing members of the component.
     *
     * @return a set of members
     */
    Set<String> getMembers() {
        return new HashSet<>(members);
    }

    /**
     * Merge the component into this.
     *
     * @param component the component to merge
     */
    void merge(final Component component) {
        members.addAll(component.getMembers());
    }
}
