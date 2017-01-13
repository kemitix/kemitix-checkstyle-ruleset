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
import lombok.val;
import me.andrz.builder.map.MapBuilder;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.util.Map;

/**
 * Runs the Checkstyle Maven Plugin with the Kemitix Checkstyle Ruleset.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Mojo(name = "check", defaultPhase = LifecyclePhase.VALIDATE)
public class CheckMojo extends AbstractMojo {

    private static final String CHECKSTYLE_GROUPID = "com.puppycrawl.tools";

    private static final String CHECKSTYLE_ARTIFACTID = "checkstyle";

    private static final String SEVNTU_GROUPID = "com.github.sevntu.checkstyle";

    private static final String SEVNTU_ARTIFACTID = "sevntu-checkstyle-maven-plugin";

    private static final String KEMITIX_GROUPID = "net.kemitix";

    private static final String KEMITIX_ARTIFACTID = "kemitix-checkstyle-ruleset";

    private static final String APACHE_PLUGIN_GROUPID = "org.apache.maven.plugins";

    private static final String APACHE_PLUGIN_ARTIFACTID = "maven-checkstyle-plugin";

    private static final String CONFIG_LOCATION = "configLocation";


    private static final String LAYOUT = "1-layout";

    private static final String NAMING = "2-naming";

    private static final String JAVADOC = "3-javadoc";

    private static final String TWEAKS = "4-tweaks";

    private static final String COMPLEXITY = "5-complexity";

    private static Map<String, String> levelConfig = new MapBuilder<String, String>().put("1", LAYOUT)
                                                                                     .put("2", NAMING)
                                                                                     .put("3", JAVADOC)
                                                                                     .put("4", TWEAKS)
                                                                                     .put("5", COMPLEXITY)
                                                                                     .build();

    //@Setter
    //@Parameter(defaultValue = "check")
    //private String goal;

    @Setter
    @Parameter(defaultValue = "2.17")
    private String mavenCheckstylePluginVersion;

    @Setter
    @Parameter(defaultValue = "7.3")
    private String checkstyleVersion;

    @Setter
    @Parameter(defaultValue = "1.23.0")
    private String sevntuVersion;

    @Setter
    @Parameter(defaultValue = "2.0.0-SNAPSHOT")
    private String rulesetVersion;

    @Setter
    @Parameter(defaultValue = "5")
    private String level;

    @Setter
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Setter
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession mavenSession;

    @Component
    private BuildPluginManager pluginManager;

    private static String getConfigFile(final String level) {
        return String.format("checkstyle-%s.xml", levelConfig.getOrDefault(level, COMPLEXITY));
    }

    /**
     * Execute Checkstyle Check.
     *
     * @throws MojoExecutionException on execution error
     * @throws MojoFailureException   on execution failure
     */
    public final void execute() throws MojoExecutionException, MojoFailureException {
        val checkstyle = MojoExecutor.dependency(CHECKSTYLE_GROUPID, CHECKSTYLE_ARTIFACTID, checkstyleVersion);
        val sevntu = MojoExecutor.dependency(SEVNTU_GROUPID, SEVNTU_ARTIFACTID, sevntuVersion);
        val ruleset = MojoExecutor.dependency(KEMITIX_GROUPID, KEMITIX_ARTIFACTID, rulesetVersion);
        val checkstylePlugin =
                MojoExecutor.plugin(APACHE_PLUGIN_GROUPID, APACHE_PLUGIN_ARTIFACTID, mavenCheckstylePluginVersion,
                                    MojoExecutor.dependencies(checkstyle, sevntu, ruleset)
                                   );
        val configLocation = MojoExecutor.element(CONFIG_LOCATION, "net/kemitix/" + getConfigFile(level));

        getLog().info(
                "Running Checkstyle " + checkstyleVersion + " (sevntu: " + sevntuVersion + ") with " + getConfigFile(
                        level));
        MojoExecutor.executeMojo(checkstylePlugin, "check", MojoExecutor.configuration(configLocation),
                                 MojoExecutor.executionEnvironment(mavenProject, mavenSession, pluginManager)
                                );
    }
}
