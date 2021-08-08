package net.kemitix.checkstyle.ruleset.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class SourcesPropertiesTest {

    private SourcesProperties sourcesProperties;

    @BeforeEach
    public void setUp() {
        sourcesProperties = new SourcesProperties();
    }

    @Test
    public void getEmpty() {
        assertThat(sourcesProperties.getSources()).isEmpty();
    }
    @Test
    public void getContent() {
        //given
        final RuleSource source =
                RuleSourceMother.create("name", true, "package");
        //when
        sourcesProperties.setSources(Collections.singletonList(source));
        //then
        assertThat(sourcesProperties.getSources()).containsExactly(source);
    }
}
