package net.kemitix.checkstyle.checks.cohesion;

import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link DefaultCohesionAnalyser}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CohesionAnalyserTest {

    private CohesionAnalyser analyser;

    private Map<String, Set<String>> usedByMethod;

    private Set<String> nonPrivateMethods;

    private CohesionAnalysisResult analysisResult;

    @Before
    public void setUp() {
        usedByMethod = new HashMap<>();
        nonPrivateMethods = new HashSet<>();
        analyser = new DefaultCohesionAnalyser();
    }

    @Test
    public void canDetectNonBeanMethods() {
        //given
        final String beanGetMethod = "getValue()";
        nonPrivateMethods.add(beanGetMethod);
        final String beanSetMethod = "setValue(java.lang.String)";
        nonPrivateMethods.add(beanSetMethod);
        final String nonBeanMethod = "nonBean()";
        nonPrivateMethods.add(nonBeanMethod);
        final String booleanBeanMethod = "isEnabled()";
        nonPrivateMethods.add(booleanBeanMethod);
        final String primitiveBooleanBeanMethod = "isValid()";
        nonPrivateMethods.add(primitiveBooleanBeanMethod);
        usedByMethod.put(beanGetMethod, setOf("value"));
        usedByMethod.put(beanSetMethod, setOf("value"));
        usedByMethod.put(nonBeanMethod, setOf("other"));
        usedByMethod.put(booleanBeanMethod, setOf("enabled"));
        usedByMethod.put(primitiveBooleanBeanMethod, setOf("valid"));
        //when
        performAnalysis();
        //then
        assertThat(analysisResult.getNonBeanMethods()).containsExactly(nonBeanMethod);
    }

    private HashSet<String> setOf(final String... values) {
        return Sets.newHashSet(Arrays.asList(values));
    }

    private void performAnalysis() {
        analyser.analyse(usedByMethod, nonPrivateMethods, captureAnalysisResult());
    }

    private Consumer<CohesionAnalysisResult> captureAnalysisResult() {
        return r -> analysisResult = r;
    }

    @Test
    public void canDetectASingleComponentFromASingleMethodAndField() {
        //given
        final String method = "getValue()";
        nonPrivateMethods.add(method);
        final String fieldName = "fieldName";
        usedByMethod.put(method, setOf(fieldName));
        //when
        performAnalysis();
        //then
        final Set<Component> components = analysisResult.getComponents();
        assertThat(components).hasSize(1);
        final Component component = components.toArray(new Component[]{})[0];
        assertThat(component.getMembers()).containsExactlyInAnyOrder(method, fieldName);
    }

    @Test
    public void requiresNonNullUseByMethod() {
        //when
        final ThrowableAssert.ThrowingCallable action =
                () -> analyser.analyse(null, nonPrivateMethods, captureAnalysisResult());
        //then
        assertThatThrownBy(action).isInstanceOf(NullPointerException.class)
                                  .hasMessage("usedByMethod");
    }

    @Test
    public void requiresNonNullNonPrivateMethods() {
        //when
        final ThrowableAssert.ThrowingCallable action =
                () -> analyser.analyse(usedByMethod, null, captureAnalysisResult());
        //then
        assertThatThrownBy(action).isInstanceOf(NullPointerException.class)
                                  .hasMessage("nonPrivateMethods");
    }

    @Test
    public void requiresNonNullResultConsumer() {
        //when
        final ThrowableAssert.ThrowingCallable action = () -> analyser.analyse(usedByMethod, nonPrivateMethods, null);
        //then
        assertThatThrownBy(action).isInstanceOf(NullPointerException.class)
                                  .hasMessage("resultConsumer");
    }

    @Test
    public void acceptWhenANonPrivateMethodsIsNotInUsedByMethod() {
        //given
        final String method = "getValue()";
        nonPrivateMethods.add(method);
        //when
        performAnalysis();
        //then
        // no exception is thrown
    }
}
