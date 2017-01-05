package net.kemitix.checkstyle.ruleset.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Creates the checkstyle ruleset files.
 *
 * <p>This application is intended to only to be used by the developer to create the actual checkstyle xml files that
 * this module provides.</p>
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@EnableConfigurationProperties({RulesProperties.class, OutputProperties.class})
@SpringBootApplication
public class CheckstyleRulesetBuilderApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CheckstyleRulesetBuilderApplication.class, args);
    }
}
