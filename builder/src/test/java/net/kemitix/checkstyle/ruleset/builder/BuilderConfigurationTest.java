package net.kemitix.checkstyle.ruleset.builder;

import com.google.common.reflect.ClassPath;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
    public void canGetClassPath() throws IOException {
        //when
        final BuilderConfiguration builderConfiguration = new BuilderConfiguration();
        final ClassPath classPath = builderConfiguration.classPath(builderConfiguration.getClass().getClassLoader());
        //then
        assertThat(classPath).isNotNull();
    }

    @Test
    public void canGetCheckClasses() {
        //given
        final PackageScanner packageScanner = mock(PackageScanner.class);
        final String checkstyleClass = "checkstyle class";
        given(packageScanner.apply(RuleSource.CHECKSTYLE)).willReturn(Stream.of(checkstyleClass));
        final String sevntuClass = "sevntu class";
        given(packageScanner.apply(RuleSource.SEVNTU)).willReturn(Stream.of(sevntuClass));
        //when
        final Map<RuleSource, List<String>> checkClasses = new BuilderConfiguration().checkClasses(packageScanner);
        //then
        assertThat(checkClasses).containsOnlyKeys(RuleSource.values());
        assertThat(checkClasses)
                .containsEntry(RuleSource.CHECKSTYLE, Collections.singletonList(checkstyleClass))
                .containsEntry(RuleSource.SEVNTU, Collections.singletonList(sevntuClass));
    }
}
