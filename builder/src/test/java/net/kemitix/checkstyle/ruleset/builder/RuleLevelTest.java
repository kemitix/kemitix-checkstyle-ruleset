package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RuleLevelTest {

    private final String[] ruleLevelStrings =
            {"DISABLED", "LAYOUT", "NAMING", "JAVADOC", "TWEAKS", "COMPLEXITY", "UNSPECIFIED"};

    @Test
    public void valuesToString() {
        assertThat(Stream.of(RuleLevel.values())
                         .map(RuleLevel::toString)).containsExactly(ruleLevelStrings);
    }

    @Test
    public void stringValueOf() {
        assertThat(Stream.of(ruleLevelStrings)
                         .map(RuleLevel::valueOf)).containsExactly(RuleLevel.values());
    }
}
