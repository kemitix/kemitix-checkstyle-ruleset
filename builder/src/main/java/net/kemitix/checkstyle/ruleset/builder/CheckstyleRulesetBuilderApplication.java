package net.kemitix.checkstyle.ruleset.builder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Creates the checkstyle ruleset files.
 *
 * <p>This application is intended to only to be used by the developer to create the actual checkstyle xml files that
 * this module provides.</p>
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Slf4j
@SpringBootApplication
public class CheckstyleRulesetBuilderApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CheckstyleRulesetBuilderApplication.class, args);
    }

    @Bean
    public CommandLineRunner listRules(final RulesProperties rulesProperties) {
        return args -> rulesProperties.getRules()
                                      .stream()
                                      .map(Rule::getName)
                                      .map(name -> String.format("- %s", name))
                                      .forEach(System.out::println);
    }
}
