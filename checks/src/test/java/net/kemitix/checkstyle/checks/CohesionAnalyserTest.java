package net.kemitix.checkstyle.checks;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultCohesionAnalyser}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CohesionAnalyserTest {

    private final CohesionAnalyser analyser = new DefaultCohesionAnalyser();

    private Map<String, Set<String>> fieldsAccessByMethod;

    private Map<String, Set<String>> methodsInvokedByMethod;

    private Set<String> nonPrivateMethods;

    private Consumer<CohesionAnalysisResult> resultConsumer;

    private CohesionAnalysisResult result;

    @Before
    public void setUp() {
        fieldsAccessByMethod = new HashMap<>();
        methodsInvokedByMethod = new HashMap<>();
        nonPrivateMethods = new HashSet<>();
        resultConsumer = cohesionAnalysisResult -> result = cohesionAnalysisResult;
    }

    @Test
    public void canDetectNonBeanMethods() {
        //given
        final String beanMethod = "java.lang.String getValue()";
        nonPrivateMethods.add(beanMethod);
        final String nonBeanMethod = "java.lang.String nonBean()";
        nonPrivateMethods.add(nonBeanMethod);
        fieldsAccessByMethod.put(beanMethod, Sets.newHashSet("value"));
        fieldsAccessByMethod.put(nonBeanMethod, Sets.newHashSet("other"));
        //when
        analyser.analyse(fieldsAccessByMethod, methodsInvokedByMethod, nonPrivateMethods, resultConsumer);
        //then
        assertThat(result.getNonBeanMethods()).containsExactly(nonBeanMethod);
    }

}
