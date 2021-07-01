package net.kemitix.checkstyle.ruleset.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RulesProperties}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RulesPropertiesTest {

    private RulesProperties rulesProperties;

    @BeforeEach
    public void setUp() {
        rulesProperties = new RulesProperties();
    }

    @Test
    public void getEmpty() {
        assertThat(rulesProperties.getRules()).isEmpty();
    }
    @Test
    public void getContent() {
        //given
        final Rule rule = new Rule();
        //when
        rulesProperties.getRules().add(rule);
        //then
        assertThat(rulesProperties.getRules()).containsExactly(rule);
    }
}
