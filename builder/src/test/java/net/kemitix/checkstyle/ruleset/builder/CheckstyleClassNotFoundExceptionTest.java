package net.kemitix.checkstyle.ruleset.builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CheckstyleClassNotFoundException}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class CheckstyleClassNotFoundExceptionTest {

    @Test
    public void canRaiseException() {
        //given
        final String classname = "class name";
        //when
        final CheckstyleClassNotFoundException exception = new CheckstyleClassNotFoundException(classname);
        //then
        assertThat(exception.getMessage()).startsWith("No class found to implement ")
                                          .endsWith(classname);
    }
}
