package net.kemitix.checkstyle.ruleset.builder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Properties defining the sources.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties("")
class SourcesProperties {

    private List<RuleSource> sources = new ArrayList<>();

    /**
     * Search for a RuleSource by name.
     *
     * @param sourceName the name of the source
     * @return an Optional containing the RuleSource if found
     */
    public Optional<RuleSource> findSource(final String sourceName) {
        return sources.stream()
                .filter(ruleSource -> ruleSource.getName().equals(sourceName))
                .findFirst();
    }
}
