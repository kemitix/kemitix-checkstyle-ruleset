package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link DefaultRuleClassLocator}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultRuleClassLocatorTest {

    private DefaultRuleClassLocator subject;

    private PackageScanner packageScanner = mock(PackageScanner.class);

    @Before
    public void setUp() {
        subject = new DefaultRuleClassLocator(packageScanner);
    }

    @Test
    public void canLookupRuleWithClassNameEndingInCheck() {
        //given
        final String rulename = "RegexpOnFilename";
        final String expected = "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck";
        final List<String> checkstyleClasses = Collections.singletonList(expected);
        final List<String> sevntuClasses = Collections.emptyList();
        given(packageScanner.apply(RuleSource.CHECKSTYLE)).willReturn(checkstyleClasses.stream());
        given(packageScanner.apply(RuleSource.SEVNTU)).willReturn(sevntuClasses.stream());
        subject.init();
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
        given(packageScanner.apply(RuleSource.CHECKSTYLE)).willReturn(checkstyleClasses.stream());
        given(packageScanner.apply(RuleSource.SEVNTU)).willReturn(sevntuClasses.stream());
        subject.init();
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
        given(packageScanner.apply(RuleSource.CHECKSTYLE)).willReturn(checkstyleClasses.stream());
        given(packageScanner.apply(RuleSource.SEVNTU)).willReturn(sevntuClasses.stream());
        subject.init();
        final Rule rule = createCheckstyleRule(rulename);
        //then
        assertThatThrownBy(() -> subject.apply(rule))
                .isInstanceOf(CheckstyleClassNotFoundException.class)
                .hasMessage("No class found to implement RegexpOnFilename");
    }

    @Test
    public void throwsNullPointerExceptionWhenInitNotCalled() {
        assertThatNullPointerException()
                .isThrownBy(() -> subject.apply(new Rule()))
                .withMessage("init() method not called");
    }

    private Rule createCheckstyleRule(final String rulename) {
        final Rule rule = new Rule();
        rule.setSource(RuleSource.CHECKSTYLE);
        rule.setName(rulename);
        return rule;
    }
}
