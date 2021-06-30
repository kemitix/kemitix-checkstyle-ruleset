package net.kemitix.checkstyle.ruleset.builder;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link PackageScanner} using ClassGraph.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 * @see <a href="https://github.com/classgraph/classgraph/">ClassGraph</a>
 */
@Service
public class ClassGraphPackageScanner implements PackageScanner {

    private final transient ClassGraph classGraph = new ClassGraph();

    @Override
    public final List<String> apply(final RuleSource ruleSource) {
        final String basePackage = ruleSource.getBasePackage();
        try (ScanResult scanResult = scanPackage(classGraph, basePackage)) {
            return scanResult
                    .getAllStandardClasses()
                    .getNames()
                    .stream()
                    .filter(packageName -> packageName.startsWith(basePackage))
                    .collect(Collectors.toList());
        }
    }

    private static ScanResult scanPackage(final ClassGraph classGraph, final String basePackage) {
        return classGraph
                .whitelistPackages(basePackage)
                .scan();
    }

}
