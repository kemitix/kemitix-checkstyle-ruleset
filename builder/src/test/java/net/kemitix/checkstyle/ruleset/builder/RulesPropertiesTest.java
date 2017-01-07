package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RulesProperties}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RulesPropertiesTest {

    private RulesProperties rulesProperties;

    @Before
    public void setUp() throws Exception {
        rulesProperties = new RulesProperties();
    }

    @Test
    public void getEmpty() throws Exception {
        assertThat(rulesProperties.getRules()).isEmpty();
    }
    @Test
    public void getContent() throws Exception {
        //given
        final Rule rule = new Rule();
        //when
        rulesProperties.getRules().add(rule);
        //then
        assertThat(rulesProperties.getRules()).containsExactly(rule);
    }
}
