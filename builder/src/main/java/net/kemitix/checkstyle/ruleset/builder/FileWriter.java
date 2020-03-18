package net.kemitix.checkstyle.ruleset.builder;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Component
public class FileWriter {

    /**
     * Writes the content to the file path, replacing any existing file.
     *
     * <p>File content will be written using UTF-8 encoding.</p>
     *
     * @param path    the file to write
     * @param content the content to write
     * @throws IOException if there is an error.
     */
    public void write(
            final Path path,
            final String content
    ) throws IOException {
        Files.write(path,
                content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}
