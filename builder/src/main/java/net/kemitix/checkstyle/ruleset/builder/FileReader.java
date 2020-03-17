package net.kemitix.checkstyle.ruleset.builder;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileReader {
    private static final String NEWLINE = "\n";

    /**
     * Reads the content from the file path.
     *
     * <p>File content will be read using UTF-8 encoding and line endings will
     * be replaced with a newline (\n).</p>
     *
     * @param path the file to read
     * @throws IOException if there is an error.
     */
    public String read(final Path path) throws IOException {
        return String.join(NEWLINE,
                Files.readAllLines(path, StandardCharsets.UTF_8));
    }
}
