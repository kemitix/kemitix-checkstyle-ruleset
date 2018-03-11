package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultPackageScanner}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultPackageScannerTest {

    private PackageScanner scanner = new DefaultPackageScanner();

    @Test
    public void canScanCheckstylePackage() {
        //when
        final Stream<String> result = scanner.apply(RuleSource.CHECKSTYLE);
        //then
        assertThat(result).allMatch(cn -> cn.startsWith(RuleSource.CHECKSTYLE.getBasePackage()))
                          .contains("com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck");
    }

    @Test
    public void canScanSevntuPackage() {
        //when
        final Stream<String> result = scanner.apply(RuleSource.SEVNTU);
        //then
        assertThat(result).allMatch(cn -> cn.startsWith(RuleSource.SEVNTU.getBasePackage()))
                          .contains("com.github.sevntu.checkstyle.checks.design.NestedSwitchCheck");
    }
}
