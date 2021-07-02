package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ReadmeFragmentNotFoundException}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ReadmeFragmentNotFoundExceptionTest {

    @Test
    public void shouldCreateExceptionWithName() {
        //given
        val name = "rule name";
        val cause = new IOException();
        //when
        val exception = new ReadmeFragmentNotFoundException(name, cause);
        //then
        assertThat(exception.getMessage()).as("rule name is message")
                                          .isEqualTo(name);
        assertThat(exception.getCause()).as("cause is caught")
                                        .isSameAs(cause);
    }
}
