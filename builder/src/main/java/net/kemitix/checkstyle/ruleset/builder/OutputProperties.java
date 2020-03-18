package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.Map;

/**
 * Properties defining the output files.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "output")
class OutputProperties {

    /**
     * The directory to create the output files in.
     */
    private Path directory;

    /**
     * Checkstyle XML files to create for each ruleset level.
     */
    private Map<RuleLevel, String> rulesetFiles;

    /**
     * The README.md file.
     */
    private String readme;
}
