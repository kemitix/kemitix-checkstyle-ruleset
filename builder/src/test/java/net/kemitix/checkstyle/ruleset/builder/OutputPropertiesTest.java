package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Tests for {@link OutputProperties}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class OutputPropertiesTest {

    private OutputProperties outputProperties;

    @Before
    public void setUp() throws Exception {
        outputProperties = new OutputProperties();
    }

    @Test
    public void setAndGet() throws Exception {
        //given
        val directory = Paths.get("directory");
        val rulesetFiles = new HashMap<RuleLevel, String>();
        val readme = Paths.get("readme.md");
        //when
        outputProperties.setDirectory(directory);
        outputProperties.setRulesetFiles(rulesetFiles);
        outputProperties.setReadme(readme);
        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(outputProperties.getDirectory())
                  .as("set/getDirectory()")
                  .isEqualTo(directory);
            softly.assertThat(outputProperties.getRulesetFiles())
                  .as("set/getRulesetFiles()")
                  .isEqualTo(rulesetFiles);
            softly.assertThat(outputProperties.getReadme())
                  .as("set/getReadme()")
                  .isEqualTo(readme);
        });
    }
}
