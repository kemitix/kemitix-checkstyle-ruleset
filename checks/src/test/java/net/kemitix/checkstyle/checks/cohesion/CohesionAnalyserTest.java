package net.kemitix.checkstyle.checks.cohesion;

import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultCohesionAnalyser}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CohesionAnalyserTest {

    private CohesionAnalyser analyser;

    private Map<String, Set<String>> fieldsAccessByMethod;

    private Map<String, Set<String>> methodsInvokedByMethod;

    private Set<String> nonPrivateMethods;

    private CohesionAnalysisResult analysisResult;

    @Before
    public void setUp() {
        fieldsAccessByMethod = new HashMap<>();
        methodsInvokedByMethod = new HashMap<>();
        nonPrivateMethods = new HashSet<>();
        analyser = new DefaultCohesionAnalyser();
    }

    @Test
    public void canDetectNonBeanMethods() {
        //given
        final String beanMethod = "java.lang.String getValue()";
        nonPrivateMethods.add(beanMethod);
        final String nonBeanMethod = "java.lang.String nonBean()";
        nonPrivateMethods.add(nonBeanMethod);
        final String booleanBeanMethod = "java.lang.Boolean isEnabled()";
        nonPrivateMethods.add(booleanBeanMethod);
        final String primitiveBooleanBeanMethod = "boolean isValid()";
        nonPrivateMethods.add(primitiveBooleanBeanMethod);
        fieldsAccessByMethod.put(beanMethod, setOf("value"));
        fieldsAccessByMethod.put(nonBeanMethod, setOf("other"));
        fieldsAccessByMethod.put(booleanBeanMethod, setOf("enabled"));
        fieldsAccessByMethod.put(primitiveBooleanBeanMethod, setOf("valid"));
        //when
        performAnalysis();
        //then
        assertThat(analysisResult.getNonBeanMethods()).containsExactly(nonBeanMethod);
    }

    private HashSet<String> setOf(final String... values) {
        return Sets.newHashSet(Arrays.asList(values));
    }

    private void performAnalysis() {
        analyser.analyse(fieldsAccessByMethod, methodsInvokedByMethod, nonPrivateMethods, r -> analysisResult = r);
    }

}
