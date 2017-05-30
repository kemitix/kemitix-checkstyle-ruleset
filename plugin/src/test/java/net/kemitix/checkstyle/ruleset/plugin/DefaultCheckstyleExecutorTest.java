package net.kemitix.checkstyle.ruleset.plugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Tests for {@link DefaultCheckstyleExecutor}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultCheckstyleExecutorTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Mock
    private PluginExecutor pluginExecutor;

    @Mock
    private MavenProject mavenProject;

    @Mock
    private MavenSession mavenSession;

    @Mock
    private ArtifactRepository artifactRepository;

    @Mock
    private BuildPluginManager pluginManager;

    @Mock
    private Plugin rulesetPlugin;

    @Mock
    private Log log;

    private DefaultCheckstyleExecutor checkstyleExecutor;

    private String level;

    @Mock
    private Artifact artifact;

    @Mock
    private MavenXpp3Reader mavenXpp3Reader;

    @Mock
    private Model pomModel;

    @Mock
    private MojoExecutor.Element configElement;

    @Mock
    private MojoExecutor.Element sourceElement;

    @Mock
    private Xpp3Dom pluginConfig;

    @Mock
    private MojoExecutor.ExecutionEnvironment env;

    @Mock
    private Dependency checkstyle;

    @Mock
    private Dependency sevntu;

    @Mock
    private Dependency ruleset;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        level = "test level";
        checkstyleExecutor = new DefaultCheckstyleExecutor(pluginExecutor, mavenXpp3Reader);
        checkstyleExecutor.setLog(log);
    }

    @Test
    public void canPerformCheck()
            throws MojoFailureException, MojoExecutionException, IOException, XmlPullParserException {
        //given
        final CheckConfiguration configuration = CheckConfiguration.builder()
                                                                   .mavenProject(mavenProject)
                                                                   .mavenSession(mavenSession)
                                                                   .artifactRepository(artifactRepository)
                                                                   .pluginManager(pluginManager)
                                                                   .rulesetVersion("ruleset version")
                                                                   .level(level)
                                                                   .build();

        given(artifactRepository.find(any())).willReturn(artifact);
        final File artifactFile = folder.newFile("pom.xml");
        given(artifact.getFile()).willReturn(artifactFile);
        given(mavenXpp3Reader.read(any(FileReader.class))).willReturn(pomModel);
        final Properties properties = new Properties();
        given(pomModel.getProperties()).willReturn(properties);
        given(pluginExecutor.element(eq("configLocation"), any())).willReturn(configElement);
        given(pluginExecutor.element(eq("sourceDirectory"), any())).willReturn(sourceElement);
        given(pluginExecutor.configuration(configElement, sourceElement)).willReturn(pluginConfig);
        given(pluginExecutor.executionEnvironment(configuration)).willReturn(env);
        final List<Dependency> dependencies = new ArrayList<>();
        dependencies.add(checkstyle);
        dependencies.add(sevntu);
        dependencies.add(ruleset);
        final String pluginVersion = "plugin version";
        final String checkstyleVersion = "checkstyle version";
        final String sevntuVersion = "sevntu version";
        final String rulesetVersion = "ruleset version";
        given(pluginExecutor.dependencies(checkstyle, sevntu, ruleset)).willReturn(dependencies);
        given(pluginExecutor.dependency("com.puppycrawl.tools", "checkstyle", checkstyleVersion)).willReturn(
                checkstyle);
        given(pluginExecutor.dependency(
                "com.github.sevntu.checkstyle", "sevntu-checkstyle-maven-plugin", sevntuVersion)).willReturn(sevntu);
        given(pluginExecutor.dependency("net.kemitix", "kemtiix-checkstyle-ruleset", rulesetVersion)).willReturn(
                ruleset);
        properties.setProperty("maven-checkstyle-plugin.version", pluginVersion);
        properties.setProperty("checkstyle.version", checkstyleVersion);
        properties.setProperty("sevntu.version", sevntuVersion);
        given(pluginExecutor.plugin(
                eq("org.apache.maven.plugins"), eq("maven-checkstyle-plugin"), eq(pluginVersion), any())).willReturn(
                rulesetPlugin);
        //when
        checkstyleExecutor.performCheck(configuration);
        //then

        then(pluginExecutor).should()
                            .executeMojo(rulesetPlugin, "check", pluginConfig, env);
    }
}
