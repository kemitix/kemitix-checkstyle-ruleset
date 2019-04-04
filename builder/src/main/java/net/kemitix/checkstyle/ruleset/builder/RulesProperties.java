package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Properties defining the enabled rules for each level.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Getter
@Configuration
@ConfigurationProperties
class RulesProperties {

    /**
     * The Checkstyle checks.
     */
    private List<Rule> rules = new ArrayList<>();
}
