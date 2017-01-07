package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link ReadmeWriter}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ReadmeWriterTest {

    private ReadmeWriter readmeWriter;

    private TemplateProperties templateProperties;

    private OutputProperties outputProperties;

    private RulesProperties rulesProperties;

    @Mock
    private RuleReadmeLoader ruleReadmeLoader;

    @Mock
    private ReadmeIndexBuilder indexBuilder;

    private Path template;

    private Path fragments;

    private Path readme;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        template = Files.createTempFile("README", ".md");
        Files.write(template, Arrays.asList("i:%s", "ce:%s", "se:%s", "cd:%s", "sd:%s"));
        fragments = Files.createTempDirectory("fragments");
        readme = Files.createTempFile("README", ".md");
        templateProperties = new TemplateProperties();
        templateProperties.setReadmeTemplate(template);
        templateProperties.setReadmeFragments(fragments);
        outputProperties = new OutputProperties();
        outputProperties.setReadme(readme);
        rulesProperties = new RulesProperties();
        readmeWriter =
                new ReadmeWriter(templateProperties, outputProperties, rulesProperties, ruleReadmeLoader, indexBuilder);
    }

    @Test
    public void createReadme() throws Exception {
        //given
        val expected = Arrays.asList("i:index", "ce:checkstyle-enabled", "se:sevntu-enabled", "cd:checkstyle-disabled",
                                     "sd:sevntu-disabled"
                                    );
        val rules = rulesProperties.getRules();
        final Rule checkstyleEnabled = rule(RuleSource.CHECKSTYLE, true);
        final Rule checkstyleDisabled = rule(RuleSource.CHECKSTYLE, false);
        final Rule sevntuEnabled = rule(RuleSource.SEVNTU, true);
        final Rule sevntuDisabled = rule(RuleSource.SEVNTU, false);
        rules.add(checkstyleEnabled);
        rules.add(checkstyleDisabled);
        rules.add(sevntuEnabled);
        rules.add(sevntuDisabled);
        given(indexBuilder.build()).willReturn("index");
        given(ruleReadmeLoader.load(checkstyleEnabled)).willReturn(Stream.of("checkstyle-enabled"));
        given(ruleReadmeLoader.load(checkstyleDisabled)).willReturn(Stream.of("checkstyle-disabled"));
        given(ruleReadmeLoader.load(sevntuEnabled)).willReturn(Stream.of("sevntu-enabled"));
        given(ruleReadmeLoader.load(sevntuDisabled)).willReturn(Stream.of("sevntu-disabled"));
        //when
        readmeWriter.run();
        //then
        final Stream<String> lines = Files.lines(readme, StandardCharsets.UTF_8);
        assertThat(lines).containsExactlyElementsOf(expected);
    }

    private Rule rule(final RuleSource source, final boolean enabled) {
        val rule = new Rule();
        rule.setSource(source);
        rule.setEnabled(enabled);
        return rule;
    }
}
