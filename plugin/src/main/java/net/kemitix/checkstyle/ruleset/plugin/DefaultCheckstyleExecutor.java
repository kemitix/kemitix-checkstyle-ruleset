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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

/**
 * Default implementation of {@link CheckstyleExecutor}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RequiredArgsConstructor
public class DefaultCheckstyleExecutor implements CheckstyleExecutor {

    private static final String CHECKSTYLE_GROUPID = "com.puppycrawl.tools";

    private static final String CHECKSTYLE_ARTIFACTID = "checkstyle";

    private static final String SEVNTU_GROUPID = "com.github.sevntu-checkstyle";

    private static final String SEVNTU_ARTIFACTID = "sevntu-checkstyle-maven-plugin";

    private static final String KEMITIX_GROUPID = "net.kemitix";

    private static final String KEMITIX_ARTIFACTID = "kemitix-checkstyle-ruleset";

    private static final String APACHE_PLUGIN_GROUPID = "org.apache.maven.plugins";

    private static final String APACHE_PLUGIN_ARTIFACTID = "maven-checkstyle-plugin";

    private static final String CONFIG_LOCATION = "configLocation";

    private static final String SOURCE_DIR = "sourceDirectory";

    private static final ArtifactHandler POM_ARTIFACT_HANDLER = new DefaultArtifactHandler("pom");

    private static final String PLUGIN_ARTIFACT_ID = KEMITIX_ARTIFACTID + "-parent";

    private final PluginExecutor pluginExecutor;

    private final MavenXpp3Reader mavenXpp3Reader;

    @Setter
    @Getter
    private Log log;

    private String rulesetVersion;

    @Override
    public final void performCheck(final CheckConfiguration config)
            throws MojoExecutionException, MojoFailureException {
        rulesetVersion = config.getRulesetVersion();
        val properties = getProperties(config.getArtifactRepository());

        // configure
        val checkstylePlugin = getPlugin(properties);
        final String sourceDirectory = config.getSourceDirectory();
        final String level = config.getLevel();
        info("Ruleset: %s", level);
        final String configFile = String.format("net/kemitix/checkstyle-%s.xml", level);
        final Xpp3Dom configuration = pluginExecutor.configuration(pluginExecutor.element(CONFIG_LOCATION, configFile),
                                                                   pluginExecutor.element(SOURCE_DIR, sourceDirectory)
                                                                  );
        final MojoExecutor.ExecutionEnvironment environment = pluginExecutor.executionEnvironment(config);

        // run
        pluginExecutor.executeMojo(checkstylePlugin, "check", configuration, environment);
        info("Checkstyle complete");
    }

    private String getProperty(final Properties properties, final String key) {
        val property = properties.getProperty(key);
        debug("%s: %s", key, property);
        return property;
    }

    private Plugin getPlugin(final Properties properties) {
        // get versions from pom.xml properties
        val pluginVersion = getProperty(properties, "maven-checkstyle-plugin.version");
        val checkstyleVersion = getProperty(properties, "checkstyle.version");
        val sevntuVersion = getProperty(properties, "sevntu.version");
        info("Checkstyle %s (plugin: %s, sevntu: %s, ruleset: %s)", checkstyleVersion,
             pluginVersion, sevntuVersion, rulesetVersion
            );

        // create checkstyle dependencies
        val checkstyle = getCheckstyleDependency(checkstyleVersion);
        val sevntu = getSevntuDependency(sevntuVersion);
        val ruleset = getRulesetDependency();
        final List<Dependency> dependencies = pluginExecutor.dependencies(checkstyle, sevntu, ruleset);

        return pluginExecutor.plugin(APACHE_PLUGIN_GROUPID, APACHE_PLUGIN_ARTIFACTID, pluginVersion, dependencies);
    }

    private Dependency getRulesetDependency() {
        return pluginExecutor.dependency(KEMITIX_GROUPID, KEMITIX_ARTIFACTID, rulesetVersion);
    }

    private Dependency getSevntuDependency(final String sevntuVersion) {
        return pluginExecutor.dependency(SEVNTU_GROUPID, SEVNTU_ARTIFACTID, sevntuVersion);
    }

    private Dependency getCheckstyleDependency(final String checkstyleVersion) {
        return pluginExecutor.dependency(CHECKSTYLE_GROUPID, CHECKSTYLE_ARTIFACTID, checkstyleVersion);
    }

    private Properties getProperties(final ArtifactRepository artifactRepository) throws MojoFailureException {
        // load properties from the plugin pom.xml
        final Artifact pluginArtifact = getPluginArtifact(artifactRepository);
        try {
            final File pomFile = pluginArtifact.getFile();
            return parsePomFile(pomFile).getProperties();
        } catch (XmlPullParserException | IOException e) {
            throw new MojoFailureException("Can't load properties from plugin's pom.xml", e);
        }
    }

    private Model parsePomFile(final File pomFile) throws IOException, XmlPullParserException {
        final Reader pomReader = new FileReader(pomFile);
        return mavenXpp3Reader.read(pomReader);
    }

    private Artifact getPluginArtifact(final ArtifactRepository artifactRepository) {
        final Artifact templateArtifact = getRulesetArtifactTemplate();
        return artifactRepository.find(templateArtifact);
    }

    private DefaultArtifact getRulesetArtifactTemplate() {
        return new DefaultArtifact(KEMITIX_GROUPID, PLUGIN_ARTIFACT_ID, rulesetVersion, null, "", null,
                                   POM_ARTIFACT_HANDLER
        );
    }

    private void info(final String format, final Object... args) {
        getLog().info(String.format(format, args));
    }

    private void debug(final String format, final Object... args) {
        getLog().debug(String.format(format, args));
    }
}
