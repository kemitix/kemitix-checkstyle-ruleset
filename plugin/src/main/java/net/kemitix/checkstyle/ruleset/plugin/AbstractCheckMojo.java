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
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Runs the Checkstyle Maven Plugin with the Kemitix Checkstyle Ruleset.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
abstract class AbstractCheckMojo extends AbstractMojo {

    private static final String CHECKSTYLE_GROUPID = "com.puppycrawl.tools";

    private static final String CHECKSTYLE_ARTIFACTID = "checkstyle";

    private static final String SEVNTU_GROUPID = "com.github.sevntu.checkstyle";

    private static final String SEVNTU_ARTIFACTID = "sevntu-checkstyle-maven-plugin";

    private static final String KEMITIX_GROUPID = "net.kemitix";

    private static final String KEMITIX_ARTIFACTID = "kemitix-checkstyle-ruleset";

    private static final String APACHE_PLUGIN_GROUPID = "org.apache.maven.plugins";

    private static final String APACHE_PLUGIN_ARTIFACTID = "maven-checkstyle-plugin";

    private static final String CONFIG_LOCATION = "configLocation";

    @Setter
    @Parameter(defaultValue = "${project.version}")
    private String rulesetVersion;

    @Setter
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Setter
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession mavenSession;

    @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
    private ArtifactRepository artifactRepository = null;

    @Component
    private BuildPluginManager pluginManager = null;

    /**
     * Execute Checkstyle Check.
     *
     * @param level The level of config file to use.
     *
     * @throws MojoExecutionException on execution error
     * @throws MojoFailureException   on execution failure
     */
    final void performCheck(final String level) throws MojoExecutionException, MojoFailureException {
        val properties = getProperties();
        debug("properties: %s", properties);

        // get versions from pom.xml properties
        val pluginVersion = getProperty(properties, "maven-checkstyle-plugin.version");
        val checkstyleVersion = getProperty(properties, "checkstyle.version");
        val sevntuVersion = getProperty(properties, "sevntu.version");

        // configure
        val checkstylePlugin = getPlugin(pluginVersion, checkstyleVersion, sevntuVersion);
        val configuration = MojoExecutor.configuration(
                MojoExecutor.element(CONFIG_LOCATION, String.format("net/kemitix/checkstyle-%s.xml", level)));
        val environment = MojoExecutor.executionEnvironment(mavenProject, mavenSession, pluginManager);

        // run
        info("Running Checkstyle %s (sevntu: %s) with ruleset %s (%s)", checkstyleVersion, sevntuVersion, level,
             rulesetVersion
            );
        MojoExecutor.executeMojo(checkstylePlugin, "check", configuration, environment);
        info("Checkstyle complete");
    }

    private String getProperty(final Properties properties, final String key) {
        val property = properties.getProperty(key);
        debug(key + ": %s", property);
        return property;
    }

    private Plugin getPlugin(final String pluginVersion, final String checkstyleVersion, final String sevntuVersion) {
        // create checkstyle dependencies
        val checkstyle = MojoExecutor.dependency(CHECKSTYLE_GROUPID, CHECKSTYLE_ARTIFACTID, checkstyleVersion);
        val sevntu = MojoExecutor.dependency(SEVNTU_GROUPID, SEVNTU_ARTIFACTID, sevntuVersion);
        val ruleset = MojoExecutor.dependency(KEMITIX_GROUPID, KEMITIX_ARTIFACTID, rulesetVersion);
        val dependencies = MojoExecutor.dependencies(checkstyle, sevntu, ruleset);

        return MojoExecutor.plugin(APACHE_PLUGIN_GROUPID, APACHE_PLUGIN_ARTIFACTID, pluginVersion, dependencies);
    }

    private Properties getProperties() throws MojoFailureException {
        // load properties from the plugin pom.xml
        val pluginArtifactId = KEMITIX_ARTIFACTID + "-maven-plugin";
        val pluginArtifact = new DefaultArtifact(KEMITIX_GROUPID, pluginArtifactId, rulesetVersion, null, "", null,
                                                 new DefaultArtifactHandler("pom")
        );
        try {
            val pomReader = new FileReader(artifactRepository.find(pluginArtifact)
                                                             .getFile());
            return new MavenXpp3Reader().read(pomReader)
                                        .getProperties();
        } catch (XmlPullParserException | IOException e) {
            throw new MojoFailureException("Can't load properties from plugin's pom.xml", e);
        }
    }

    private void info(final String format, final Object... args) {
        getLog().info(String.format(format, args));
    }

    private void debug(final String format, final Object... args) {
        getLog().debug(String.format(format, args));
    }
}
