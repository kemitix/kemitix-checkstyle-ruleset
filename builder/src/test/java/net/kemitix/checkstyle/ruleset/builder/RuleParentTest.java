package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RuleParent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RuleParentTest {

    @Test
    public void valueOf() throws Exception {
        assertThat(RuleParent.valueOf("CHECKER")).isEqualTo(RuleParent.CHECKER);
        assertThat(RuleParent.valueOf("TREEWALKER")).isEqualTo(RuleParent.TREEWALKER);
    }

    @Test
    public void values() throws Exception {
        //given
        val expected = Arrays.asList("CHECKER", "TREEWALKER");
        //when
        val values = Arrays.stream(RuleParent.values())
                           .map(RuleParent::toString);
        //then
        assertThat(values).containsExactlyElementsOf(expected);
    }
}
