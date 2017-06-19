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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.kemitix.checkstyle.checks.cohesion.CohesionAnalyser;
import net.kemitix.checkstyle.checks.cohesion.CohesionAnalysisResult;
import net.kemitix.checkstyle.checks.cohesion.DefaultCohesionAnalyser;

import java.util.ArrayList;
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

    private Map<String, Set<String>> identsUsedByMethod = new HashMap<>();

    private final Set<String> nonPrivateMethods = new HashSet<>();

    private final Set<String> fieldNames = new HashSet<>();

    private final Set<String> methodNames = new HashSet<>();

    private String currentMethodReturnType;

    private String currentMethodName;

    private String currentMethodSignature;

    private List<String> currentMethodParameterTypes = new ArrayList<>();

    private boolean inExpression;

    private Set<String> currentMethodIdentsUsed = new HashSet<>();

    @Override
    public final int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.VARIABLE_DEF, TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF, TokenTypes.EXPR,
                TokenTypes.IDENT, TokenTypes.CLASS_DEF
        };
    }

    @Override
    public final void init() {
        tokenVisitHandlers.put(TokenTypes.CLASS_DEF, this::visitClassDef);
        tokenVisitHandlers.put(TokenTypes.VARIABLE_DEF, this::visitVariableDef);
        tokenVisitHandlers.put(TokenTypes.METHOD_DEF, this::visitMethodDef);
        tokenVisitHandlers.put(TokenTypes.PARAMETER_DEF, this::visitParameterDef);
        tokenVisitHandlers.put(TokenTypes.EXPR, this::visitExpression);
        tokenVisitHandlers.put(TokenTypes.IDENT, this::visitIdent);
        tokenLeaveHandlers.put(TokenTypes.CLASS_DEF, this::leaveClass);
        tokenLeaveHandlers.put(TokenTypes.METHOD_DEF, this::leaveMethodDef);
        tokenLeaveHandlers.put(TokenTypes.EXPR, this::leaveExpression);
        usedByMethod.clear();
        identsUsedByMethod.clear();
        nonPrivateMethods.clear();
        fieldNames.clear();
        methodNames.clear();
        usedByMethod.clear();
    }

    private void visitClassDef(final DetailAST ast) {
        System.out.println("Checking class: " + getIdent(ast));
    }

    private void visitIdent(final DetailAST ast) {
        if (inExpression) {
            System.out.println("adding item to current methods: " + ast.getText());
            currentMethodIdentsUsed.add(ast.getText());
        }
    }

    private void leaveExpression(final DetailAST ast) {
        //System.out.println("CohesionCheck.leaveExpression: " + ast.toStringTree());
        inExpression = false;
    }

    private void visitExpression(final DetailAST ast) {
        //System.out.println("CohesionCheck.visitExpression: " + ast.toStringList());
        inExpression = true;
    }

    private void leaveMethodDef(final DetailAST ast) {
        System.out.println("CohesionCheck.leaveMethodDef");
        final String parameters = String.join(", ", currentMethodParameterTypes);
        currentMethodSignature = String.format("%s %s(%s)", currentMethodReturnType, currentMethodName, parameters);
        System.out.println("currentMethodSignature = " + currentMethodSignature);
        identsUsedByMethod.put(currentMethodSignature, new HashSet<>(currentMethodIdentsUsed));
        currentMethodIdentsUsed.clear();
    }

    private void visitParameterDef(final DetailAST ast) {
        currentMethodParameterTypes.add(getType(ast));
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

    private void visitVariableDef(final DetailAST ast) {
        final String variableName = getIdent(ast);
        fieldNames.add(variableName);
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

    private void leaveClass(final DetailAST ast) {
        System.out.println("identsUsedByMethod = " + identsUsedByMethod);
        System.out.println("fieldNames = " + fieldNames);
        System.out.println("methodNames = " + methodNames);
        identsUsedByMethod.entrySet()
                          .stream()
                          .forEach(e -> {
                              final Set<String> identsUsed = e.getValue();
                              final Set<String> fieldsUsed = Sets.intersection(fieldNames, identsUsed);
                              System.out.println("fieldsUsed = " + fieldsUsed);
                              final Set<String> methodsUsed = Sets.intersection(methodNames, identsUsed);
                              System.out.println("methodsUsed = " + methodsUsed);
                              final Set<String> itemsUsed = Sets.union(fieldsUsed, methodsUsed);
                              System.out.println("itemsUsed = " + itemsUsed);
                              final String methodSignature = e.getKey();
                              usedByMethod.put(methodSignature, itemsUsed);
                          });
        System.out.println("usedByMethod = " + usedByMethod);
        analyser.analyse(usedByMethod, nonPrivateMethods, this::resultConsumer);
    }

    private void visitMethodDef(final DetailAST ast) {
        System.out.println("CohesionCheck.visitMethodDef");
        currentMethodReturnType = getType(ast);
        currentMethodName = getIdent(ast);
        System.out.println(String.format("signature: %s %s(.?.)", currentMethodReturnType, currentMethodName));
        methodNames.add(currentMethodName);
    }

    private void resultConsumer(final CohesionAnalysisResult result) {
        final int size = result.getComponents()
                               .size();
        if (size > 1) {
            log(1, "cohesion.partitioned", size, result.getPartitionedNonPrivateMethods());
        }
    }
}
