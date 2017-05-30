package net.kemitix.checkstyle.ruleset.builder;

import com.google.common.reflect.ClassPath;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BuilderConfiguration}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class BuilderConfigurationTest {

    @Test
    public void canGetClassPath() throws IOException {
        //when
        final ClassPath classPath = new BuilderConfiguration().classPath();
        //then
        assertThat(classPath).isNotNull();
    }
}
