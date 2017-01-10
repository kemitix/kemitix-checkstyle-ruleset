package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultRuleReadmeLoader}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultRuleReadmeLoaderTest {

    private RuleReadmeLoader loader;

    private TemplateProperties templateProperties;

    private Rule rule;

    private Path fragment;

    private Path fragments;

    @org.junit.Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        templateProperties = new TemplateProperties();
        fragments = folder.newFolder("fragments")
                          .toPath();
        templateProperties.setReadmeFragments(fragments);
        loader = new DefaultRuleReadmeLoader(templateProperties);
        rule = new Rule();
        rule.setName("name");
        rule.setUri(URI.create("uri"));
    }

    @Test
    public void loadEnabledOkay() throws IOException {
        //given
        rule.setEnabled(true);
        fragment = fragments.resolve("name.md");
        Files.write(fragment, Arrays.asList("", "body"));
        //when
        val fragment = loader.load(rule);
        //then
        assertThat(fragment).containsExactlyElementsOf(Arrays.asList("#### [name](uri)", "", "body"));
    }

    @Test
    public void loadEnabledWithMissingFragment() {
        //given
        rule.setEnabled(true);
        exception.expect(ReadmeFragmentNotFoundException.class);
        exception.expectMessage("name");
        //when
        loader.load(rule);
    }

    @Test
    public void loadDisabled() {
        //given
        rule.setEnabled(false);
        rule.setReason("reason");
        //when
        val fragment = loader.load(rule);
        //then
        assertThat(fragment).containsExactlyElementsOf(Arrays.asList("#### [name](uri)", "", "reason"));
    }
}
