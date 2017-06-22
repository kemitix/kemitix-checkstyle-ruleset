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
        assertThat(RuleSource.valueOf("KEMITIX")).isEqualTo(RuleSource.KEMITIX);
    }

    @Test
    public void values() throws Exception {
        //given
        val expected = Arrays.asList("CHECKSTYLE", "SEVNTU", "KEMITIX");
        //when
        val values = Arrays.stream(RuleSource.values())
                           .map(RuleSource::toString);
        //then
        assertThat(values).containsExactlyElementsOf(expected);
    }

    @Test
    public void basePackages() {
        //given
        final String puppycrawl = "puppycrawl";
        final String sevntu = "sevntu";
        final String kemitix = "kemitix";
        //then
        assertThat(RuleSource.CHECKSTYLE.getBasePackage()).contains(puppycrawl)
                                                          .doesNotContain(sevntu);
        assertThat(RuleSource.SEVNTU.getBasePackage()).contains(sevntu)
                                                      .doesNotContain(puppycrawl);
        assertThat(RuleSource.KEMITIX.getBasePackage()).contains(kemitix)
                                                      .doesNotContain(puppycrawl);
    }
}
