package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadmeBuilder {

    public static final String CHECKSTYLE = "checkstyle";
    public static final String SEVNTU = "sevntu";

    private final ReadmeIndexBuilder indexBuilder;
    private final RuleLoader ruleLoader;

    /**
     * Creates a readme based on the template, inserting lists of the enabled
     * and disabled checkstyle and sevntu rules.
     *
     * @param readmeTemplate the String.format compatible template
     * @return the formatted readme document.
     */
    public String build(final String readmeTemplate) {
        return String.format(readmeTemplate,
                indexBuilder.build(),
                ruleLoader.loadEnabled(CHECKSTYLE),
                ruleLoader.loadEnabled(SEVNTU),
                ruleLoader.loadDisabled(CHECKSTYLE),
                ruleLoader.loadDisabled(SEVNTU));
    }

}
