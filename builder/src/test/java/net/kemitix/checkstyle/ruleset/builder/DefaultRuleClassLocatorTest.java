package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link DefaultRuleClassLocator}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultRuleClassLocatorTest {

    private final Map<RuleSource, List<String>> checkClasses = new HashMap<>();

    private final DefaultRuleClassLocator subject = new DefaultRuleClassLocator(checkClasses);

    @Test
    public void canLookupRuleWithClassNameEndingInCheck() {
        //given
        final String rulename = "RegexpOnFilename";
        final String expected = "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck";
        final List<String> checkstyleClasses = Collections.singletonList(expected);
        final List<String> sevntuClasses = Collections.emptyList();
        checkClasses.put(RuleSource.CHECKSTYLE, checkstyleClasses);
        checkClasses.put(RuleSource.SEVNTU, sevntuClasses);
        final Rule rule = createCheckstyleRule(rulename);
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
        checkClasses.put(RuleSource.CHECKSTYLE, checkstyleClasses);
        checkClasses.put(RuleSource.SEVNTU, sevntuClasses);
        final Rule rule = createCheckstyleRule(rulename);
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
        checkClasses.put(RuleSource.CHECKSTYLE, checkstyleClasses);
        checkClasses.put(RuleSource.SEVNTU, sevntuClasses);
        final Rule rule = createCheckstyleRule(rulename);
        //then
        assertThatThrownBy(() -> subject.apply(rule))
                .isInstanceOf(CheckstyleClassNotFoundException.class)
                .hasMessage("No class found to implement RegexpOnFilename");
    }

    private Rule createCheckstyleRule(final String rulename) {
        final Rule rule = new Rule();
        rule.setSource(RuleSource.CHECKSTYLE);
        rule.setName(rulename);
        return rule;
    }
}
