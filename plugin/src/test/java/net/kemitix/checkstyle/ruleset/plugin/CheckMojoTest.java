package net.kemitix.checkstyle.ruleset.plugin;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link CheckMojo}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CheckMojoTest {

    @Mock
    private CheckstyleExecutor checkstyleExecutor;

    @Mock
    private MavenProject mavenProject;

    @Mock
    private MavenSession mavenSession;

    @Mock
    private ArtifactRepository artifactRepository;

    @Mock
    private BuildPluginManager pluginManager;

    @Captor
    private ArgumentCaptor<CheckConfiguration> configuration;

    @Mock
    private Plugin rulesetPlugin;

    @Mock
    private Build build;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canExecute() throws MojoFailureException, MojoExecutionException {
        //given
        final CheckMojo mojo = new CheckMojo();
        mojo.setCheckstyleExecutor(checkstyleExecutor);
        mojo.setMavenProject(mavenProject);
        mojo.setMavenSession(mavenSession);
        mojo.setArtifactRepository(artifactRepository);
        mojo.setPluginManager(pluginManager);
        given(mavenProject.getPlugin(any())).willReturn(rulesetPlugin);
        given(mavenProject.getBuild()).willReturn(build);
        //when
        mojo.execute();
        //then
        then(checkstyleExecutor).should()
                                .performCheck(configuration.capture());
        assertThat(configuration.getValue()).returns(mavenProject, CheckConfiguration::getMavenProject)
                                            .returns(mavenSession, CheckConfiguration::getMavenSession)
                                            .returns(artifactRepository, CheckConfiguration::getArtifactRepository)
                                            .returns(pluginManager, CheckConfiguration::getPluginManager);
    }
}
