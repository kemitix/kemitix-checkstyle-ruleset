package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Properties defining the output files.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Slf4j
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "output")
class OutputProperties {

    /**
     * The directory to create the output files in.
     */
    private File directory;

    /**
     * The name of the level 1 ruleset file.
     */
    private String level1;

    /**
     * The name of the level 2 ruleset file.
     */
    private String level2;

    /**
     * The name of the level 3 ruleset file.
     */
    private String level3;

    /**
     * The name of the level 4 ruleset file.
     */
    private String level4;

    /**
     * The name of the level 5 ruleset file.
     */
    private String level5;
}
