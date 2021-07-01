package net.kemitix.checkstyle.ruleset.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ReadmeBuilderTest {

    @Mock
    private ReadmeIndexBuilder indexBuilder;
    @Mock
    private RuleLoader ruleLoader;
    private ReadmeBuilder readmeBuilder;
    private String template = "i:%s,ec:%s,es:%s,dc:%s,ds:%s";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        readmeBuilder = new ReadmeBuilder(indexBuilder, ruleLoader);
    }

    @Test
    public void canBuild() {
        //given
        given(indexBuilder.build()).willReturn("index");
        given(ruleLoader.loadEnabled("checkstyle")).willReturn("enabled-checkstyle");
        given(ruleLoader.loadEnabled("sevntu")).willReturn("enabled-sevntu");
        given(ruleLoader.loadDisabled("checkstyle")).willReturn("disabled-checkstyle");
        given(ruleLoader.loadDisabled("sevntu")).willReturn("disabled-sevntu");
        //when
        String result = readmeBuilder.build(template);
        //then
        assertThat(result).isEqualTo(
                "i:index," +
                        "ec:enabled-checkstyle," +
                        "es:enabled-sevntu," +
                        "dc:disabled-checkstyle," +
                        "ds:disabled-sevntu");
    }

}