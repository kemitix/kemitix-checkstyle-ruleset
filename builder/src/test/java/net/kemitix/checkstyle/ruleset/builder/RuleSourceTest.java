package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RuleSource}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RuleSourceTest {

    @Test
    public void valueOf() throws Exception {
        assertThat(RuleSource.valueOf("CHECKSTYLE")).isEqualTo(RuleSource.CHECKSTYLE);
        assertThat(RuleSource.valueOf("SEVNTU")).isEqualTo(RuleSource.SEVNTU);
    }

    @Test
    public void values() throws Exception {
        //given
        val expected = Arrays.asList("CHECKSTYLE", "SEVNTU");
        //when
        val values = Arrays.stream(RuleSource.values())
                           .map(RuleSource::toString);
        //then
        assertThat(values).containsExactlyElementsOf(expected);
    }
}
