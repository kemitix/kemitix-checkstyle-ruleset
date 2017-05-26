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
 * Wrapper for {@link org.twdata.maven.mojoexecutor.MojoExecutor}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public final class DefaultPluginExecutor implements PluginExecutor {

    @Override
    public MojoExecutor.Element element(final String key, final String value) {
        return MojoExecutor.element(key, value);
    }

    @Override
    public Xpp3Dom configuration(final MojoExecutor.Element... elements) {
        return MojoExecutor.configuration(elements);
    }

    @Override
    public MojoExecutor.ExecutionEnvironment executionEnvironment(final CheckConfiguration config) {
        return MojoExecutor.executionEnvironment(
                config.getMavenProject(), config.getMavenSession(), config.getPluginManager());
    }

    @Override
    public void executeMojo(
            final Plugin plugin, final String goal, final Xpp3Dom configuration,
            final MojoExecutor.ExecutionEnvironment env
                           ) throws MojoExecutionException {
        MojoExecutor.executeMojo(plugin, goal, configuration, env);
    }

    @Override
    public Dependency dependency(final String groupid, final String artifactid, final String version) {
        return MojoExecutor.dependency(groupid, artifactid, version);
    }

    @Override
    public List<Dependency> dependencies(final Dependency... dependencies) {
        return MojoExecutor.dependencies(dependencies);
    }

    @Override
    public Plugin plugin(
            final String groupid, final String artifactid, final String version, final List<Dependency> dependencies
                        ) {
        return MojoExecutor.plugin(groupid, artifactid, version, dependencies);
    }
}
