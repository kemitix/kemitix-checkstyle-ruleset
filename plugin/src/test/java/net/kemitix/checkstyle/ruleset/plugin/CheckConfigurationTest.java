package net.kemitix.checkstyle.ruleset.plugin;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class CheckConfigurationTest {

    @Test
    public void toStringCoverage() {
        //given
        final String rulesetVersion = "ruleset version";
        final String sourceDirectory = "source directory";
        //when
        final String result = CheckConfiguration.builder()
                                                .rulesetVersion(rulesetVersion)
                                                .sourceDirectory(sourceDirectory)
                                                .toString();
        //then
        System.out.println("result = " + result);
        assertThat(result).contains("rulesetVersion=ruleset version")
                          .contains("sourceDirectory=source directory");
    }
}
