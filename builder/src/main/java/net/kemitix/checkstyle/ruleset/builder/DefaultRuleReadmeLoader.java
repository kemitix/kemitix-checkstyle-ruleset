package net.kemitix.checkstyle.ruleset.builder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Slf4j
@Component
@RequiredArgsConstructor
class DefaultRuleReadmeLoader implements RuleReadmeLoader {

    private final TemplateProperties templateProps;

    @Override
    @SuppressFBWarnings("EXS_EXCEPTION_SOFTENING_NO_CHECKED")
    public Stream<String> load(final Rule rule) {
        if (rule.isEnabled()) {
            final Path resolve =
                    templateProps.getReadmeFragments()
                            .resolve(rule.getName() + ".md");
            log.info("Loading fragment: {}", resolve);
            try {
                return Stream.concat(Stream.of(formatRuleHeader(rule)), Files.lines(resolve));
            } catch (IOException e) {
                throw new ReadmeFragmentNotFoundException(rule.getName(), e);
            }
        } else {
            return Stream.of(formatRuleHeader(rule), "", rule.getReason());
        }
    }

    private String formatRuleHeader(final Rule rule) {
        return String.format("#### [%s](%s)", rule.getName(), rule.getUri());
    }
}
