package net.kemitix.checkstyle.ruleset.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

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
    private final FileReader fileReader;
    private final FileWriter fileWriter;

    @Override
    public void run(final String... args) throws Exception {
        Path templatePath = templateProperties.getReadmeTemplate();
        String templateBody = fileReader.read(templatePath);
        Path outputPath = outputProperties.getReadme();
        String outputBody = readmeBuilder.build(templateBody);
        fileWriter.write(outputPath, outputBody);
    }

}
