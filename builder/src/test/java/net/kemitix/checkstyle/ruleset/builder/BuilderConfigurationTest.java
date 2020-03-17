package net.kemitix.checkstyle.ruleset.builder;

import com.google.common.reflect.ClassPath;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link BuilderConfiguration}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class BuilderConfigurationTest {

    private SourcesProperties sourceProperties = new SourcesProperties();

    @Test
    public void canGetCheckClasses() {
        //given
        final PackageScanner packageScanner = mock(PackageScanner.class);
        final String checkstyleClass = "checkstyle class";
        final RuleSource checkstyleRuleSource = RuleSourceMother.checkstyle.get();
        final RuleSource sevntuRuleSource = RuleSourceMother.sevntu.get();
        given(packageScanner.apply(checkstyleRuleSource)).willReturn(singletonList(checkstyleClass));
        final String sevntuClass = "sevntu class";
        given(packageScanner.apply(sevntuRuleSource)).willReturn(singletonList(sevntuClass));
        sourceProperties.setSources(Arrays.asList(checkstyleRuleSource, sevntuRuleSource));
        BuilderConfiguration subject = new BuilderConfiguration(sourceProperties);
        //when
        final Map<RuleSource, List<String>> checkClasses = subject.checkClasses(packageScanner);
        //then
        assertThat(checkClasses).containsOnlyKeys(Arrays.asList(checkstyleRuleSource, sevntuRuleSource));
        assertThat(checkClasses)
                .containsEntry(checkstyleRuleSource, singletonList(checkstyleClass))
                .containsEntry(sevntuRuleSource, singletonList(sevntuClass));
    }
}
