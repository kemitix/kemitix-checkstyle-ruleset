package net.kemitix.checkstyle.ruleset.plugin;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.InvalidPluginDescriptorException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.PluginConfigurationException;
import org.apache.maven.plugin.PluginDescriptorParsingException;
import org.apache.maven.plugin.PluginManagerException;
import org.apache.maven.plugin.PluginNotFoundException;
import org.apache.maven.plugin.PluginResolutionException;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Tests for {@link DefaultPluginExecutor}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultPluginExecutorTest {

    private PluginExecutor pluginExecutor;

    @Mock
    private MavenProject mavenProject;

    @Mock
    private MavenSession mavenSession;

    @Mock
    private BuildPluginManager pluginManager;

    @Mock
    private Plugin plugin;

    @Mock
    private Xpp3Dom configuration;

    @Mock
    private PluginDescriptor pluginDescriptor;

    @Mock
    private MojoDescriptor mojoDescriptor;

    @Mock
    private PlexusConfiguration mojoConfiguration;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pluginExecutor = new DefaultPluginExecutor();
    }

    @Test
    public void canElement() {
        //when
        final MojoExecutor.Element element = pluginExecutor.element("name", "value");
        //then
        assertThat(element.toDom()).returns("name", Xpp3Dom::getName)
                                   .returns("value", Xpp3Dom::getValue);
    }

    @Test
    public void canConfiguration() {
        //given
        final MojoExecutor.Element alpha = pluginExecutor.element("alpha", "first");
        final MojoExecutor.Element beta = pluginExecutor.element("beta", "second");
        //when
        final Xpp3Dom configuration = pluginExecutor.configuration(alpha, beta);
        //then
        assertThat(configuration.getChild("alpha")).returns("alpha", Xpp3Dom::getName)
                                                   .returns("first", Xpp3Dom::getValue);
        assertThat(configuration.getChild("beta")).returns("beta", Xpp3Dom::getName)
                                                  .returns("second", Xpp3Dom::getValue);
    }

    @Test
    public void canExecutionEnvironment() {
        //given
        final CheckConfiguration configuration = createCheckConfiguration();
        //when
        final MojoExecutor.ExecutionEnvironment environment = pluginExecutor.executionEnvironment(configuration);
        //then
        assertThat(environment).returns(mavenProject, MojoExecutor.ExecutionEnvironment::getMavenProject)
                               .returns(mavenSession, MojoExecutor.ExecutionEnvironment::getMavenSession)
                               .returns(pluginManager, MojoExecutor.ExecutionEnvironment::getPluginManager);
    }

    private CheckConfiguration createCheckConfiguration() {
        return CheckConfiguration.builder()
                                 .mavenProject(mavenProject)
                                 .mavenSession(mavenSession)
                                 .pluginManager(pluginManager)
                                 .build();
    }

    @Test
    public void canExecuteMojo()
            throws MojoExecutionException, InvalidPluginDescriptorException, PluginResolutionException,
                   PluginDescriptorParsingException, PluginNotFoundException, PluginManagerException,
                   PluginConfigurationException, MojoFailureException {
        //given
        final String goal = "check";
        final MojoExecutor.ExecutionEnvironment environment =
                pluginExecutor.executionEnvironment(createCheckConfiguration());
        given(mavenSession.getCurrentProject()).willReturn(mavenProject);
        given(pluginManager.loadPlugin(eq(plugin), any(), any())).willReturn(pluginDescriptor);
        given(pluginDescriptor.getMojo(goal)).willReturn(mojoDescriptor);
        given(mojoDescriptor.getMojoConfiguration()).willReturn(mojoConfiguration);
        given(mojoConfiguration.getAttributeNames()).willReturn(new String[]{});
        given(mojoConfiguration.getChildren()).willReturn(new PlexusConfiguration[]{});
        //when
        pluginExecutor.executeMojo(plugin, goal, configuration, environment);
        //then
        then(pluginManager).should()
                           .executeMojo(eq(mavenSession), any());
    }

    @Test
    public void canDependency() {
        //given
        final String groupId = "groupId";
        final String artifactId = "artifactId";
        final String version = "version";
        //when
        final Dependency dependency = pluginExecutor.dependency(groupId, artifactId, version);
        //then
        assertThat(dependency).returns(groupId, Dependency::getGroupId)
                              .returns(artifactId, Dependency::getArtifactId)
                              .returns(version, Dependency::getVersion);
    }

    @Test
    public void canDependencies() {
        //given
        final Dependency dependency = pluginExecutor.dependency("groupId", "artifactId", "version");
        //when
        final List<Dependency> dependencies = pluginExecutor.dependencies(dependency);
        //then
        assertThat(dependencies).containsExactly(dependency);
    }

    @Test
    public void canPlugin() {
        //given
        final String groupId = "groupId";
        final String artifactId = "artifactId";
        final String version = "version";
        final List<Dependency> dependencies = pluginExecutor.dependencies(pluginExecutor.dependency("pg", "pa", "pv"));
        //when
        final Plugin plugin = pluginExecutor.plugin(groupId, artifactId, version, dependencies);
        //then
        assertThat(plugin).returns(groupId, Plugin::getGroupId)
                          .returns(artifactId, Plugin::getArtifactId)
                          .returns(version, Plugin::getVersion)
                          .returns(dependencies, Plugin::getDependencies);
    }
}
