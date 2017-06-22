package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
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

    @org.junit.Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private ReadmeWriter readmeWriter;

    private RulesProperties rulesProperties;

    @Mock
    private RuleReadmeLoader ruleReadmeLoader;

    @Mock
    private ReadmeIndexBuilder indexBuilder;

    private Path readme;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final Path template = folder.newFile("README-template.md")
                                    .toPath();
        Files.write(template, Arrays.asList("i:%s", "ce:%s", "se:%s", "ke:%s", "cd:%s", "sd:%s"));
        final TemplateProperties templateProperties = new TemplateProperties();
        templateProperties.setReadmeTemplate(template);
        final Path fragments = folder.newFolder("fragments")
                                     .toPath();
        templateProperties.setReadmeFragments(fragments);
        final OutputProperties outputProperties = new OutputProperties();
        readme = folder.newFile("README.md")
                       .toPath();
        outputProperties.setReadme(readme);
        rulesProperties = new RulesProperties();
        readmeWriter =
                new ReadmeWriter(templateProperties, outputProperties, rulesProperties, ruleReadmeLoader, indexBuilder);
    }

    @Test
    public void createReadme() throws Exception {
        //given
        val expected = Arrays.asList("i:index", "ce:checkstyle-enabled", "se:sevntu-enabled", "ke:kemitix-enabled",
                                     "cd:checkstyle-disabled", "sd:sevntu-disabled"
                                    );
        val rules = rulesProperties.getRules();
        final Rule checkstyleEnabled = rule(RuleSource.CHECKSTYLE, true, "checkstyle enabled");
        final Rule checkstyleDisabled = rule(RuleSource.CHECKSTYLE, false, "checkstyle disabled");
        final Rule sevntuEnabled = rule(RuleSource.SEVNTU, true, "sevntu enabled");
        final Rule sevntuDisabled = rule(RuleSource.SEVNTU, false, "sevntu disabled");
        final Rule kemitixEnabled = rule(RuleSource.KEMITIX, true, "kemitix enabled");
        rules.add(checkstyleEnabled);
        rules.add(checkstyleDisabled);
        rules.add(sevntuEnabled);
        rules.add(sevntuDisabled);
        rules.add(kemitixEnabled);
        given(indexBuilder.build()).willReturn("index");
        given(ruleReadmeLoader.load(checkstyleEnabled)).willReturn(Stream.of("checkstyle-enabled"));
        given(ruleReadmeLoader.load(checkstyleDisabled)).willReturn(Stream.of("checkstyle-disabled"));
        given(ruleReadmeLoader.load(sevntuEnabled)).willReturn(Stream.of("sevntu-enabled"));
        given(ruleReadmeLoader.load(sevntuDisabled)).willReturn(Stream.of("sevntu-disabled"));
        given(ruleReadmeLoader.load(kemitixEnabled)).willReturn(Stream.of("kemitix-enabled"));
        //when
        readmeWriter.run();
        //then
        final Stream<String> lines = Files.lines(readme, StandardCharsets.UTF_8);
        assertThat(lines).containsExactlyElementsOf(expected);
    }

    private Rule rule(final RuleSource source, final boolean enabled, final String name) {
        val rule = new Rule();
        rule.setName(name);
        rule.setSource(source);
        rule.setEnabled(enabled);
        return rule;
    }

    @Test
    public void rulesAreAlphabetical() throws Exception {
        //given
        val expected = Arrays.asList("ce:alpha", "beta", "delta");
        val rules = rulesProperties.getRules();
        final Rule alpha = rule(RuleSource.CHECKSTYLE, true, "alpha");
        final Rule beta = rule(RuleSource.CHECKSTYLE, true, "beta");
        final Rule delta = rule(RuleSource.CHECKSTYLE, true, "delta");
        rules.add(alpha);
        rules.add(delta);
        rules.add(beta);
        given(indexBuilder.build()).willReturn("index");
        given(ruleReadmeLoader.load(alpha)).willReturn(Stream.of(alpha.getName()));
        given(ruleReadmeLoader.load(beta)).willReturn(Stream.of(beta.getName()));
        given(ruleReadmeLoader.load(delta)).willReturn(Stream.of(delta.getName()));
        //when
        readmeWriter.run();
        //then
        final Stream<String> lines = Files.lines(readme, StandardCharsets.UTF_8);
        assertThat(lines).containsSequence(expected);
    }
}
