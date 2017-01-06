package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Default README fragment loader.
 *
 * @author Paul Campbell (paul.campbell@hubio.com)
 */
@Slf4j
@Component
@RequiredArgsConstructor
class DefaultRuleReadmeLoader implements RuleReadmeLoader {

    private final TemplateProperties templateProperties;

    @Override
    public Stream<String> load(final Rule rule) {
        if (rule.isEnabled()) {
            try {
                final Path resolve = templateProperties.getReadmeFragments()
                                                       .resolve(rule.getName() + ".md");
                log.info("Loading fragment: {}", resolve);
                return Stream.concat(Stream.of(formatRuleHeader(rule)), Files.lines(resolve));
            } catch (IOException e) {
                return Stream.empty();
            }
        } else {
            return Stream.of(formatRuleHeader(rule), "", rule.getReason());
        }
    }

    private String formatRuleHeader(final Rule rule) {
        return String.format("%n#### [%s](%s)", rule.getName(), rule.getUri());
    }
}
