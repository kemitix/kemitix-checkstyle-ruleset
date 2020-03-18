package net.kemitix.checkstyle.ruleset.builder;

import lombok.val;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
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
    private FileReader fileReader;

    @Mock
    private FileWriter fileWriter;

    @InjectMocks
    private ReadmeWriter readmeWriter;

    @Mock
    private Path templatePath;
    @Mock
    private Path outputPath;
    private String templateBody = UUID.randomUUID().toString();
    private String formattedOutput = UUID.randomUUID().toString();

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        readmeWriter =
                new ReadmeWriter(templateProperties, outputProperties,
                        readmeBuilder, fileReader, fileWriter);

        templateProperties.setReadmeTemplate(templatePath);
        outputProperties.setReadme(outputPath);
        given(fileReader.read(templatePath))
                .willReturn(templateBody);
        given(readmeBuilder.build(templateBody))
                .willReturn(formattedOutput);
    }

    @Test
    public void readAndWrite() throws Exception {
        //when
        readmeWriter.run();
        //then
        verify(fileWriter).write(outputPath, formattedOutput);
    }

}
