package net.kemitix.checkstyle.ruleset.builder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

    private final TemplateProperties templateProps;
    private final OutputProperties outputProperties;
    private final ReadmeBuilder readmeBuilder;
    private final FileReaderWriter fileReaderWriter;

    @Override
    @SuppressFBWarnings("PATH_TRAVERSAL_IN")
    public void run(final String... args) throws Exception {
        final File templateFile = templateProps.getReadmeTemplate();
        final String templateBody = fileReaderWriter.read(templateFile);
        final File outputFile = Paths.get(outputProperties.getReadme()).toFile();
        final String outputBody = readmeBuilder.build(templateBody);
        fileReaderWriter.write(outputFile, outputBody);
    }

}
