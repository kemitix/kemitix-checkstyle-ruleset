package net.kemitix.checkstyle.ruleset.builder;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Tests for {@link TemplateProperties}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class TemplatePropertiesTest {

    private TemplateProperties templateProperties;

    @BeforeEach
    public void setUp() throws Exception {
        templateProperties = new TemplateProperties();
    }

    @Test
    public void setAndGet() throws Exception {
        //given
        final Path checkstyleXml = Paths.get("checkstyle.xml");
        final File readmeTemplate = Paths.get("readme.md").toFile();
        final Path readmeFragments = Paths.get("readme.dir");
        //when
        templateProperties.setCheckstyleXml(checkstyleXml);
        templateProperties.setReadmeTemplate(readmeTemplate);
        templateProperties.setReadmeFragments(readmeFragments);
        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(templateProperties.getCheckstyleXml())
                  .as("set/getCheckstyleXml()")
                  .isEqualTo(checkstyleXml);
            softly.assertThat(templateProperties.getReadmeTemplate())
                  .as("set/getReadmeTemplate()")
                  .isEqualTo(readmeTemplate);
            softly.assertThat(templateProperties.getReadmeFragments())
                  .as("set/getReadmeFragments()")
                  .isEqualTo(readmeFragments);
        });
    }
}
