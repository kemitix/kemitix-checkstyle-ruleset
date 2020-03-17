package net.kemitix.checkstyle.ruleset.builder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class RuleSourceMother {

    public static Supplier<RuleSource> checkstyle = () ->
            RuleSourceMother.create(
                    "checkstyle",
                    true,
                    "com.puppycrawl.tools.checkstyle");

    public static Supplier<RuleSource> sevntu = () ->
            RuleSourceMother.create(
                    "sevntu",
                    true,
                    "com.github.sevntu.checkstyle.checks");

    public static Supplier<List<RuleSource>> list = () ->
            Arrays.asList(
                    checkstyle.get(),
                    sevntu.get()
            );

    public static RuleSource create(String name, boolean enabled, String basePackage) {
        RuleSource ruleSource = new RuleSource();
        ruleSource.setName(name);
        ruleSource.setEnabled(enabled);
        ruleSource.setBasePackage(basePackage);
        return ruleSource;
    }
}
