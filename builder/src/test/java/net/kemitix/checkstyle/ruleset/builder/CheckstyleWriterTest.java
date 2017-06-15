package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import me.andrz.builder.map.MapBuilder;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link CheckstyleWriter}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CheckstyleWriterTest {

    private static final String TEMPLATE = "C:%s\nTW:%s";

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    @org.junit.Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private CheckstyleWriter checkstyleWriter;

    private OutputProperties outputProperties;

    private TemplateProperties templateProperties;

    private RulesProperties rulesProperties;

    private String ruleName;

    private String ruleClassname;

    private Map<RuleLevel, String> outputFiles;

    private Path outputDirectory;

    @Mock
    private RuleClassLocator ruleClassLocator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ruleName = "RegexpOnFilename";
        ruleClassname = "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck";
        outputProperties = new OutputProperties();
        outputFiles = new MapBuilder<RuleLevel, String>().put(getOutputFile(RuleLevel.DISABLED))
                                                         .put(getOutputFile(RuleLevel.LAYOUT))
                                                         .put(getOutputFile(RuleLevel.NAMING))
                                                         .put(getOutputFile(RuleLevel.JAVADOC))
                                                         .put(getOutputFile(RuleLevel.TWEAKS))
                                                         .put(getOutputFile(RuleLevel.COMPLEXITY))
                                                         .put(getOutputFile(RuleLevel.EXPERIMENTAL))
                                                         .build();
        outputProperties.setRulesetFiles(outputFiles);
        outputDirectory = folder.newFolder()
                                .toPath();
        outputProperties.setDirectory(outputDirectory);
        templateProperties = new TemplateProperties();
        val checkstyleTemplate = folder.newFile("checkstyle-template.xml")
                                       .toPath();
        Files.write(
                checkstyleTemplate, TEMPLATE.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
        templateProperties.setCheckstyleXml(checkstyleTemplate);
        rulesProperties = new RulesProperties();
        checkstyleWriter =
                new CheckstyleWriter(outputProperties, templateProperties, rulesProperties, ruleClassLocator);
        given(ruleClassLocator.apply(any())).willReturn(ruleClassname);
    }

    private Map.Entry<RuleLevel, String> getOutputFile(final RuleLevel level) throws IOException {
        final String xmlFile = String.format("checkstyle-%s.xml", level.toString());
        return new AbstractMap.SimpleImmutableEntry<>(level, xmlFile);
    }

    // write rule that matches current level
    @Test
    public void writeRuleThatMatchesCurrentLevel() throws Exception {
        //given
        val rule = enabledRule(RuleLevel.LAYOUT, RuleParent.TREEWALKER);
        rulesProperties.getRules()
                       .add(rule);
        //when
        checkstyleWriter.run();
        //then
        val lines = loadOutputFile(RuleLevel.LAYOUT);
        assertThat(lines).containsExactly("C:", String.format("TW:<module name=\"%s\"/>", ruleClassname));
    }

    private List<String> loadOutputFile(final RuleLevel level) throws IOException {
        val path = outputDirectory.resolve(outputFiles.get(level));
        assertThat(path).as("Output path exists")
                        .exists();
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    private Rule enabledRule(final RuleLevel level, final RuleParent parent) {
        val rule = new Rule();
        rule.setName(ruleName);
        rule.setSource(RuleSource.CHECKSTYLE);
        rule.setEnabled(true);
        rule.setLevel(level);
        rule.setParent(parent);
        return rule;
    }

    // write rule that is below current level
    @Test
    public void writeRuleThatIsBelowCurrentLevel() throws Exception {
        //given
        val rule = enabledRule(RuleLevel.LAYOUT, RuleParent.TREEWALKER);
        rulesProperties.getRules()
                       .add(rule);
        //when
        checkstyleWriter.run();
        //then
        val lines = loadOutputFile(RuleLevel.NAMING);
        assertThat(lines).containsExactly("C:", String.format("TW:<module name=\"%s\"/>", ruleClassname));
    }

    // write rule with checker parent
    @Test
    public void writeRuleWithCheckerParent() throws Exception {
        //given
        val rule = enabledRule(RuleLevel.LAYOUT, RuleParent.CHECKER);
        rulesProperties.getRules()
                       .add(rule);
        //when
        checkstyleWriter.run();
        //then
        val lines = loadOutputFile(RuleLevel.LAYOUT);
        assertThat(lines).containsExactly(String.format("C:<module name=\"%s\"/>", ruleClassname), "TW:");
    }

    // write rule with properties
    @Test
    public void writeRuleWithProperties() throws Exception {
        //given
        val rule = enabledRule(RuleLevel.LAYOUT, RuleParent.TREEWALKER);
        rule.getProperties()
            .put("key", "value");
        rulesProperties.getRules()
                       .add(rule);
        //when
        checkstyleWriter.run();
        //then
        val lines = loadOutputFile(RuleLevel.LAYOUT);
        assertThat(lines).containsExactly("C:", String.format("TW:<module name=\"%s\">", ruleClassname),
                                          "    <property name=\"key\" value=\"value\"/>", "</module>"
                                         );
    }

    // ignore rule that is above current level
    @Test
    public void ignoreRuleThatIsAboveCurrentLevel() throws Exception {
        //given
        val rule = enabledRule(RuleLevel.NAMING, RuleParent.TREEWALKER);
        rulesProperties.getRules()
                       .add(rule);
        //when
        checkstyleWriter.run();
        //then
        val lines = loadOutputFile(RuleLevel.LAYOUT);
        assertThat(lines).containsExactly("C:", "TW:");
    }

    // ignore rule that has unspecified level
    @Test
    public void ignoreRuleThatHasUnspecifiedLevel() throws Exception {
        //given
        val rule = enabledRule(RuleLevel.UNSPECIFIED, RuleParent.TREEWALKER);
        rulesProperties.getRules()
                       .add(rule);
        //when
        checkstyleWriter.run();
        //then
        val lines = loadOutputFile(RuleLevel.LAYOUT);
        assertThat(lines).containsExactly("C:", "TW:");
    }

    // throw RTE if template not found
    @Test
    public void throwRteIfTemplateNotFound() throws Exception {
        //given
        templateProperties.setCheckstyleXml(Paths.get("garbage"));
        //when
        final ThrowableAssert.ThrowingCallable action = () -> checkstyleWriter.run();
        //then
        assertThatThrownBy(action).isInstanceOf(TemplateNotFoundException.class)
                                  .hasMessage("Missing template: garbage");
    }

    // throw RTE if error writing file
    @Test
    public void throwRteIfErrorWritingFile() throws Exception {
        //given
        final String imaginary = String.join(FILE_SEPARATOR, "", "..", "imaginary");
        outputProperties.setDirectory(Paths.get(imaginary));
        //when
        final ThrowableAssert.ThrowingCallable action = () -> checkstyleWriter.run();
        //then
        assertThatThrownBy(action).isInstanceOf(CheckstyleWriterException.class)
                                  .hasMessageStartingWith(
                                          String.format("java.nio.file.NoSuchFileException: %scheckstyle-",
                                                        imaginary + FILE_SEPARATOR
                                                       ));
    }
}
