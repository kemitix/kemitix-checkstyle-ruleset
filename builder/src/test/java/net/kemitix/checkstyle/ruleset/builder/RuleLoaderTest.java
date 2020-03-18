package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class RuleLoaderTest {

    private RulesProperties ruleProperties = new RulesProperties();
    @Mock
    private RuleReadmeLoader ruleReadmeLoader;
    private SourcesProperties sourcesProperties = new SourcesProperties();
    private RuleLoader ruleLoader;
    List<RuleSource> ruleSources = sourcesProperties.getSources();
    private List<Rule> rules = ruleProperties.getRules();
    private RuleSource enabledRuleSource;
    private String enabledRuleSourceName = "enabled-" + UUID.randomUUID().toString();
    private String enabledRuleSourcePackage = "enabled-" + UUID.randomUUID().toString();
    private RuleSource disabledRuleSource;
    private String disabledRuleSourceName = "disabled-" + UUID.randomUUID().toString();
    private String disabledRuleSourcePackage = "disabled-" + UUID.randomUUID().toString();
    private Rule enabledRule = new Rule();
    private List<String> enabledRuleLines = new ArrayList<>();
    private String enabledRuleLineOne = "enabled-1-" + UUID.randomUUID().toString();
    private String enabledRuleLineTwo = "enabled-2-" + UUID.randomUUID().toString();
    private String enabledRuleFormatted =
            enabledRuleLineOne + "\n" +
                    enabledRuleLineTwo;
    private Rule disabledRule = new Rule();
    private List<String> disabledRuleLines = new ArrayList<>();
    private String disabledRuleLineOne = "disabled-1-" + UUID.randomUUID().toString();
    private String disabledRuleLineTwo = "disabled-2-" + UUID.randomUUID().toString();
    private String disabledRuleFormatted =
            disabledRuleLineOne + "\n" +
                    disabledRuleLineTwo;
    private String enabledRuleName = "enabled-" + UUID.randomUUID().toString();
    private String disabledRuleName = "disabled-" + UUID.randomUUID().toString();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ruleLoader =
                new RuleLoader(
                        ruleProperties,
                        ruleReadmeLoader,
                        sourcesProperties);

        enabledRule.setEnabled(true);
        enabledRule.setName(enabledRuleName);

        disabledRule.setEnabled(false);
        disabledRule.setName(disabledRuleName);

        enabledRuleSource = RuleSourceMother.create(
                enabledRuleSourceName,
                true,
                enabledRuleSourcePackage);
        ruleSources.add(enabledRuleSource);
        rules.add(enabledRule);
        enabledRuleLines.add(enabledRuleLineOne);
        enabledRuleLines.add(enabledRuleLineTwo);
        given(ruleReadmeLoader.load(enabledRule))
                .willReturn(enabledRuleLines.stream());

        disabledRuleSource = RuleSourceMother.create(
                disabledRuleSourceName,
                false,
                disabledRuleSourcePackage);
        ruleSources.add(disabledRuleSource);
        rules.add(disabledRule);
        disabledRuleLines.add(disabledRuleLineOne);
        disabledRuleLines.add(disabledRuleLineTwo);
        given(ruleReadmeLoader.load(disabledRule))
                .willReturn(disabledRuleLines.stream());
    }

    @Test
    public void enabledForEnabledSource() {
        //given
        enabledRule.setSource(enabledRuleSourceName);
        disabledRule.setSource(enabledRuleSourceName);
        //when
        String result = ruleLoader.loadEnabled(enabledRuleSourceName);
        //then
        assertThat(result).contains(enabledRuleFormatted);
    }

    @Test
    public void enabledForDisabledSource() {
        //given
        enabledRule.setSource(disabledRuleSourceName);
        disabledRule.setSource(disabledRuleSourceName);
        //when
        String result = ruleLoader.loadEnabled(disabledRuleSourceName);
        //then
        assertThat(result).isEmpty();
    }
    @Test
    public void disabledForEnabledSource() {
        //given
        enabledRule.setSource(enabledRuleSourceName);
        disabledRule.setSource(enabledRuleSourceName);
        //when
        String result = ruleLoader.loadDisabled(enabledRuleSourceName);
        //then
        assertThat(result).contains(disabledRuleFormatted);
    }

    @Test
    public void disabledForDisabledSource() {
        //given
        enabledRule.setSource(disabledRuleSourceName);
        disabledRule.setSource(disabledRuleSourceName);
        //when
        String result = ruleLoader.loadDisabled(disabledRuleSourceName);
        //then
        assertThat(result)
                .contains(enabledRuleFormatted)
                .contains(disabledRuleFormatted);
    }
}
