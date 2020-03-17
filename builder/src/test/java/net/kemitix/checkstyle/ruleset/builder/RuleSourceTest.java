package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Test;

import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RuleSource}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RuleSourceTest {

    private String name = UUID.randomUUID().toString();
    private String basePackage = UUID.randomUUID().toString();
    private boolean enabled = new Random().nextBoolean();

    @Test
    public void create() {
        //given
        RuleSource ruleSource = RuleSourceMother.create(name, enabled, basePackage);
        //then
        assertThat(ruleSource.getName()).isEqualTo(name);
        assertThat(ruleSource.getBasePackage()).isEqualTo(basePackage);
        assertThat(ruleSource.isEnabled()).isEqualTo(enabled);
    }

}
