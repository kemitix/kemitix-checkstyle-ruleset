package net.kemitix.checkstyle.ruleset.builder;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FileWriterReaderTest {


    private String line1 = UUID.randomUUID().toString();
    private String line2 = UUID.randomUUID().toString();
    private String body = line1 + "\n" + line2;

    @org.junit.Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private Path path;

    private FileWriter writer = new FileWriter();
    private FileReader reader = new FileReader();

    @Before
    public void setUp() throws IOException {
        path = folder.newFile().toPath();
    }

    @Test
    public void writeTheReadFile() throws IOException {
        //when
        writer.write(path, body);
        String read = reader.read(path);
        //then
        assertThat(read).isEqualTo(body);
    }
}