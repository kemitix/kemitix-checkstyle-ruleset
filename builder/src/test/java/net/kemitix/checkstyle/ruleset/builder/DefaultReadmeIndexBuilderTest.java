package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
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
        RuleSource checkStyleRuleSource = RuleSourceMother.checkstyle.get();
        RuleSource sevntuRuleSource = RuleSourceMother.sevntu.get();
        rules.add(rule("a", RuleLevel.LAYOUT, checkStyleRuleSource, true, true));
        rules.add(rule("b", RuleLevel.NAMING, sevntuRuleSource, false, false));
        rules.add(rule("c", RuleLevel.JAVADOC, checkStyleRuleSource, true, true));
        rules.add(rule("d", RuleLevel.TWEAKS, checkStyleRuleSource, true, true));
        rules.add(rule("e", RuleLevel.COMPLEXITY, checkStyleRuleSource, true, true));
        rules.add(rule("f", RuleLevel.UNSPECIFIED, checkStyleRuleSource, true, true));
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
        rule.setSource(source.getName());
        rule.setEnabled(enabled);
        rule.setInsuppressible(!supressible);
        return rule;
    }
}
