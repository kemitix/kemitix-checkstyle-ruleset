package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Tests for {@link CheckstyleSourceLoadException}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class CheckstyleSourceLoadExceptionTest {

    @Test
    public void canRaiseException() {
        //given
        final String basePackage = "base package";
        final IOException cause = new IOException();
        //when
        final CheckstyleSourceLoadException exception = new CheckstyleSourceLoadException(basePackage, cause);
        //then
        assertThat(exception).hasCause(cause)
                             .hasMessageStartingWith("Error loading checkstyle rules from package: ")
                             .hasMessageEndingWith(basePackage);
    }
}
