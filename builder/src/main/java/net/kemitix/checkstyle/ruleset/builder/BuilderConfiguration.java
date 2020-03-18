package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Configuration for Builder.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
@Configuration
@RequiredArgsConstructor
public class BuilderConfiguration {

    private final SourcesProperties sourcesProperties;

    /**
     * A Map of rules for each RuleSource.
     *
     * @param packageScanner the PackageScanner
     *
     * @return a Map with a list of check classes for each rule source
     */
    @Bean
    public Map<RuleSource, List<String>> checkClasses(final PackageScanner packageScanner) {
        return sourcesProperties.getSources().stream()
                .map(source -> entry(source, packageScanner.apply(source)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static <K, V> AbstractMap.SimpleEntry<K, V> entry(final K key, final V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}
