package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link DefaultRuleClassLocator}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultRuleClassLocatorTest {

    private DefaultRuleClassLocator subject;

    @Mock
    private PackageScanner packageScanner;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new DefaultRuleClassLocator(packageScanner);
    }

    @Test
    public void canLookupRule() {
        //given
        final String rulename = "RegexpOnFilename";
        final String expected = "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck";
        final List<String> checkstyleClasses = Collections.singletonList(expected);
        final List<String> sevntuClasses = Collections.emptyList();
        final List<String> kemitixClasses = Collections.emptyList();
        given(packageScanner.apply(RuleSource.CHECKSTYLE)).willReturn(checkstyleClasses.stream());
        given(packageScanner.apply(RuleSource.SEVNTU)).willReturn(sevntuClasses.stream());
        given(packageScanner.apply(RuleSource.KEMITIX)).willReturn(kemitixClasses.stream());
        subject.init();
        final Rule rule = createCheckstyleRule(rulename);
        //when
        final String result = subject.apply(rule);
        //then
        assertThat(result).isEqualTo(expected);
    }

    private Rule createCheckstyleRule(final String rulename) {
        final Rule rule = new Rule();
        rule.setSource(RuleSource.CHECKSTYLE);
        rule.setName(rulename);
        return rule;
    }
}
