/*
The MIT License (MIT)

Copyright (c) 2016 Paul Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.kemitix.checkstyle.ruleset.plugin;

import lombok.Setter;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Runs the Checkstyle Maven Plugin with the Kemitix Checkstyle Ruleset.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Mojo(name = "check", defaultPhase = LifecyclePhase.VALIDATE)
public class CheckMojo extends AbstractMojo {

    private static final String KEMITIX_GROUPID = "net.kemitix";

    private static final String KEMITIX_ARTIFACTID = "kemitix-checkstyle-ruleset";

    @Setter
    private CheckstyleExecutor checkstyleExecutor =
            new DefaultCheckstyleExecutor(new DefaultPluginExecutor(), new MavenXpp3Reader());

    @Setter
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Setter
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession mavenSession;

    @Setter
    @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
    private ArtifactRepository artifactRepository;

    @Parameter(defaultValue = "5-complexity", readonly = true)
    private String level = "5-complexity";

    @Setter
    @Component
    private BuildPluginManager pluginManager;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        checkstyleExecutor.setLog(getLog());
        CheckConfiguration config = getCheckConfiguration();
        checkstyleExecutor.performCheck(config);
    }

    private CheckConfiguration getCheckConfiguration() {
        final Plugin rulesetPlugin = getRulesetPlugin();
        final Build build = mavenProject.getBuild();
        return CheckConfiguration.builder()
                                 .mavenProject(mavenProject)
                                 .mavenSession(mavenSession)
                                 .artifactRepository(artifactRepository)
                                 .pluginManager(pluginManager)
                                 .rulesetVersion(rulesetPlugin.getVersion())
                                 .sourceDirectory(build.getSourceDirectory())
                                 .level(level)
                                 .build();
    }

    private Plugin getRulesetPlugin() {
        return mavenProject.getPlugin(KEMITIX_GROUPID + ":" + KEMITIX_ARTIFACTID + "-maven-plugin");
    }
}
