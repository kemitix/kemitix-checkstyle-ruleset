package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Tests for {@link DefaultRuleReadmeLoader}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultRuleReadmeLoaderTest
        implements WithAssertions {

    private RuleReadmeLoader loader;

    private Rule rule;

    private Path fragments;

    @TempDir
    public File folder;

    @BeforeEach
    public void setUp() throws Exception {
        final TemplateProperties templateProperties = new TemplateProperties();
        fragments = folder.toPath().resolve("fragments");
        Files.createDirectories(fragments);
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
        final Path fragment1 = fragments.resolve("name.md");
        Files.write(fragment1, Arrays.asList("", "body"));
        //when
        val fragment = loader.load(rule);
        //then
        assertThat(fragment).containsExactlyElementsOf(Arrays.asList("#### [name](uri)", "", "body"));
    }

    @Test
    public void loadEnabledWithMissingFragment() {
        //given
        rule.setEnabled(true);
        //when
        assertThatExceptionOfType(ReadmeFragmentNotFoundException.class)
                .isThrownBy(() -> loader.load(rule))
                .withMessage("name");
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
