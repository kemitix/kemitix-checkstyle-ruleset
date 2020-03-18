package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ClassGraphPackageScanner}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ClassGraphPackageScannerTest {

    private PackageScanner scanner = new ClassGraphPackageScanner();

    private final RuleSource checkstyleRuleSource = RuleSourceMother.checkstyle.get();

    private final RuleSource sevntuRuleSource = RuleSourceMother.sevntu.get();

    @Test
    public void canScanCheckstylePackage() {
        //when
        final List<String> result = scanner.apply(checkstyleRuleSource);
        //then
        assertThat(result).allMatch(cn -> cn.startsWith(checkstyleRuleSource.getBasePackage()))
                          .contains("com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck");
    }

    @Test
    public void canScanSevntuPackage() {
        //when
        final List<String> result = scanner.apply(sevntuRuleSource);
        //then
        assertThat(result).allMatch(cn -> cn.startsWith(sevntuRuleSource.getBasePackage()))
                          .contains("com.github.sevntu.checkstyle.checks.design.NestedSwitchCheck");
    }
}
