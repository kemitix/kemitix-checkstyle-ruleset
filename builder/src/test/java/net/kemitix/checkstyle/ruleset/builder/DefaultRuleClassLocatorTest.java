package net.kemitix.checkstyle.ruleset.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link DefaultRuleClassLocator}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultRuleClassLocatorTest {

    private final Map<RuleSource, List<String>> checkClasses = new HashMap<>();

    private SourcesProperties sourceProperties = new SourcesProperties();

    private final RuleSource checkstyleRuleSource = RuleSourceMother.checkstyle.get();
    private final RuleSource sevntuRuleSource = RuleSourceMother.sevntu.get();

    @Test
    public void canLookupRuleWithClassNameEndingInCheck() {
        //given
        final String rulename = "RegexpOnFilename";
        final String expected = "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck";
        final List<String> checkstyleClasses = Collections.singletonList(expected);
        final List<String> sevntuClasses = Collections.emptyList();
        checkClasses.put(checkstyleRuleSource, checkstyleClasses);
        checkClasses.put(sevntuRuleSource, sevntuClasses);
        sourceProperties.setSources(Arrays.asList(
                checkstyleRuleSource, sevntuRuleSource
        ));
        final Rule rule = createCheckstyleRule(rulename);
        DefaultRuleClassLocator subject =
                new DefaultRuleClassLocator(
                        checkClasses,
                        sourceProperties);
        //when
        final String result = subject.apply(rule);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void canLookupRuleWithClassNameMatchingRuleName() {
        //given
        final String rulename = "RegexpOnFilename";
        final String expected = "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilename";
        final List<String> checkstyleClasses = Collections.singletonList(expected);
        final List<String> sevntuClasses = Collections.emptyList();
        checkClasses.put(checkstyleRuleSource, checkstyleClasses);
        checkClasses.put(sevntuRuleSource, sevntuClasses);
        sourceProperties.setSources(Arrays.asList(
                checkstyleRuleSource, sevntuRuleSource
        ));
        final Rule rule = createCheckstyleRule(rulename);
        DefaultRuleClassLocator subject =
                new DefaultRuleClassLocator(
                        checkClasses,
                        sourceProperties);
        //when
        final String result = subject.apply(rule);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void throwsExceptionWhenCheckstyleClassNotFound() {
        //given
        final String rulename = "RegexpOnFilename";
        final String expected = "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameNoMatch";
        final List<String> checkstyleClasses = Collections.singletonList(expected);
        final List<String> sevntuClasses = Collections.emptyList();
        checkClasses.put(checkstyleRuleSource, checkstyleClasses);
        checkClasses.put(sevntuRuleSource, sevntuClasses);
        final Rule rule = createCheckstyleRule(rulename);
        DefaultRuleClassLocator subject =
                new DefaultRuleClassLocator(
                        checkClasses,
                        sourceProperties);
        //then
        assertThatThrownBy(() -> subject.apply(rule))
                .isInstanceOf(CheckstyleClassNotFoundException.class)
                .hasMessage("No class found to implement RegexpOnFilename");
    }

    private Rule createCheckstyleRule(final String rulename) {
        final Rule rule = new Rule();
        rule.setSource(checkstyleRuleSource.getName());
        rule.setName(rulename);
        return rule;
    }
}
