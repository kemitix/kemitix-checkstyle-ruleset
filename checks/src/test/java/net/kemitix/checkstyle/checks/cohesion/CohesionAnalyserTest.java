package net.kemitix.checkstyle.checks.cohesion;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
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
        fieldsAccessByMethod.put(beanMethod, Sets.newHashSet("value"));
        fieldsAccessByMethod.put(nonBeanMethod, Sets.newHashSet("other"));
        //when
        analyser.analyse(fieldsAccessByMethod, methodsInvokedByMethod, nonPrivateMethods, r -> analysisResult = r);
        //then
        assertThat(analysisResult.getNonBeanMethods()).containsExactly(nonBeanMethod);
    }

}
