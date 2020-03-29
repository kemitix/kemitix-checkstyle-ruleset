package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;

/**
 * Properties for template files.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "template")
class TemplateProperties {

    /**
     * Template for Checkstyle XML files.
     */
    private Path checkstyleXml;

    /**
     * Template for README.md file.
     */
    private File readmeTemplate;

    /**
     * The directory containing the README fragments.
     */
    private Path readmeFragments;
}
