package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultReadmeIndexBuilder}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultReadmeIndexBuilderTest {

    private DefaultReadmeIndexBuilder indexBuilder;

    private RulesProperties rulesProperties;

    @Before
    public void setUp() throws Exception {
        rulesProperties = new RulesProperties();
        indexBuilder = new DefaultReadmeIndexBuilder(rulesProperties);
    }

    @Test
    public void createIndex() throws Exception {
        //given
        final List<String> expectedIndexItems = new ArrayList<>(6);
        expectedIndexItems.add("[a](#a)|layout|checkstyle|Yes|");
        expectedIndexItems.add("[b](#b)|naming|sevntu||No");
        expectedIndexItems.add("[c](#c)|javadoc|checkstyle|Yes|");
        expectedIndexItems.add("[d](#d)|tweaks|checkstyle|Yes|");
        expectedIndexItems.add("[e](#e)|complexity|checkstyle|Yes|");
        expectedIndexItems.add("[f](#f)|unspecified|checkstyle|Yes|");
        val rules = rulesProperties.getRules();
        rules.add(rule("a", RuleLevel.LAYOUT, RuleSource.CHECKSTYLE, true, true));
        rules.add(rule("b", RuleLevel.NAMING, RuleSource.SEVNTU, false, false));
        rules.add(rule("c", RuleLevel.JAVADOC, RuleSource.CHECKSTYLE, true, true));
        rules.add(rule("d", RuleLevel.TWEAKS, RuleSource.CHECKSTYLE, true, true));
        rules.add(rule("e", RuleLevel.COMPLEXITY, RuleSource.CHECKSTYLE, true, true));
        rules.add(rule("f", RuleLevel.UNSPECIFIED, RuleSource.CHECKSTYLE, true, true));
        //when
        val index = indexBuilder.build()
                                .split("\n");
        //then
        assertThat(index).containsExactlyElementsOf(expectedIndexItems);
    }

    private Rule rule(
            final String name, final RuleLevel level, final RuleSource source, final boolean enabled,
            final boolean supressible
                     ) {
        val rule = new Rule();
        rule.setName(name);
        rule.setLevel(level);
        rule.setSource(source);
        rule.setEnabled(enabled);
        rule.setInsuppressible(!supressible);
        return rule;
    }
}
