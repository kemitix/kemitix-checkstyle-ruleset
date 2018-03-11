package net.kemitix.checkstyle.ruleset.builder;

import com.google.common.reflect.ClassPath;
import org.junit.Test;

import java.io.IOException;
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

    @Test
    public void canGetCheckClasses() {
        //given
        final PackageScanner packageScanner = mock(PackageScanner.class);
        final String checkstyleClass = "checkstyle class";
        given(packageScanner.apply(RuleSource.CHECKSTYLE)).willReturn(singletonList(checkstyleClass));
        final String sevntuClass = "sevntu class";
        given(packageScanner.apply(RuleSource.SEVNTU)).willReturn(singletonList(sevntuClass));
        //when
        final Map<RuleSource, List<String>> checkClasses = new BuilderConfiguration().checkClasses(packageScanner);
        //then
        assertThat(checkClasses).containsOnlyKeys(RuleSource.values());
        assertThat(checkClasses)
                .containsEntry(RuleSource.CHECKSTYLE, singletonList(checkstyleClass))
                .containsEntry(RuleSource.SEVNTU, singletonList(sevntuClass));
    }
}
