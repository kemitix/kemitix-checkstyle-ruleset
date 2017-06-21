/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Paul Campbell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.kemitix.checkstyle.checks;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import lombok.RequiredArgsConstructor;
import net.kemitix.checkstyle.checks.cohesion.CohesionAnalyser;
import net.kemitix.checkstyle.checks.cohesion.CohesionAnalysisResult;
import net.kemitix.checkstyle.checks.cohesion.DefaultCohesionAnalyser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Checkstyle Check to ensure appropriate cohesion within a class of its methods and fields.
 *
 * <p>Specifically it measures the ratio of fields used by each public method.</p>
 *
 * <p>If this ratio falls below a threshold, then a violation will be logged.</p>
 *
 * <p>It also tried to detect when there is a partitioning of methods and fields indicating that they should probably be
 * split into a separate class.</p>
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class CohesionCheck extends AbstractCheck {

    private final CohesionAnalyser analyser = new DefaultCohesionAnalyser();

    private final Map<Integer, Consumer<DetailAST>> tokenVisitHandlers = new HashMap<>();

    private final Map<Integer, Consumer<DetailAST>> tokenLeaveHandlers = new HashMap<>();

    private final Map<String, Set<String>> usedByMethod = new HashMap<>();

    private final Set<String> nonPrivateMethods = new HashSet<>();

    private final Set<String> fieldNames = new HashSet<>();

    private final Set<String> methodNames = new HashSet<>();

    private Map<String, Set<String>> identsUsedByMethod = new HashMap<>();

    private String currentMethodName;

    private String currentMethodSignature;

    private List<String> currentMethodParameterTypes = new ArrayList<>();

    private boolean inExpression;

    private Set<String> currentMethodIdentsUsed = new HashSet<>();

    private Deque<Frame> frameStack = new ArrayDeque<>();

    private Frame classFrame;

    private Frame currentFrame;

    @Override
    public final int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.VARIABLE_DEF, TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF, TokenTypes.EXPR,
                TokenTypes.IDENT, TokenTypes.CLASS_DEF
        };
    }

    @Override
    public final void init() {
        tokenHandlers(TokenTypes.CLASS_DEF, this::visitClassDef, this::leaveClassDef);
        tokenHandlers(TokenTypes.VARIABLE_DEF, this::visitVariableDef, this::leaveVariableDef);
        tokenHandlers(TokenTypes.METHOD_DEF, this::visitMethodDef, this::leaveMethodDef);
        tokenHandlers(TokenTypes.PARAMETER_DEF, this::visitParameterDef, this::leaveParameterDef);
        tokenHandlers(TokenTypes.EXPR, this::visitExpression, this::leaveExpression);
        tokenHandlers(TokenTypes.IDENT, this::visitIdent, this::leaveIdent);
        usedByMethod.clear();
        identsUsedByMethod.clear();
        nonPrivateMethods.clear();
        fieldNames.clear();
        methodNames.clear();
        usedByMethod.clear();
    }

    @Override
    public final void visitToken(final DetailAST ast) {
        Optional.ofNullable(tokenVisitHandlers.get(ast.getType()))
                .ifPresent(handler -> handler.accept(ast));
    }

    @Override
    public final void leaveToken(final DetailAST ast) {
        Optional.ofNullable(tokenLeaveHandlers.get(ast.getType()))
                .ifPresent(handler -> handler.accept(ast));
    }

    private void tokenHandlers(final int token, final Consumer<DetailAST> onVisit, final Consumer<DetailAST> onLeave) {
        tokenVisitHandlers.put(token, onVisit);
        tokenLeaveHandlers.put(token, onLeave);
    }

    private void visitClassDef(final DetailAST ast) {
        System.out.println("Checking class: " + getIdent(ast));
        openClassFrame();
        final FileContents fileContents = getFileContents();
        final String fileName = fileContents.getFileName();
        // parse filename?
        System.out.println("fileName = " + fileName);
    }

    private void openClassFrame() {
        classFrame = new Frame(null);
        currentFrame = classFrame;
        frameStack.push(currentFrame);
    }

    private void leaveClassDef(final DetailAST ast) {
        //TODO: gather field names from current frame
        identsUsedByMethod.forEach((methodSignature, identsUsed) -> {
            final Set<String> fieldsUsed = Sets.intersection(fieldNames, identsUsed);
            final Set<String> methodsUsed = Sets.intersection(methodNames, identsUsed);
            final Set<String> itemsUsed = Sets.union(fieldsUsed, methodsUsed);
            usedByMethod.put(methodSignature, itemsUsed);
        });
        Display.usedByMethod(usedByMethod);
        Display.nonPrivateMethods(nonPrivateMethods);
        analyser.analyse(usedByMethod, nonPrivateMethods, this::resultConsumer);
    }

    private void visitVariableDef(final DetailAST ast) {
        final String variableName = getIdent(ast);
        fieldNames.add(variableName);
        currentFrame.addVariable(variableName, getType(ast));
    }

    private void leaveVariableDef(final DetailAST ast) {

    }

    private void visitMethodDef(final DetailAST ast) {
        openFrame();
        currentMethodName = getIdent(ast);
        methodNames.add(currentMethodName);
        if (!isPrivate(ast)) {
            nonPrivateMethods.add(currentMethodName);
        }
    }

    private void openFrame() {
        currentFrame = new Frame(currentFrame);
        frameStack.push(currentFrame);
    }

    private boolean isPrivate(final DetailAST ast) {
        return Optional.ofNullable(ast.findFirstToken(TokenTypes.MODIFIERS))
                       .map(t -> t.branchContains(TokenTypes.LITERAL_PRIVATE))
                       .orElse(false);
    }

    private void leaveMethodDef(final DetailAST ast) {
        System.out.println("CohesionCheck.leaveMethodDef");
        final String parameters = String.join(", ", currentMethodParameterTypes);
        System.out.println("currentMethodSignature = " + currentMethodSignature);
        currentMethodSignature = String.format("%s(%s)", currentMethodName, parameters);
        identsUsedByMethod.put(currentMethodSignature, new HashSet<>(currentMethodIdentsUsed));
        currentMethodIdentsUsed.clear();
        closeFrame();
    }

    private void closeFrame() {
        frameStack.pop();
        currentFrame = frameStack.getFirst();
    }

    private void visitParameterDef(final DetailAST ast) {
        currentMethodParameterTypes.add(getType(ast));
    }

    private void leaveParameterDef(final DetailAST ast) {
        currentFrame.addVariable(getIdent(ast), getType(ast));

    }

    private void visitExpression(final DetailAST ast) {
        //System.out.println("CohesionCheck.visitExpression: " + ast.toStringList());
        inExpression = true;
    }

    private void leaveExpression(final DetailAST ast) {
        //System.out.println("CohesionCheck.leaveExpression: " + ast.toStringTree());
        inExpression = false;
    }

    private void visitIdent(final DetailAST ast) {
        if (inExpression) {
            final String ident = ast.getText();
            if (isField(ident)) {
                System.out.println("adding item to current methods: " + ident);
                currentMethodIdentsUsed.add(ident);
            }
        }
    }

    private boolean isField(final String ident) {
        return classFrame.containsVariable(ident);
    }

    private void leaveIdent(final DetailAST ast) {

    }

    private String getIdent(final DetailAST ast) {
        return findIdent(ast).orElse("[unnamed]");
    }

    private Optional<String> findIdent(final DetailAST ast) {
        return Optional.ofNullable(ast.findFirstToken(TokenTypes.IDENT))
                       .map(DetailAST::getText);
    }

    private String getType(final DetailAST ast) {
        return Optional.ofNullable(ast.findFirstToken(TokenTypes.TYPE))
                       .map(DetailAST::getFirstChild)
                       .map(DetailAST::getText)
                       .orElse("[unknown]");
    }

    private void resultConsumer(final CohesionAnalysisResult result) {
        final int size = result.getComponents()
                               .size();
        if (size > 1) {
            log(1, "cohesion.partitioned", size, result.getPartitionedNonPrivateMethods());
        }
    }

    private static class Display {

        static void usedByMethod(final Map<String, Set<String>> usedByMethod) {
            System.out.println("Display.usedByMethod");
            usedByMethod.keySet()
                        .forEach(methodName -> {
                            System.out.println(methodName + ":");
                            usedByMethod.get(methodName)
                                        .stream()
                                        .map(item -> " - " + item)
                                        .forEach(System.out::println);
                        });
        }

        static void nonPrivateMethods(final Set<String> nonPrivateMethods) {
            System.out.println("Display.nonPrivateMethods");
            System.out.println("nonPrivateMethods.size() = " + nonPrivateMethods.size());
            nonPrivateMethods.forEach(System.out::println);
        }
    }

    @RequiredArgsConstructor
    private class Frame {

        private final Frame parent;

        private final Map<String, String> variables = new HashMap<>();

        void addVariable(final String name, final String type) {
            variables.put(name, type);
        }

        boolean containsVariable(final String name) {
            return variables.containsKey(name);
        }

        String getTypeFor(final String name) {
            System.out.println("Frame.getTypeFor");
            System.out.println("name = [" + name + "]");
            System.out.println("variables = " + variables);
            if (variables.containsKey(name)) {
                return variables.get(name);
            }
            return Optional.ofNullable(parent)
                           .map(p -> p.getTypeFor(name))
                           .orElse("[unknown]");
        }
    }
}
