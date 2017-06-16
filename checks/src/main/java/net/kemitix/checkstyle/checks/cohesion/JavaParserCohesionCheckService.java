package net.kemitix.checkstyle.checks.cohesion;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class JavaParserCohesionCheckService implements CohesionCheckService {

    private final CohesionAnalyser analyser = new DefaultCohesionAnalyser();

    @Override
    public void check(
            final File file, final Consumer<CohesionAnalysisResult> resultConsumer
                     ) {
        try {
            final Map<String, Set<String>> membersUsedByMethods = new HashMap<>();
            final CompilationUnit parsed = JavaParser.parse(file);
            final Map<String, String> imports = parsed.getChildNodesByType(ImportDeclaration.class)
                                                      .stream()
                                                      .map(importDeclaration -> {
                                                          final String longName = importDeclaration.getNameAsString();
                                                          final int lastIndexOf = longName.lastIndexOf(".");
                                                          final String shortName = longName.substring(lastIndexOf + 1);
                                                          return new AbstractMap.SimpleEntry<>(shortName, longName);
                                                      })
                                                      .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                                                                                AbstractMap.SimpleEntry::getValue
                                                                               ));
            parsed.getChildNodesByType(MethodDeclaration.class)
                  .forEach(method -> {
                      final String methodName = getSignature(method, imports);
                      method.getChildNodesByType(NameExpr.class)
                            //TODO: method parameter types should not be included
                            .forEach(field -> {
                                final String fieldName = field.getNameAsString();
                                usedByMethod(membersUsedByMethods, methodName, fieldName);
                            });
                      method.getChildNodesByType(MethodCallExpr.class)
                            .stream()
                            .filter(call -> !call.getScope()
                                                 .isPresent())
                            .forEach(call -> {
                                final String calledMethod = getSignature(call);
                                usedByMethod(membersUsedByMethods, calledMethod, calledMethod);
                            });
                  });
            final Set<String> nonPrivateMethods = parsed.getChildNodesByType(MethodDeclaration.class)
                                                        .stream()
                                                        .filter(isNonPrivateMethod())
                                                        .map(MethodDeclaration::getSignature)
                                                        .map(CallableDeclaration.Signature::asString)
                                                        .collect(Collectors.toSet());
            analyser.analyse(membersUsedByMethods, nonPrivateMethods, resultConsumer);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSignature(
            final MethodDeclaration method, final Map<String, String> imports
                               ) {
        final String returnType = getReturnType(method, imports);
        final String signature = String.format("%s %s", returnType, method.getSignature()
                                                                          .asString());
        System.out.println("declaration signature = " + signature);
        assert !signature.startsWith("String");
        return signature;
    }

    private String getReturnType(final MethodDeclaration method, final Map<String, String> imports) {
        //TODO: handle compatible types. e.g. int <=> Integer
        return imports.computeIfAbsent(method.getType()
                                             .asString(), key -> "java.lang." + key);
    }

    private String getSignature(final MethodCallExpr call) {
        System.out.println("JavaParserCohesionCheckService.getSignature");
        final String returnType = getReturnType(call);
        System.out.println("call returnType = " + returnType);
        final String signature = String.format("%s %s", returnType, call.toString());
        System.out.println("call signature = " + signature);
        return signature;
    }

    private String getReturnType(final MethodCallExpr call) {
        System.out.println("call.getParentNode() = " + call.getParentNode());
        return "[unknown]";

    }

    private Predicate<MethodDeclaration> isNonPrivateMethod() {
        return methodDeclaration -> !methodDeclaration.isPrivate();
    }

    private void usedByMethod(
            final Map<String, Set<String>> membersUsedByMethods, final String methodName, final String fieldName
                             ) {
        Optional.ofNullable(membersUsedByMethods.get(methodName))
                .orElseGet(() -> {
                    final Set<String> set = new HashSet<>();
                    membersUsedByMethods.put(methodName, set);
                    return set;
                })
                .add(fieldName);
    }

}
