package net.kemitix.checkstyle.ruleset.builder;

import com.google.common.reflect.ClassPath;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultPackageScanner}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultPackageScannerTest {

    private ClassPath classPath;

    private DefaultPackageScanner scanner;

    @Before
    public void setUp() throws Exception {
        classPath = ClassPath.from(getClass().getClassLoader());
        scanner = new DefaultPackageScanner(classPath);
    }

    @Test
    public void canScanCheckstylePackage() throws IOException {
        //when
        final Stream<String> result = scanner.apply(RuleSource.CHECKSTYLE);
        //then
        assertThat(result).allMatch(cn -> cn.startsWith(RuleSource.CHECKSTYLE.getBasePackage()))
                          .contains("com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck");
    }

    @Test
    public void canScanSevntuPackage() throws IOException {
        //when
        final Stream<String> result = scanner.apply(RuleSource.SEVNTU);
        //then
        assertThat(result).allMatch(cn -> cn.startsWith(RuleSource.SEVNTU.getBasePackage()))
                          .contains("com.github.sevntu.checkstyle.checks.design.NestedSwitchCheck");
    }
}
