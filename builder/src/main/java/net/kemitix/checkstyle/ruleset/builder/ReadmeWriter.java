package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import net.kemitix.files.FileReaderWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

/**
 * Writes the README file.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Component
@RequiredArgsConstructor
class ReadmeWriter implements CommandLineRunner {

    private final TemplateProperties templateProperties;
    private final OutputProperties outputProperties;
    private final ReadmeBuilder readmeBuilder;
    private final FileReaderWriter fileReaderWriter;

    @Override
    public void run(final String... args) throws Exception {
        File templateFile = templateProperties.getReadmeTemplate();
        String templateBody = fileReaderWriter.read(templateFile);
        File outputFile = Paths.get(outputProperties.getReadme()).toFile();
        String outputBody = readmeBuilder.build(templateBody);
        fileReaderWriter.write(outputFile, outputBody);
    }

}
