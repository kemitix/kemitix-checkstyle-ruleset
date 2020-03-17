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
        String enabledCheckstyle = ruleLoader.loadEnabled(CHECKSTYLE);
        String enabledSevntu = ruleLoader.loadEnabled(SEVNTU);
        String disabledCheckstyle = ruleLoader.loadDisabled(CHECKSTYLE);
        String disabledSevntu = ruleLoader.loadDisabled(SEVNTU);
        return String.format(readmeTemplate,
                indexBuilder.build(),
                enabledCheckstyle,
                enabledSevntu,
                disabledCheckstyle,
                disabledSevntu);
    }

}
