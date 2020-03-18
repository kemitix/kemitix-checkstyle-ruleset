package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SourcesPropertiesTest {

    private SourcesProperties sourcesProperties;

    @Before
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
        sourcesProperties.getSources().add(source);
        //then
        assertThat(sourcesProperties.getSources()).containsExactly(source);
    }
}
