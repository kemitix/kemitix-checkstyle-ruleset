package net.kemitix.checkstyle.ruleset.builder;

import net.kemitix.files.FileReaderWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ReadmeWriter}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ReadmeWriterTest {

    @Mock
    private ReadmeBuilder readmeBuilder;

    private TemplateProperties templateProperties = new TemplateProperties();
    private OutputProperties outputProperties = new OutputProperties();

    @Mock
    private FileReaderWriter fileReaderWriter;

    @InjectMocks
    private ReadmeWriter readmeWriter;

    @Mock
    private File templateFile;
    private String outputPath = UUID.randomUUID().toString();
    private String templateBody = UUID.randomUUID().toString();
    private String formattedOutput = UUID.randomUUID().toString();

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        readmeWriter =
                new ReadmeWriter(templateProperties, outputProperties,
                        readmeBuilder, fileReaderWriter);

        templateProperties.setReadmeTemplate(templateFile);
        outputProperties.setReadme(outputPath);
        given(fileReaderWriter.read(templateFile))
                .willReturn(templateBody);
        given(readmeBuilder.build(templateBody))
                .willReturn(formattedOutput);
    }

    @Test
    public void readAndWrite() throws Exception {
        //when
        readmeWriter.run();
        //then
        verify(fileReaderWriter)
                .write(Paths.get(outputPath).toFile(), formattedOutput);
    }

}
