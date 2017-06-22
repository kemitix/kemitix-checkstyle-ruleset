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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.kemitix.checkstyle.checks.cohesion.ClassCohesionParser;
import net.kemitix.checkstyle.checks.cohesion.ClassCohesionParserImpl;
import net.kemitix.checkstyle.checks.cohesion.CohesionAnalyser;
import net.kemitix.checkstyle.checks.cohesion.CohesionAnalysisResult;
import net.kemitix.checkstyle.checks.cohesion.DefaultCohesionAnalyser;

import java.util.HashMap;
import java.util.HashSet;
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

    private final ClassCohesionParser parser = new ClassCohesionParserImpl();

    private Map<String, Set<String>> usedByMethod = new HashMap<>();

    private Set<String> nonPrivateMethods = new HashSet<>();

    private String currentPackage;

    @Override
    public final int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.PACKAGE_DEF, TokenTypes.CLASS_DEF
        };
    }

    @Override
    public final void init() {
        tokenVisitHandlers.put(TokenTypes.PACKAGE_DEF, this::visitPackageDef);
        tokenVisitHandlers.put(TokenTypes.CLASS_DEF, this::visitClassDef);
        usedByMethod.clear();
        nonPrivateMethods.clear();
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

    private void visitPackageDef(final DetailAST ast) {
        final DetailAST nameAST = ast.getLastChild()
                                     .getPreviousSibling();
        final FullIdent full = FullIdent.createFullIdent(nameAST);
        currentPackage = full.getText();
    }

    private void visitClassDef(final DetailAST ast) {
        final FileContents fileContents = getFileContents();
        final String fileName = fileContents.getFileName();
        final String className = currentPackage + "." + getIdent(ast);
        final ClassCohesionParser.Results parserResults = parser.parse(fileName, className);
        usedByMethod = parserResults.getUsedByMethod();
        nonPrivateMethods = parserResults.getNonPrivateMethods();
        analyser.analyse(usedByMethod, nonPrivateMethods, this::resultConsumer);
    }

    private String getIdent(final DetailAST ast) {
        return findIdent(ast).orElse("[unnamed]");
    }

    private Optional<String> findIdent(final DetailAST ast) {
        return Optional.ofNullable(ast.findFirstToken(TokenTypes.IDENT))
                       .map(DetailAST::getText);
    }

    private void resultConsumer(final CohesionAnalysisResult result) {
        final int size = result.getComponents()
                               .size();
        if (size > 1) {
            log(1, "cohesion.partitioned", size, result.getPartitionedNonPrivateMethods());
        }
    }

}
