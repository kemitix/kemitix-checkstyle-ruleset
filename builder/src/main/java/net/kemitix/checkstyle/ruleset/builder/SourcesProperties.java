package net.kemitix.checkstyle.ruleset.builder;

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
@Configuration
@ConfigurationProperties("")
class SourcesProperties {

    private final List<RuleSource> sources = new ArrayList<>();

    public void setSources(final List<RuleSource> sources) {
        this.sources.clear();
        this.sources.addAll(sources);
    }

    public List<RuleSource> getSources() {
        return new ArrayList<>(sources);
    }

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
