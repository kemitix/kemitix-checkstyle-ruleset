package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.net.URI;

/**
 * Tests for {@link Rule}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RuleTest {

    private Rule rule;

    private RuleSource ruleSource = RuleSourceMother.checkstyle.get();

    @Before
    public void setUp() throws Exception {
        rule = new Rule();
    }

    @Test
    public void setAndGet() throws Exception {
        //given
        val name = "name";
        val parent = RuleParent.TREEWALKER;
        val level = RuleLevel.LAYOUT;
        val enabled = true;
        val insuppressible = true;
        val uri = URI.create("rule://name.md");
        val reason = "reason";
        val key = "key";
        val value = "value";
        //when
        rule.setName(name);
        rule.setParent(parent);
        rule.setLevel(level);
        rule.setSource(ruleSource.getName());
        rule.setEnabled(enabled);
        rule.setInsuppressible(insuppressible);
        rule.setUri(uri);
        rule.setReason(reason);
        rule.getProperties()
            .put(key, value);
        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rule.getName())
                  .as("set/getName()")
                  .isEqualTo(name);
            softly.assertThat(rule.getParent())
                  .as("set/getParent()")
                  .isEqualTo(parent);
            softly.assertThat(rule.getLevel())
                  .as("set/getLevel()")
                  .isEqualTo(level);
            softly.assertThat(rule.getSource())
                  .as("set/getSource()")
                  .isEqualTo(ruleSource.getName());
            softly.assertThat(rule.isEnabled())
                  .as("set/isEnabled()")
                  .isEqualTo(enabled);
            softly.assertThat(rule.isInsuppressible())
                  .as("set/isInsuppressible()")
                  .isEqualTo(insuppressible);
            softly.assertThat(rule.getUri())
                  .as("set/getUri()")
                  .isEqualTo(uri);
            softly.assertThat(rule.getReason())
                  .as("set/getReason()")
                  .isEqualTo(reason);
            softly.assertThat(rule.getProperties())
                  .as("getProperties()")
                  .containsEntry(key, value);
        });
    }
}
