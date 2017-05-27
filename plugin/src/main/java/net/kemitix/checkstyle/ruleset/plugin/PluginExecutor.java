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

package net.kemitix.checkstyle.ruleset.plugin;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.util.List;

/**
 * Interface for wrapping calls to static methods on {@link org.twdata.maven.mojoexecutor.MojoExecutor}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public interface PluginExecutor {

    /**
     * Constructs the element with a textual body.
     *
     * @param name  The element name
     * @param value The element text value
     *
     * @return The element object
     */
    MojoExecutor.Element element(String name, String value);

    /**
     * Builds the configuration for the goal using Elements.
     *
     * @param elements A list of elements for the configuration section
     *
     * @return The elements transformed into the Maven-native XML format
     */
    Xpp3Dom configuration(MojoExecutor.Element... elements);

    /**
     * Constructs the {@link MojoExecutor.ExecutionEnvironment} instance fluently.
     *
     * @param configuration The Configuration
     *
     * @return The execution environment
     */
    MojoExecutor.ExecutionEnvironment executionEnvironment(CheckConfiguration configuration);

    /**
     * Entry point for executing a mojo.
     *
     * @param plugin        The plugin to execute
     * @param goal          The goal to execute
     * @param configuration The execution configuration
     * @param env           The execution environment
     *
     * @throws MojoExecutionException If there are any exceptions locating or executing the mojo
     */
    void executeMojo(
            Plugin plugin, String goal, Xpp3Dom configuration, MojoExecutor.ExecutionEnvironment env
                    ) throws MojoExecutionException;

    /**
     * Defines a dependency.
     *
     * @param groupId    The group id
     * @param artifactId The artifact id
     * @param version    The plugin version
     *
     * @return the dependency instance
     */
    Dependency dependency(String groupId, String artifactId, String version);

    /**
     * Creates a list of dependencies.
     *
     * @param dependencies the dependencies
     *
     * @return A list of dependencies
     */
    List<Dependency> dependencies(Dependency... dependencies);

    /**
     * Defines a plugin.
     *
     * @param groupId      The group id
     * @param artifactId   The artifact id
     * @param version      The plugin version
     * @param dependencies The plugin dependencies
     *
     * @return The plugin instance
     */
    Plugin plugin(
            String groupId, String artifactId, String version, List<Dependency> dependencies
                 );
}
