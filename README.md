# kemitix-checkstyle-ruleset

Provides an extensive Checkstyle ruleset for use with Checkstyle, together with a fully configured maven-tile.

![GitHub release (latest by date)](
https://img.shields.io/github/v/release/kemitix/kemitix-checkstyle-ruleset?style=for-the-badge)
![GitHub Release Date](
https://img.shields.io/github/release-date/kemitix/kemitix-checkstyle-ruleset?style=for-the-badge)

[![Sonatype Nexus (Release)](https://img.shields.io/nexus/r/https/oss.sonatype.org/net.kemitix.checkstyle/ruleset.svg?style=for-the-badge)](https://repo1.maven.org/maven2/net/kemitix/checkstyle/)
[![Maven Central](https://img.shields.io/maven-central/v/net.kemitix.checkstyle/ruleset.svg?style=for-the-badge)](https://search.maven.org/search?q=g:net.kemitix.checkstyle)

The ruleset includes checks from both the core Checkstyle library and from the Sevntu-Checkstyle library.

* [Usage](#usage)
* [All Checks](#all-checks)
* [Enabled Checks](#enabled-checks)
    * [Checkstyle](#checkstyle)
    * [Sevntu](#sevntu)
* [Disabled Checks](#disabled-checks)
    * [Checkstyle](#checkstyle-1)
    * [Sevntu](#sevntu-1)

## Usage

The simplest way to use the ruleset is with the maven-tile:

```xml
<project>
    <properties>
        <tiles-maven-plugin.version>2.11</tiles-maven-plugin.version>
    </properties>
    <build>
        <plugins>
            <plugin>
                <groupId>io.repaint.maven</groupId>
                <artifactId>tiles-maven-plugin</artifactId>
                 <version>${tiles-maven-plugin.version}</version>
                <extensions>true</extensions>
                <configuration>
                    <tiles>
                        <tile>net.kemitix.checkstyle:tile:${version}</tile>
                    </tiles>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

The following levels implement increasingly strict rulesets:

* 0-disabled
* 1-layout
* 2-naming
* 3-javadoc
* 4-tweaks
* 5-complexity

The default ruleset from the maven-tile is 5-complexity. Other levels can be selected by setting the `kemitix.checkstyle.ruleset.level` to one the values above.

### Change from 4.x

The `RedundantModifier` rule has been replaced by the `InterfaceMemberImpliedModifier`. This is to ensure that intefaces
in Java 9+ are easier to comprehend with the addition of private methods. The rules governing the implied modifiers for
members of interfaces differs from those of classes. So, to remove the need to remember the different rules, they are 
now required to be explicitly stated.

### Change from 3.x

Rename the artifact `net.kemitix:kemitix-checkstyle-ruleset` as `net.kemitix.checkstyle:ruleset`.

Introduction of the artifact `net.kemitix.checkstyle:tile` for use with the [tiles-maven-plugin](https://github.com/repaint-io/maven-tiles).

### Change from 2.x

In 2.x, the level was specified as the goal to invoke. In 3.x, there is only the 'check' goal. The level is now specified as a configuration parameter. See the example below. The kemitix-checkstyle-maven-plugin has also been removed in favour of the maven-tile.

## All Checks

Rule|Level|Source|Enabled|Suppressible
----|-----|------|-------|------------
[AbbreviationAsWordInName](#abbreviationaswordinname)|naming|checkstyle|Yes|
[AbstractClassName](#abstractclassname)|naming|checkstyle||
[AnnotationLocation](#annotationlocation)|layout|checkstyle|Yes|
[AnnotationUseStyle](#annotationusestyle)|layout|checkstyle|Yes|
[AnonInnerLength](#anoninnerlength)|complexity|checkstyle|Yes|
[ArrayTrailingComma](#arraytrailingcomma)|tweaks|checkstyle||
[ArrayTypeStyle](#arraytypestyle)|layout|checkstyle|Yes|
[AtclauseOrder](#atclauseorder)|javadoc|checkstyle|Yes|
[AvoidConditionInversion](#avoidconditioninversion)|complexity|sevntu||
[AvoidConstantAsFirstOperandInCondition](#avoidconstantasfirstoperandincondition)|tweaks|sevntu|Yes|
[AvoidDefaultSerializableInInnerClasses](#avoiddefaultserializableininnerclasses)|tweaks|sevntu|Yes|
[AvoidEscapedUnicodeCharacters](#avoidescapedunicodecharacters)|tweaks|checkstyle|Yes|
[AvoidHidingCauseException](#avoidhidingcauseexception)|tweaks|sevntu|Yes|
[AvoidInlineConditionals](#avoidinlineconditionals)|complexity|checkstyle|Yes|
[AvoidModifiersForTypes](#avoidmodifiersfortypes)|unspecified|sevntu||
[AvoidNestedBlocks](#avoidnestedblocks)|complexity|checkstyle|Yes|
[AvoidNotShortCircuitOperatorsForBoolean](#avoidnotshortcircuitoperatorsforboolean)|tweaks|sevntu|Yes|
[AvoidStarImport](#avoidstarimport)|layout|checkstyle||
[AvoidStaticImport](#avoidstaticimport)|complexity|checkstyle||
[BooleanExpressionComplexity](#booleanexpressioncomplexity)|complexity|checkstyle|Yes|
[CatchParameterName](#catchparametername)|naming|checkstyle|Yes|
[CauseParameterInException](#causeparameterinexception)|tweaks|sevntu||
[ChildBlockLength](#childblocklength)|complexity|sevntu||
[ClassDataAbstractionCoupling](#classdataabstractioncoupling)|complexity|checkstyle|Yes|
[ClassFanOutComplexity](#classfanoutcomplexity)|complexity|checkstyle|Yes|
[ClassTypeParameterName](#classtypeparametername)|naming|checkstyle|Yes|
[CommentsIndentation](#commentsindentation)|layout|checkstyle|Yes|
[ConfusingCondition](#confusingcondition)|complexity|sevntu|Yes|
[ConstantName](#constantname)|naming|checkstyle|Yes|No
[ConstructorWithoutParams](#constructorwithoutparams)|complexity|sevntu|Yes|
[CovariantEquals](#covariantequals)|complexity|checkstyle|Yes|No
[CustomDeclarationOrder](#customdeclarationorder)|layout|sevntu||
[CyclomaticComplexity](#cyclomaticcomplexity)|complexity|checkstyle|Yes|
[DeclarationOrder](#declarationorder)|layout|checkstyle|Yes|
[DefaultComesLast](#defaultcomeslast)|tweaks|checkstyle|Yes|
[DesignForExtension](#designforextension)|complexity|checkstyle|Yes|
[DiamondOperatorForVariableDefinition](#diamondoperatorforvariabledefinition)|tweaks|sevntu|Yes|
[EitherLogOrThrow](#eitherlogorthrow)|tweaks|sevntu|Yes|
[EmptyBlock](#emptyblock)|tweaks|checkstyle|Yes|
[EmptyCatchBlock](#emptycatchblock)|tweaks|checkstyle|Yes|
[EmptyForInitializerPad](#emptyforinitializerpad)|layout|checkstyle|Yes|
[EmptyForIteratorPad](#emptyforiteratorpad)|layout|checkstyle|Yes|
[EmptyLineSeparator](#emptylineseparator)|layout|checkstyle||
[EmptyPublicCtorInClass](#emptypublicctorinclass)|tweaks|sevntu|Yes|
[EmptyStatement](#emptystatement)|layout|checkstyle|Yes|
[EnumValueName](#enumvaluename)|naming|sevntu|Yes|
[EqualsAvoidNull](#equalsavoidnull)|tweaks|checkstyle|Yes|
[EqualsHashCode](#equalshashcode)|complexity|checkstyle|Yes|No
[ExecutableStatementCount](#executablestatementcount)|complexity|checkstyle|Yes|
[ExplicitInitialization](#explicitinitialization)|tweaks|checkstyle|Yes|
[FallThrough](#fallthrough)|javadoc|checkstyle|Yes|
[FileLength](#filelength)|complexity|checkstyle|Yes|
[FileTabCharacter](#filetabcharacter)|layout|checkstyle|Yes|
[FinalClass](#finalclass)|complexity|checkstyle|Yes|
[FinalizeImplementation](#finalizeimplementation)|tweaks|sevntu|Yes|
[FinalLocalVariable](#finallocalvariable)|tweaks|checkstyle||
[FinalParameters](#finalparameters)|tweaks|checkstyle|Yes|
[ForbidAnnotation](#forbidannotation)|unspecified|sevntu||
[ForbidCCommentsInMethods](#forbidccommentsinmethods)|layout|sevntu|Yes|
[ForbidCertainImports](#forbidcertainimports)|unspecified|sevntu||
[ForbidInstantiation](#forbidinstantiation)|unspecified|sevntu||
[ForbidReturnInFinallyBlock](#forbidreturninfinallyblock)|complexity|sevntu|Yes|
[ForbidThrowAnonymousExceptions](#forbidthrowanonymousexceptions)|tweaks|sevntu||
[ForbidWildcardAsReturnType](#forbidwildcardasreturntype)|complexity|sevntu|Yes|
[GenericWhitespace](#genericwhitespace)|layout|checkstyle|Yes|
[Header](#header)|layout|checkstyle||
[HiddenField](#hiddenfield)|tweaks|checkstyle|Yes|
[HideUtilityClassConstructor](#hideutilityclassconstructor)|tweaks|checkstyle|Yes|
[IllegalCatch](#illegalcatch)|tweaks|checkstyle|Yes|
[IllegalImport](#illegalimport)|tweaks|checkstyle|Yes|
[IllegalInstantiation](#illegalinstantiation)|unspecified|checkstyle||
[IllegalThrows](#illegalthrows)|tweaks|checkstyle|Yes|
[IllegalToken](#illegaltoken)|tweaks|checkstyle|Yes|
[IllegalTokenText](#illegaltokentext)|unspecified|checkstyle||
[IllegalType](#illegaltype)|tweaks|checkstyle|Yes|
[ImportControl](#importcontrol)|unspecified|checkstyle||
[ImportOrder](#importorder)|layout|checkstyle||
[Indentation](#indentation)|layout|checkstyle||
[InnerAssignment](#innerassignment)|tweaks|checkstyle|Yes|
[InnerTypeLast](#innertypelast)|tweaks|checkstyle|Yes|
[InterfaceIsType](#interfaceistype)|complexity|checkstyle|Yes|
[InterfaceMemberImpliedModifier](#interfacememberimpliedmodifier)|tweaks|checkstyle||
[InterfaceTypeParameterName](#interfacetypeparametername)|naming|checkstyle|Yes|
[JavadocMethod](#javadocmethod)|javadoc|checkstyle||
[JavadocMissingLeadingAsterisk](#javadocmissingleadingasterisk)|layout|checkstyle|Yes|
[JavadocMissingWhitespaceAfterAsterisk](#javadocmissingwhitespaceafterasterisk)|layout|checkstyle|Yes|
[JavadocPackage](#javadocpackage)|javadoc|checkstyle|Yes|
[JavadocParagraph](#javadocparagraph)|javadoc|checkstyle|Yes|
[JavadocStyle](#javadocstyle)|javadoc|checkstyle|Yes|
[JavadocTagContinuationIndentation](#javadoctagcontinuationindentation)|layout|checkstyle||
[JavadocType](#javadoctype)|javadoc|checkstyle|Yes|
[JavadocVariable](#javadocvariable)|javadoc|checkstyle||
[JavaNCSS](#javancss)|complexity|checkstyle|Yes|
[LambdaBodyLength](#lambdabodylength)|complexity|checkstyle|Yes|
[LambdaParameterName](#lambdaparametername)|naming|checkstyle|Yes|
[LeftCurly](#leftcurly)|layout|checkstyle|Yes|
[LineLength](#linelength)|layout|checkstyle|Yes|
[LocalFinalVariableName](#localfinalvariablename)|naming|checkstyle|Yes|
[LocalVariableName](#localvariablename)|naming|checkstyle|Yes|
[LogicConditionNeedOptimization](#logicconditionneedoptimization)|tweaks|sevntu|Yes|
[MagicNumber](#magicnumber)|naming|checkstyle|Yes|
[MapIterationInForEachLoop](#mapiterationinforeachloop)|complexity|sevntu|Yes|
[MemberName](#membername)|naming|checkstyle|Yes|
[MethodCount](#methodcount)|complexity|checkstyle|Yes|
[MethodLength](#methodlength)|complexity|checkstyle|Yes|
[MethodName](#methodname)|naming|checkstyle|Yes|
[MethodParamPad](#methodparampad)|layout|checkstyle|Yes|
[MethodTypeParameterName](#methodtypeparametername)|naming|checkstyle|Yes|
[MissingCtor](#missingctor)|tweaks|checkstyle||
[MissingDeprecated](#missingdeprecated)|javadoc|checkstyle|Yes|
[MissingOverride](#missingoverride)|tweaks|checkstyle||
[MissingSwitchDefault](#missingswitchdefault)|tweaks|checkstyle|Yes|
[ModifiedControlVariable](#modifiedcontrolvariable)|tweaks|checkstyle|Yes|
[ModifierOrder](#modifierorder)|naming|checkstyle|Yes|
[MoveVariableInsideIfCheck](#movevariableinsideifcheck)|tweaks|sevntu|Yes|
[MultipleStringLiterals](#multiplestringliterals)|naming|checkstyle|Yes|
[MultipleVariableDeclarations](#multiplevariabledeclarations)|naming|checkstyle|Yes|
[MutableException](#mutableexception)|tweaks|checkstyle|Yes|
[NameConventionForJunit4TestClasses](#nameconventionforjunit4testclasses)|naming|sevntu|Yes|
[NeedBraces](#needbraces)|naming|checkstyle|Yes|
[NestedForDepth](#nestedfordepth)|complexity|checkstyle|Yes|
[NestedIfDepth](#nestedifdepth)|complexity|checkstyle|Yes|
[NestedSwitch](#nestedswitch)|complexity|sevntu|Yes|
[NestedTryDepth](#nestedtrydepth)|complexity|checkstyle|Yes|
[NewlineAtEndOfFile](#newlineatendoffile)|layout|checkstyle|Yes|
[NoClone](#noclone)|tweaks|checkstyle|Yes|No
[NoCodeInFile](#nocodeinfile)|layout|checkstyle|Yes|
[NoFinalizer](#nofinalizer)|tweaks|checkstyle|Yes|
[NoLineWrap](#nolinewrap)|layout|checkstyle|Yes|
[NoMainMethodInAbstractClass](#nomainmethodinabstractclass)|tweaks|sevntu|Yes|
[NonEmptyAtclauseDescription](#nonemptyatclausedescription)|javadoc|checkstyle|Yes|
[NoWhitespaceAfter](#nowhitespaceafter)|layout|checkstyle|Yes|
[NoWhitespaceBefore](#nowhitespacebefore)|layout|checkstyle|Yes|
[NoWhitespaceBeforeCaseDefaultColon](#nowhitespacebeforecasedefaultcolon)|layout|checkstyle|Yes|
[NPathComplexity](#npathcomplexity)|complexity|checkstyle|Yes|
[NumericLiteralNeedsUnderscore](#numericliteralneedsunderscore)|naming|sevntu|Yes|
[OneStatementPerLine](#onestatementperline)|layout|checkstyle|Yes|
[OneTopLevelClass](#onetoplevelclass)|tweaks|checkstyle|Yes|No
[OperatorWrap](#operatorwrap)|layout|checkstyle|Yes|
[OuterTypeFilename](#outertypefilename)|tweaks|checkstyle|Yes|No
[OuterTypeNumber](#outertypenumber)|tweaks|checkstyle||
[OverloadMethodsDeclarationOrder](#overloadmethodsdeclarationorder)|layout|checkstyle|Yes|
[OverridableMethodInConstructor](#overridablemethodinconstructor)|tweaks|sevntu|Yes|
[PackageAnnotation](#packageannotation)|tweaks|checkstyle|Yes|
[PackageDeclaration](#packagedeclaration)|javadoc|checkstyle|Yes|No
[PackageName](#packagename)|naming|checkstyle|Yes|
[ParameterAssignment](#parameterassignment)|tweaks|checkstyle||
[ParameterName](#parametername)|naming|checkstyle|Yes|
[ParameterNumber](#parameternumber)|complexity|checkstyle|Yes|
[ParenPad](#parenpad)|layout|checkstyle|Yes|
[PublicReferenceToPrivateType](#publicreferencetoprivatetype)|tweaks|sevntu|Yes|
[RedundantImport](#redundantimport)|layout|checkstyle||
[RedundantModifier](#redundantmodifier)|tweaks|checkstyle||
[RedundantReturn](#redundantreturn)|tweaks|sevntu|Yes|
[Regexp](#regexp)|unspecified|checkstyle||
[RegexpHeader](#regexpheader)|unspecified|checkstyle||
[RegexpMultiline](#regexpmultiline)|unspecified|checkstyle||
[RegexpOnFilename](#regexponfilename)|layout|checkstyle|Yes|
[RegexpOnFilename](#regexponfilename)|unspecified|checkstyle||
[RegexpSingleline](#regexpsingleline)|unspecified|checkstyle||
[RegexpSinglelineJava](#regexpsinglelinejava)|unspecified|checkstyle||
[RequiredParameterForAnnotation](#requiredparameterforannotation)|unspecified|sevntu||
[RequireThis](#requirethis)|tweaks|checkstyle|Yes|
[ReturnBooleanFromTernary](#returnbooleanfromternary)|tweaks|sevntu|Yes|
[ReturnCount](#returncount)|complexity|checkstyle|Yes|
[ReturnNullInsteadOfBoolean](#returnnullinsteadofboolean)|tweaks|sevntu|Yes|
[RightCurly](#rightcurly)|layout|checkstyle|Yes|
[SeparatorWrap](#separatorwrap)|layout|checkstyle|Yes|
[SimpleAccessorNameNotation](#simpleaccessornamenotation)|naming|sevntu||
[SimplifyBooleanExpression](#simplifybooleanexpression)|complexity|checkstyle|Yes|
[SimplifyBooleanReturn](#simplifybooleanreturn)|complexity|checkstyle|Yes|
[SingleBreakOrContinue](#singlebreakorcontinue)|tweaks|sevntu|Yes|
[SingleLineJavadoc](#singlelinejavadoc)|javadoc|checkstyle||
[SingleSpaceSeparator](#singlespaceseparator)|layout|checkstyle|Yes|
[StaticMethodCandidate](#staticmethodcandidate)|unspecified|sevntu||
[StaticVariableName](#staticvariablename)|naming|checkstyle|Yes|
[StringLiteralEquality](#stringliteralequality)|tweaks|checkstyle|Yes|
[SummaryJavadoc](#summaryjavadoc)|javadoc|checkstyle||
[SuperClone](#superclone)|tweaks|checkstyle||
[SuperFinalize](#superfinalize)|tweaks|checkstyle||
[SuppressWarnings](#suppresswarnings)|naming|checkstyle|Yes|
[SuppressWarningsFilter](#suppresswarningsfilter)|naming|checkstyle|Yes|
[SuppressWarningsHolder](#suppresswarningsholder)|naming|checkstyle|Yes|
[TernaryPerExpressionCount](#ternaryperexpressioncount)|tweaks|sevntu|Yes|
[ThrowsCount](#throwscount)|complexity|checkstyle|Yes|
[TodoComment](#todocomment)|javadoc|checkstyle|Yes|
[TrailingComment](#trailingcomment)|layout|checkstyle|Yes|
[Translation](#translation)|javadoc|checkstyle|Yes|
[TypecastParenPad](#typecastparenpad)|layout|checkstyle|Yes|
[TypeName](#typename)|naming|checkstyle|Yes|No
[UncommentedMain](#uncommentedmain)|javadoc|checkstyle|Yes|
[UniformEnumConstantName](#uniformenumconstantname)|naming|sevntu|Yes|
[UniqueProperties](#uniqueproperties)|javadoc|checkstyle|Yes|
[UnnecessaryParentheses](#unnecessaryparentheses)|layout|checkstyle|Yes|
[UnnecessarySemicolonAfterOuterTypeDeclaration](#unnecessarysemicolonafteroutertypedeclaration)|layout|checkstyle|Yes|
[UnusedImports](#unusedimports)|layout|checkstyle|Yes|
[UpperEll](#upperell)|layout|checkstyle|Yes|
[UselessSingleCatch](#uselesssinglecatch)|tweaks|sevntu|Yes|
[UselessSuperCtorCall](#uselesssuperctorcall)|tweaks|sevntu|Yes|
[VariableDeclarationUsageDistance](#variabledeclarationusagedistance)|tweaks|checkstyle|Yes|
[VisibilityModifier](#visibilitymodifier)|tweaks|checkstyle|Yes|No
[WhitespaceAfter](#whitespaceafter)|layout|checkstyle|Yes|
[WhitespaceAround](#whitespacearound)|layout|checkstyle|Yes|
[WhitespaceBeforeArrayInitializer](#whitespacebeforearrayinitializer)|layout|sevntu|Yes|
[WriteTag](#writetag)|unspecified|checkstyle||

## Enabled Checks

The following is a list of each of the checks and the expectations each has on your code.

### Checkstyle

Rules are listed in alphabetical order.

#### [AbbreviationAsWordInName](http://checkstyle.sourceforge.net/config_naming.html#AbbreviationAsWordInName)

Enforces proper `CamelCase` and avoids sequences of consecutive uppercase characters in identifiers. Does not apply to @Overridden methods.

Valid:
````
class DaoManager {}
````

Invalid:
````
class DAOManager {}
````
#### [AnnotationLocation](http://checkstyle.sourceforge.net/config_annotation.html#AnnotationLocation)

Annotations must be on a line by themselves unless annotating a method parameter or among class modifiers.

Valid:
````
@Component
@Qualifier("Red")
class RedStick implements Stick {

    public @NonNull String getLabel(@Value("${stick.length}") final int length) {
        // ...
    }
}
````

Invalid:
````
@Component @Qualifier("Red")
class RedStick implements Stick {}
````
#### [AnnotationUseStyle](http://checkstyle.sourceforge.net/config_annotation.html#AnnotationUseStyle)

Annotations should only use brackets and named attributes when they are needed. If only the default parameter is specified, then only the attribute value should be given. If there are no parameters, then no brackets should be given.

Valid:
````
@Entity
@Table("names")
@MyAnnotation(realm = "external")
````

Invalid:
````
@Entity()
@Table(value = "names")
````
#### [AnonInnerLength](http://checkstyle.sourceforge.net/config_sizes.html#AnonInnerLength)

Anonymous inner classes should be no more than 20 lines.
#### [ArrayTypeStyle](http://checkstyle.sourceforge.net/config_misc.html#ArrayTypeStyle)

Enforces Java style arrays.

Valid:
````
public static void main(String[] args) {}
````

Invalid:
````
public static void main(String args[]) {}
````
#### [AtclauseOrder](http://checkstyle.sourceforge.net/config_javadoc.html#AtclauseOrder)

Javadoc `@` clauses must be in the order:

````
/**
 *
 * @param ...
 * @author ...
 * @version ...
 * @serial ...
 * @return ...
 * @throws ...
 * @exception ...
 * @serialData ...
 * @serialField ...
 * @see ...
 * @since ...
 * @deprecated ...
 */
````
#### [AvoidEscapedUnicodeCharacters](http://checkstyle.sourceforge.net/config_misc.html#AvoidEscapedUnicodeCharacters)

Prevents use of obscure escape codes (e.g. `\u221e`). However, non-printable/control characters are still permitted.

Valid:
````
String unitAbbrev = "??s";
String byteOrdered = '\ufeff' = content;
````

Invalid:
````
String unitAbbrev = "\u03bcs";
````
#### [AvoidInlineConditionals](http://checkstyle.sourceforge.net/config_coding.html#AvoidInlineConditionals)

Prevents use of the `?:` operators.
#### [AvoidNestedBlocks](http://checkstyle.sourceforge.net/config_blocks.html#AvoidNestedBlocks)

Avoid unnecessary blocks.

Valid:
````
if (isDebug()) {
    // ...
}
````

Invalid:
````
// if (isDebug())
{
    // ...
}
````
#### [BooleanExpressionComplexity](http://checkstyle.sourceforge.net/config_metrics.html#BooleanExpressionComplexity)

Restrict the number of number of &&, ||, &, | and ^ in an expression to 2.

Valid:
````
if (a || (b && c)) {}
````

Invalid:
````
if (a > b || b > c || c == a || d > a) {}
````
#### [CatchParameterName](http://checkstyle.sourceforge.net/config_naming.html#CatchParameterName)

Checks that catch parameter names conform to the following characteristic:

* allows names beginning with two lowercase letters followed by at least one uppercase or lowercase letter
* allows e abbreviation (suitable for exceptions end errors)
* allows ex abbreviation (suitable for exceptions)
* allows t abbreviation (suitable for throwables)
* prohibits numbered abbreviations like e1 or t2
* prohibits one letter prefixes like pException
* prohibits two letter abbreviations like ie or ee
* prohibits any other characters than letters

Valid:
````
catch(Exception txD) {}
catch(Exception txf) {}
catch(Exception e) {}
catch(Error e) {}
catch(Exception ex) {}
catch(Throwable t) {}
````

Invalid:
````
catch(Exception e2) {}
catch(Exception pExceptions) {}
catch(Exception gh) {}
catch(Exception e_x) {}
````
#### [ClassDataAbstractionCoupling](http://checkstyle.sourceforge.net/config_metrics.html#ClassDataAbstractionCoupling)

Restricts to 7 the number of different classes instantiated within a class when that class is instantiated.

Valid:
````
class Valid {
    private final Item i1 = new Item();
    private final Item i2 = new Item();
    private final Item i3 = new Item();
    private final Item i4 = new Item();
    private final Item i5 = new Item();
    private final Item i6 = new Item();
    private final Item i7 = new Item();
    private final Item i8 = new Item();
}
````

Invalid:
````
class Invalid {
    private final ItemA i1 = new ItemA();
    private final ItemB i2 = new ItemB();
    private final ItemC i3 = new ItemC();
    private final ItemD i4 = new ItemD();
    private final ItemE i5 = new ItemE();
    private final ItemF i6 = new ItemF();
    private final ItemG i7 = new ItemG();
    private final ItemH i8 = new ItemH();
}
````
#### [ClassFanOutComplexity](http://checkstyle.sourceforge.net/config_metrics.html#ClassFanOutComplexity)

Restricts the number of other classes that a class can rely on to 20.

While `ClassDataAbstractionCoupling` limits the number of classes that are instantiated when the class is, this check counts all fields whether they are assigned a value or not.
#### [ClassTypeParameterName](http://checkstyle.sourceforge.net/config_naming.html#ClassTypeParameterName)

Restricts class generics parameters to be a single uppercase letter.

Valid:
````
class Deliverator <A> {}
````

Invalid:
````
class Invalidator <a> {}
class Invalidator <BB> {}
class Invalidator <C3> {}
````
#### [CommentsIndentation](http://checkstyle.sourceforge.net/config_misc.html#CommentsIndentation)

Requires the indentation of comments to match the surrounding code.

Valid:
````
/**
 * This is okay.
 */
int size = 20;

public foo() {
    super();
    // this is okay
}

public void foo11() {
    CheckUtils
        .getFirstNode(new DetailAST())
        .getFirstChild()
        .getNextSibling();
    // this is okay
}
````

Invalid:
````
    /**
     * This is NOT okay.
     */
int size = 20;

public foo() {
    super();
        // this is NOT okay
// this is NOT okay
}

public void foo11() {
    CheckUtils
        .getFirstNode(new DetailAST())
        .getFirstChild()
        .getNextSibling();
            // this is NOT okay
}
````
#### [ConstantName](http://checkstyle.sourceforge.net/config_naming.html#ConstantName)

> This check cannot be suppressed.

Requires constants (static, final fields) to be all uppercase. Numbers and numbers are permitted but not as the first character.

Valid:
````
private static final int JACK_CARD = 11;
````

Invalid:
````
private static final int ace_card = 1;
private static final int 12_CARD = 12;
````
#### [CovariantEquals](http://checkstyle.sourceforge.net/config_coding.html#CovariantEquals)

> This check cannot be suppressed.

Checks that classes which define a covariant equals() method also override method equals(Object).

Valid:
````
class Test {
    public boolean equals(Test i) {
        return false;
    }

    public boolean equals(Object i) {
       return false;
    }
}
````

Invalid:
````
class Test {
    public boolean equals(Test i) {
        return false;
    }
}
````
#### [CyclomaticComplexity](http://checkstyle.sourceforge.net/config_metrics.html#CyclomaticComplexity)

Restricts the cyclomatic complexity of a method to 5. The cyclomatic complexity is a measure of the number of decision points in a method.

A method with no branches has a complexity of 1. For each `if`, `while`, `do`, `for`, `?:`, `catch`, `switch`, `case`, `&&` and `||` the complexity goes up by 1.

Valid:
````
void isValid(int a, int b, int c) {
    // 1
    if (a > b) { // +1 = 2
        switch (c) { // +1 = 3
            case 1: // +1 = 4
                break;
            case 2: // +1 = 5
                break;
        }
    }
}
````

Invalid:
````
void isInvalid(int a, int b, int c) {
    // 1
    if (a > b) { // +1 = 2
        switch (c) { // +1 = 3
            case 1: // +1 = 4
                break;
            case 2: // +1 = 5
                break;
            case 3: // +1 = 6
                break;
        }
    }
}
````
#### [DeclarationOrder](http://checkstyle.sourceforge.net/config_coding.html#DeclarationOrder)

Ensure class elements appear in the correct order.

Valid:
````
class Valid {
    // static
    public static int a;
    protected static int b;
    static int c;
    private static int d;

    // instance
    public int e;
    protected int f;
    int g;
    private int h;

    // constructors
    Valid() {}

    // methods
    void foo() {}
}
````

Invalid:
````
class Invalid {
    protected static int b;
    public static int a;
    private static int d;

    public int e;
    static int c;
    protected int f;
    private int h;

    void foo() {}

    Valid() {}

    int g;
}
````
#### [DefaultComesLast](http://checkstyle.sourceforge.net/config_coding.html#DefaultComesLast)

Check that the `default` is after all the `case`s in a `switch` statement.

Valid:
````
switch (a) {
    case 1:
        break;
    case 2:
        break;
    default:
        break;
}
````

Invalid:
````
switch (a) {
    case 1:
        break;
    default:
        break;
    case 2:
        break;
}
````
#### [DesignForExtension](http://checkstyle.sourceforge.net/config_design.html#DesignForExtension)

Judicous use of `@SuppressWarnings("designdorextension")` is recommended for this check.

This check is primarily intended for use in library modules rather than applications.

Classes that are deemed by their designer to be 'designed for extension', must take steps to prevent a subclass from breaking the class's behaviour by overriding methods incorrectly. This can be done through a combination of:

* Defining 'hook' methods with empty implementations that subclasses override to add their own behaviour
* Marking methods that are non-private and non-static as abstract or final

> See the official [Checkstyle documentation](http://checkstyle.sourceforge.net/config_design.html#DesignForExtension) for more details and [Effective Java], 2nd Edition by Josh Bloch: Item 17: Design and document for inheritance or else prohibit it.
#### [EmptyBlock](http://checkstyle.sourceforge.net/config_blocks.html#EmptyBlock)

Checks for empty blocks.

Valid:
````
if (a >b) {
	doSomething();
}
````

Invalid:
````
if (a > b) {
}
````
#### [EmptyCatchBlock](http://checkstyle.sourceforge.net/config_blocks.html#EmptyCatchBlock)

Checks that `catch` blocks are not empty, or are commented with the word `expected` or `ignore`.

Valid:
````
try {
    something();
} catch (Exception e) {
    // ignore
}
````

Invalid:
````
try {
    something();
} catch (Exception e) {
    // do nothing
}
````
#### [EmptyForInitializerPad](http://checkstyle.sourceforge.net/config_whitespace.html#EmptyForInitializerPad)

Checks that there is no padding in an empty `for` loop **initialiser**.

Valid:
````
for (; i < j ; i++) {}
````

Invalid:
````
for ( ; i < j ; i++) {}
````
#### [EmptyForIteratorPad](http://checkstyle.sourceforge.net/config_whitespace.html#EmptyForIteratorPad)

Checks that there is no padding in an empty `for` loop **iterator**.

Valid:
````
for (Iterator i = list.getIterator(); i.hasNext() ;) {}
````

Invalid:
````
for (Iterator i = list.getIterator(); i.hasNext() ; ) {}
````
#### [EmptyStatement](http://checkstyle.sourceforge.net/config_coding.html#EmptyStatement)

Checks for empty statements. An empty statement is a standalone semicolon (;).

Valid:
````
doSomething();
````

Invalid:
````
doSomething();;
````
#### [EqualsAvoidNull](http://checkstyle.sourceforge.net/config_coding.html#EqualsAvoidNull)

Checks that string literals are on the left side in an `equals()` comparison.

Valid:
````
String nullString = null;
"value".equals(nullString);
````

Invalid:
````
String nullString = null;
nullString.equals("value");
````
#### [EqualsHashCode](http://checkstyle.sourceforge.net/config_coding.html#EqualsHashCode)

> This check cannot be suppressed.

Checks that when a class overrides the `equals()` method, that it also overrides the `hashCode()` method.
#### [ExecutableStatementCount](http://checkstyle.sourceforge.net/config_sizes.html#ExecutableStatementCount)

Limits the number of executable statements in a method to 30.
#### [ExplicitInitialization](http://checkstyle.sourceforge.net/config_coding.html#ExplicitInitialization)

Checks that object fields are not being explicitly initialised to their already default value.

Does not check primitive field types.

Valid:
````
class Valid {

    private int foo = 0;

    private Object bar;
}
````

Invalid:
````
class Invalid {

    private Integer foo = 0;

    private Object bar = null;
}
````
#### [FallThrough](http://checkstyle.sourceforge.net/config_coding.html#FallThrough)

Checks that when a `case` in a `switch` statement falls through (i.e. doesn't end with `break;`) that the fall through is documented with a comment.

Valid:
````
switch (i) {
    case 0:
        i++; // fall through

    case 1:
        i++;
        // falls through

    case 2:
    case 3:
    case 4: { i++ } // fallthrough
    case 5:
        i++;
        /* fallthrou */
    case 6:
        i++;
        break;
}
````

Invalid:
````
switch (i) {
    case 0:
        i++;
    case 1:
        i++;
    case 2:
    case 3:
    case 4: { i++ }
    case 5:
        i++;
    case 6:
        i++;
        break;
}
````
#### [FileLength](http://checkstyle.sourceforge.net/config_sizes.html#FileLength)

Checks that each file has no more than 2000 lines.
#### [FileTabCharacter](http://checkstyle.sourceforge.net/config_whitespace.html#FileTabCharacter)

Checks that there are no tab characters in the source files.
#### [FinalClass](http://checkstyle.sourceforge.net/config_design.html#FinalClass)

Checks that classes which have only private constructors are also declared as `final`. These classes can't be extended by a subclass as they can't call `super()` from their constructors.

Valid:
````
final class Valid {

    private Valid() {}
}
````

Invalid:
````
class Invalid {

    private Invalid() {}
}
````
#### [FinalParameters](http://checkstyle.sourceforge.net/config_misc.html#FinalParameters)

Parameters to a method must be `final`.

Valid:
````
void foo(final int a) {}
````

Invalid:
````
void foo(int a) {}
````
#### [GenericWhitespace](http://checkstyle.sourceforge.net/config_whitespace.html#GenericWhitespace)

Checks that the angle brackets around Generics parameters have the correct whitespace padding:

Valid:
````
public void <K, V extends Number> boolean foo(K, V) {}
class name<T1, T2, ..., Tn> {}
OrderedPair<String, Box<Integer>> p;
boolean same = Util.<Integer, String>compare(p1, p2);
Pair<Integer, String> p1 = new Pair<>(1, "apple");
List<T> list = ImmutableList.Builder<T>::new;
sort(list, Comparable::<String>compareTo);
````
#### [HiddenField](http://checkstyle.sourceforge.net/config_coding.html#HiddenField)

Checks that a local variable or parameter in a method doesn't have the same name as a field. Doesn't apply in constructors or setters.

Valid:
````
class Foo {

    private int a;

    Foo(int a) {
        this.a = a;
    }

    setA(int a) {
        this.a = a;
    }
}
````

Invalid:
````
class Bar {

    private int b;

    void baz(int b) {
        // ...
    }
}
````
#### [HideUtilityClassConstructor](http://checkstyle.sourceforge.net/config_design.html#HideUtilityClassConstructor)

Classes that only have static fields or methods should not have a public constructor. This includes the default constructor.

Valid:
````
final class StringUtils {

    private Utils() {}

    private static int count(chat c, String s) {}
}

class StringUtils {

    protected Utils() {
        throw new UnsupportedOperationException();
    }

    private static int count(chat c, String s) {}
}
````

Invalid:
````
class StringUtils {

    private static int count(chat c, String s) {}
}
````
#### [IllegalCatch](http://checkstyle.sourceforge.net/config_coding.html#IllegalCatch)

Prevent the following types from being in a `catch` statement:

* java.lang.Exception
* java.lang.Throwable
* java.lang.RuntimeException

Valid:
````
try {
    doSomething();
} catch (SpecificException e) {
    // log
}
````

Invalid:
````
try {
    doSomething();
} catch (Exception e) {
    // log
}
````
#### [IllegalImport](http://checkstyle.sourceforge.net/config_imports.html#IllegalImport)

Prevent `import`ing from the `sun.*` packages.

Invalid:
````
import sun.security.provider.Sun;
````
#### [IllegalThrows](http://checkstyle.sourceforge.net/config_coding.html#IllegalThrows)

Prevent the following types from being `throw`n:

* java.lang.Exception
* java.lang.Throwable
* java.lang.RuntimeException

Valid:
````
throw new SpecificException("error");
````

Invalid:
````
throw new RuntimeException("boom!");
````
#### [IllegalToken](http://checkstyle.sourceforge.net/config_coding.html#IllegalToken)

Checks that labels are not used.
#### [IllegalType](http://checkstyle.sourceforge.net/config_coding.html#IllegalType)

Prevents use of implementation classes as variables, parameters or method returns. Use the interfaces instead.

Prevents variables, parameters and method returns from being any of the following:

* java.util.ArrayDeque
* java.util.ArrayList
* java.util.EnumMap
* java.util.EnumSet
* java.util.HashMap
* java.util.HashSet
* java.util.IdentityHashMap
* java.util.LinkedHashMap
* java.util.LinkedHashSet
* java.util.LinkedList
* java.util.PriorityQueue
* java.util.TreeMap
* java.util.TreeSet

Valid:
````
Set<String> getNames();
````

Invalid:
````
HashSet<String> getNames();
````
#### [InnerAssignment](http://checkstyle.sourceforge.net/config_coding.html#InnerAssignment)

Checks for assignments within an expressions. However, it still allows assignment in a while loop clause.

Valid:
````
while((line = reader.readLine()) != null) {
}
````

Invalid:
````
String s = Integer.toString(i = 2);
````
#### [InnerTypeLast](http://checkstyle.sourceforge.net/config_design.html#InnerTypeLast)

Inner classes must appear at the bottom of a class, below fields and methods.
#### [InterfaceIsType](http://checkstyle.sourceforge.net/config_design.html#InterfaceIsType)

An `interface` must define methods, not just constants.

Valid:
````
interface Foo {

    static final String "Foo!!";

    getBar();
}
````

Invalid:
````
interface Foo {

    static final String "Foo!!";
}
````
#### [InterfaceTypeParameterName](http://checkstyle.sourceforge.net/config_naming.html#InterfaceTypeParameterName)

Checks that the type parameters for an interface are a single uppercase letter.

Valid:
````
interface <T> Portable {}
````

Invalid:
````
interface <Type> Portable {}
````
#### [JavadocMissingLeadingAsterisk](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocMissingLeadingAsterisk)

Checks if the javadoc has leading asterisks on each line.

The check does not require asterisks on the first line, nor on the last line if
it is blank. All other lines in a Javadoc should start with *, including blank
lines and code blocks. 

Valid:
````
/**
 * Valid Java-style comment.
 *
 * <pre>
 *   int value = 0;
 * </pre>
 */
class JavaStyle {} // ok

/** Valid Scala-style comment.
  * Some description here.
  **/
class ScalaStyle {} // ok

/** **
  * Asterisks on first and last lines are optional.
  * */
class Asterisks {} // ok

/** No asterisks are required for single-line comments. */
class SingleLine {} // ok
````

Invalid:
````
/** // violation on next blank line, javadoc has lines without leading asterisk.

 */
class BlankLine {}

/** Wrapped
    single-line comment */ // violation, javadoc has lines without leading asterisk.
class Wrapped {}

/**
  * <pre>
    int value; // violation, javadoc has lines without leading asterisk.
  * </pre>
  */
class Code {}
````
#### [JavadocMissingWhitespaceAfterAsterisk](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocMissingWhitespaceAfterAsterisk)

Checks that there is at least one whitespace after the leading asterisk.
Although spaces after asterisks are optional in the Javadoc comments, their
absence makes the documentation difficult to read. It is the de facto standard
to put at least one whitespace after the leading asterisk.

Valid:
````java
/** This is valid single-line Javadoc. */
class TestClass {
  /**
   * This is valid Javadoc.
   */
  void validJavaDocMethod() {
  }
  /** This is valid single-line Javadoc. */
  void validSingleLineJavaDocMethod() {
  }
}
````

Invalid:
````java
class TestClass {
  /**
   *This is invalid Javadoc.
   */
  int invalidJavaDoc;
  /**This is invalid single-line Javadoc. */
  void invalidSingleLineJavaDocMethod() {
  }
}
````
#### [JavadocPackage](http://checkstyle.sourceforge.net/config_javadoc.html#JavadocPackage)

Checks that each package has a `package-info.java` file.
#### [JavadocParagraph](http://checkstyle.sourceforge.net/config_javadoc.html#JavadocParagraph)

Checks that paragraphs in Javadoc blocks are wrapped in `<p>` elements and have blank lines between paragraphs. This first paragraph does not need the `<p>` elements.
#### [JavadocStyle](http://checkstyle.sourceforge.net/config_javadoc.html#JavadocStyle)

Checks the formatting of the Javadoc blocks. See the official [Checkstyle documentation](http://checkstyle.sourceforge.net/config_javadoc.html#JavadocStyle) for all the checks that are applied.
#### [JavadocType](http://checkstyle.sourceforge.net/config_javadoc.html#JavadocType)

Checks the format for Javadoc for classes and enums. Javadoc must be present, not have any unknown tags and not missing any `@param` tags.
#### [JavaNCSS](http://checkstyle.sourceforge.net/config_metrics.html#JavaNCSS)

Restricts the NCSS score for methods, classes and files to 40, 1200 and 1600 respectively. The NCSS score is a measure of the number of statements within a scope.

Too high an NCSS score suggests that the method or class is doing too much and should be decomposed into smaller units.
#### [LambdaBodyLength](https://checkstyle.sourceforge.io/config_sizes.html#LambdaBodyLength)

Checks lambda body length.

Rationale: Similar to anonymous inner classes, if lambda body becomes very long
it is hard to understand and to see the flow of the method where the lambda is 
defined. Therefore, long lambda body should usually be extracted to method. 

Valid:
````
Runnable r3 = () -> { // ok, 10 lines
    System.out.println(2); // line 2 of lambda
    System.out.println(3);
    System.out.println(4);
    System.out.println(5);
    System.out.println(6);
    System.out.println(7);
    System.out.println(8);
    System.out.println(9);
}; // line 10
````

Invalid:
````
Runnable r = () -> { // violation, 11 lines
    System.out.println(2); // line 2 of lambda
    System.out.println(3);
    System.out.println(4);
    System.out.println(5);
    System.out.println(6);
    System.out.println(7);
    System.out.println(8);
    System.out.println(9);
    System.out.println(10);
}; // line 11

Runnable r2 = () -> // violation, 11 lines
  "someString".concat("1") // line 1 of lambda
              .concat("2")
              .concat("3")
              .concat("4")
              .concat("5")
              .concat("6")
              .concat("7")
              .concat("8")
              .concat("9")
              .concat("10")
              .concat("11"); // line 11
````
#### [LambdaParameterName](http://checkstyle.sourceforge.net/config_naming.html#LambdaParameterName)

Checks the format of lambda parameter names.

Identifiers must match `^[a-z][a-zA-Z0-9]*$`.
#### [LeftCurly](http://checkstyle.sourceforge.net/config_blocks.html#LeftCurly)

Checks that the left curly brace ('{') is placed at the end of the line. Does not check enums.

Valid:
````
class Foo {
}
````

Invalid:
````
class Bar
{

}
````
#### [LineLength](http://checkstyle.sourceforge.net/config_sizes.html#LineLength)

Limits the line length to 120 characters.

Doesn't check package or import lines.
#### [LocalFinalVariableName](http://checkstyle.sourceforge.net/config_naming.html#LocalFinalVariableName)

Checks the format of local, `final` variable names, including `catch` parameters.

Identifiers must match `^[a-z][a-zA-Z0-9]*$`.
#### [LocalVariableName](http://checkstyle.sourceforge.net/config_naming.html#LocalVariableName)

Checks the format of local, non-`final` variable names.

Identifiers must match `^[a-z][a-zA-Z0-9]*$`.
#### [MagicNumber](http://checkstyle.sourceforge.net/config_coding.html#MagicNumber)

Checks that numeric literals are defined as constants. Being constants they then have a name that aids in making them non-magical.

The numbers -1, 0, 1 and 2 are not considered to be magical.

Valid:
````
static final int SECONDS_PER_DAY = 24 * 60 * 60;
static final Border STANDARD_BORDER = BorderFactory.createEmptyBorder(3, 3, 3, 3);
````

Invalid
````
String item = getItem(200);
````
#### [MemberName](http://checkstyle.sourceforge.net/config_naming.html#MemberName)

Checks the format of non-static field names.

Identifiers must match `^[a-z][a-zA-Z0-9]*$`.
#### [MethodCount](http://checkstyle.sourceforge.net/config_sizes.html#MethodCount)

Restricts the number of methods in a type to 30.
#### [MethodLength](http://checkstyle.sourceforge.net/config_sizes.html#MethodLength)

Restricts the number of lines in a method to 60. Include blank lines and single line comments. You should be able to see an entire method without needing to scroll.
#### [MethodName](http://checkstyle.sourceforge.net/config_naming.html#MethodName)

Checks the format of method names.

Identifiers must match `^[a-z][a-zA-Z0-9]*$`.
#### [MethodParamPad](http://checkstyle.sourceforge.net/config_whitespace.html#MethodParamPad)

Checks that the padding between the method identifier and the left parenthesis is on the same line and doesn't have a space in-between.

Valid:
````
void getInstance();
````

Invalid:
````
void getInstance ();

void getValue
    ();
````
#### [MethodTypeParameterName](http://checkstyle.sourceforge.net/config_naming.html#MethodTypeParameterName)

Restricts method generics parameters to be a single uppercase letter.

Valid:
````
List<A> getItems() {}
````

Invalid:
````
List<a> getItems() {}
List<BB> getItems() {}
List<C3> getItems() {}
````
#### [MissingDeprecated](http://checkstyle.sourceforge.net/config_annotation.html#MissingDeprecated)

Both the `@Deprecated` annotation and the Javadoc tag `@deprecated` must be used in pairs.

Valid:
````
/**
  * Foo.
  *
  * @deprecated
  */
@Deprecated
void foo() {}
````

Invalid:
````
/**
  * Foo.
  */
@Deprecated
void foo() {}

/**
  * Bar.
  *
  * @deprecated
  */
void bar() {}
````
#### [MissingSwitchDefault](http://checkstyle.sourceforge.net/config_coding.html#MissingSwitchDefault)

Checks that `switch` statement has a `default` case.

Valid:
````
switch (foo) {
    case 1:
        //
        break;
    case 2:
        //
        break;
    default:
        throw new IllegalStateExcetion("Foo: " + foo);
}
````

Invalid:
````
switch (foo) {
    case 1:
        //
        break;
    case 2:
        //
        break;
}
````
#### [ModifiedControlVariable](http://checkstyle.sourceforge.net/config_coding.html#ModifiedControlVariable)

Checks that the control variable in a `for` loop is not modified inside the loop.

Invalid:
````
for (int i = 0; i < 1; i++) {
    i++;
}
````
#### [ModifierOrder](http://checkstyle.sourceforge.net/config_modifier.html#ModifierOrder)

Check that modifiers are in the following order:

* `public`
* `protected`
* `private`
* `abstract`
* `static`
* `final`
* `transient`
* `volatile`
* `synchronized`
* `native`
* `strictfp`

Type annotations are ignored.
#### [MultipleStringLiterals](http://checkstyle.sourceforge.net/config_coding.html#MultipleStringLiterals)

Checks for multiple occurrences of the same string literal within a single file. Does not apply to empty strings ("").

Invalid:
````
String fooFoo = "foo" + "foo";
````
#### [MultipleVariableDeclarations](http://checkstyle.sourceforge.net/config_coding.html#MultipleVariableDeclarations)

Checks that each variable is declared in its own statement and line.

Valid:
````
int a;
int b;
````

Invalid:
````
int a, b;
````
#### [MutableException](http://checkstyle.sourceforge.net/config_design.html#MutableException)

Checks that `Exception` classes are immutable. However, you can still call `setStackTrace`.

Classes checked are those whose name ends with the following. Or that the class they extend does.

* `Exception`
* `Error`
* `Throwable`
#### [NeedBraces](http://checkstyle.sourceforge.net/config_blocks.html#NeedBraces)

Check that code blocks are surrounded by braces.

Valid:
````
if (obj.isValid()) {
    return true;
}

while (obj.isValid()) {
    return true;
}

do {
    this.notify();
} while (o != null);

for (int i = 0; ;) {
    this.notify();
}
````

Invalid:
````
if (obj.isValid()) return true;

while (obj.isValid()) return true;

do this.notify(); while (o != null);

for (int i = 0; ;) this.notify();
````
#### [NestedForDepth](http://checkstyle.sourceforge.net/config_coding.html#NestedForDepth)

Checks that `for` loops are not nested more than 1 deep.

Valid:
````
for (int i = 0; i < 1; i++) { // depth 0
    for (int j = 0; j < 1; j++) { // depth 1
        //
    }
}
````

Invalid:
````
for (int i = 0; i < 1; i++) { // depth 0
    for (int j = 0; j < 1; j++) { // depth 1
        for (int k = 0; j < 1; k++) { // depth 2!
            //
        }
    }
}
````
#### [NestedIfDepth](http://checkstyle.sourceforge.net/config_coding.html#NestedIfDepth)

Checks that `if` blocks are not nested more than 1 deep.

Valid:
````
if (isValid()) { // depth 0
    if (isExpected()) { // depth 1
        doIt();
    }
}
````

Invalid:
````
if (isValid()) { // depth 0
    if (isExpected()) { // depth 1
        if (isNecessary()) { // depth 2!
            doIt();
        }
    }
}
````
#### [NestedTryDepth](http://checkstyle.sourceforge.net/config_coding.html#NestedTryDepth)

Checks that `try` blocks are not nested.

Valid:
````
try {
    doSomething();
    doSomeOtherThing();
} catch (SomeException se) {
    // handle it
} catch (OtherExceptions oe) {
    // handle it
}
````

Invalid:
````
try {
   doSomething();
   try {
       doSomeOtherThing();
   } catch (OtherExceptions oe) {
       // handle it
   }
} catch (SomeException se) {
   // handle it
}
````
#### [NewlineAtEndOfFile](http://checkstyle.sourceforge.net/config_misc.html#NewlineAtEndOfFile)

Checks that files end with a line-feed character, (i.e. unix-style line ending).
#### [NoClone](http://checkstyle.sourceforge.net/config_coding.html#NoClone)

> This check cannot be suppressed.

Checks that the `clone()` method from `Object` has not been overridden.  Use a copy constructor, or better yet, a static factory method.

> See [Effective Java], 2nd Edition by Josh Bloch: Item 11: Override clone judiciously.
#### [NoCodeInFile](https://checkstyle.sourceforge.io/config_misc.html#NoCodeInFile)

Checks whether file contains code. Files which are considered to have no code:

- File with no text
- File with only single line comment(s)
- File with only a multi line comment(s).

Invalid:
````java
// single line comment // violation
````

Invalid:
````java
/* // violation
 block comment
*/
````
#### [NoFinalizer](http://checkstyle.sourceforge.net/config_coding.html#NoFinalizer)

Checks that the `finalize()` method from `Object` has not been overridden.

> See [Effective Java], 2nd Edition by Josh Bloch: Item 7: Avoid finalizers.
#### [NoLineWrap](http://checkstyle.sourceforge.net/config_whitespace.html#NoLineWrap)

Prevents wrapping of `package` and `import` statements.
#### [NonEmptyAtclauseDescription](http://checkstyle.sourceforge.net/config_javadoc.html#NonEmptyAtclauseDescription)

Checks that the Javadoc clauses `@param`, `@return`, `@throws` and `@deprecated` all have descriptions.

Valid:
````
/**
 * Foo.
 *
 * @returns the foo
 */
````

Invalid:
````
/**
 * Foo.
 *
 * @returns
 */
````
#### [NoWhitespaceAfter](http://checkstyle.sourceforge.net/config_whitespace.html#NoWhitespaceAfter)

Checks that there is no whitespace after the array init ('{'), prefix increment ('++'), prefix decrement ('--'), bitwise complement ('~'), logical complement ('!'), array declaration ('[' in `int[] a;`) or array index operator ('[' in `a[2]`).

Valid:
````
int[] y = {1, 2};
++i;
--i;
int j = -1;
int k = +1;
int l = ~2;
boolean state = !isReady();
int b = o.getValue();
int[] a;
int d = a[2];
````

Invalid:
````
int[] y = { 1, 2 };
++ i;
-- i;
int j = - 1;
int k = + 1;
int l = ~ 2;
boolean state = ! isReady();
int b = o. getValue();
int[ ] a;
int d = a[ 2];
````
#### [NoWhitespaceBefore](http://checkstyle.sourceforge.net/config_whitespace.html#NoWhitespaceBefore)

Checks that there is no whitespace before the comma operator (','), statement terminator (';'), postfix increment ('++') or postfix decrement ('--').

Valid:
````
int y = {1, 2};
doSomething();
i++;
i--;
````

Invalid:
````
int y = {1 , 2};
doSomething() ;
i ++;
i --;
````
#### [NoWhitespaceBeforeCaseDefaultColon](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBeforeCaseDefaultColon)

Checks that there is no whitespace before the colon in a switch block. .

Valid:
````
switch(1) {
    case 1:
        break;
    case 2:
        break;
    default:
        break;
}
````

Invalid:
````
switch(2) {
    case 2: // ok
        break;
    case 3, 4
             : break; // violation, whitespace before ':' is not allowed here
    case 4,
          5: break; // ok
    default
          : // violation, whitespace before ':' is not allowed here
        break;
}
````
#### [NPathComplexity](http://checkstyle.sourceforge.net/config_metrics.html#NPathComplexity)

Checks that the NPATH score (number of paths) through a method is no more than 5. This is similar to [Cyclomatic Complexity](#cyclomaticcomplexity).
#### [OneStatementPerLine](http://checkstyle.sourceforge.net/config_coding.html#OneStatementPerLine)

Checks that there is only one statement per line.

Valid:
````
doSomething();
doSomethingElse();
````

Invalid:
````
doSomething(); doSomethingElse();
````
#### [OneTopLevelClass](http://checkstyle.sourceforge.net/config_design.html#OneTopLevelClass)

> This check cannot be suppressed.

Checks that each source file contains only one top-level class, interface or enum.
#### [OperatorWrap](http://checkstyle.sourceforge.net/config_whitespace.html#OperatorWrap)

Checks that when wrapping a line on an operator that the operator appears on the new line.

Valid:
````
int answer = getTheAnswerToLife() + getTheAnswerToTheUniverse()
    + getTheAnswerToEverything();
````

Invalid:
````
int answer = getTheAnswerToLife() + getTheAnswerToTheUniverse() +
    getTheAnswerToEverything();
````
#### [OuterTypeFilename](http://checkstyle.sourceforge.net/config_misc.html#OuterTypeFilename)

> This check cannot be suppressed.

Checks that the source filename matches the name of the top-level class.  e.g. `class Foo {}` is in file `Foo.java`.
#### [OverloadMethodsDeclarationOrder](http://checkstyle.sourceforge.net/config_coding.html#OverloadMethodsDeclarationOrder)

Checks that overload methods are grouped together in the source file.
#### [PackageAnnotation](http://checkstyle.sourceforge.net/config_annotation.html#PackageAnnotation)

Checks that package level annotations are in the `package-info.java` file.
#### [PackageDeclaration](http://checkstyle.sourceforge.net/config_coding.html#PackageDeclaration)

> This check cannot be suppressed.

Checks that the class has a `package` definition.
#### [PackageName](http://checkstyle.sourceforge.net/config_naming.html#PackageName)

Checks the format of package names. Only lowercase letters, no initial numbers or any underscores.

Identifiers must match `^[a-z]+(\.[a-z][a-z0-9]+)*$`.
#### [ParameterName](http://checkstyle.sourceforge.net/config_naming.html#ParameterName)

Checks the format of method parameter names, including `catch` parameters.

Identifiers must match `^[a-z][a-zA-Z0-9]*$`.
#### [ParameterNumber](http://checkstyle.sourceforge.net/config_sizes.html#ParameterNumber)

Restricts the number of parameters in a method or constructor to 7. Overridden methods are not checked as there may be no access to change the super method.
#### [ParenPad](http://checkstyle.sourceforge.net/config_whitespace.html#ParenPad)

Checks that there are no spaces padding parentheses.

Valid:
````
doSomething();
doSomethingElse(5);
````

Invalid:
````
doSomething( );
doSomethingElse( 5);
doSomethingElse(5 );
````
#### [RegexpOnFilename](http://checkstyle.sourceforge.net/config_regexp.html#RegexpOnFilename)

Checks for the existence of forbidden java file names.

File names are forbidden if they match the pattern `(.sync-conflict-| conflicted copy )`.

N.B. only `*.java` files are checked.

This check is intended to detect Syncthing and Dropbox conflict files.

e.g.
````
DataClass (Bob's conflicted copy 2017-03-11).java
DataClass.sync-conflict-20170311-1648.java
````

#### [RequireThis](http://checkstyle.sourceforge.net/config_coding.html#RequireThis)

Checks that references to instance fields where a parameter name overlaps are qualified by `this.`.
#### [ReturnCount](http://checkstyle.sourceforge.net/config_coding.html#ReturnCount)

Restricts methods to have at most 2 `return` statements in non-void methods, and at most 1 in void methods.

Valid:
````
int getNumber(int a) {
    if (a > 1) {
        return a;
    }
    return 0;
}

void getName(int a) {
    String name = "default";
    if (a > 1) {
        name = "Bob";
    }
    return name;
}
````

Invalid:
````
int getNumber(int a) {
    if (a > 1) {
        return a;
    }
    if (a < 2) {
        return a * a;
    }
    return 0;
}

void getName(int a) {
    if (a > 1) {
        return "Bob";
    }
    return "default";
}
````
#### [RightCurly](http://checkstyle.sourceforge.net/config_blocks.html#RightCurly)

Checks that the right curly brace ('}') is placed on the same line as the next part of a multi-block statement (e.g. try-catch-finally, if-then-else).

Valid:
````
try {
    //
} catch (Exception e) {
    //
} finally {
    //
}

if (a > 0) {
    //
} else {
    //
}
````

Invalid:
````
try {
    //
}
catch (Exception e) {
    //
}
finally {
    //
}

if (a > 0) {
    //
}
else {
    //
}

if (a > 0) {
    //
} a = 2;

public long getId() {return id;}
````
#### [SeparatorWrap](http://checkstyle.sourceforge.net/config_whitespace.html#SeparatorWrap)

Checks the line wrapping around separators.

* The comma separator (',') should be at the end of the line.
* The dot separator ('.') should be on the new line.

Valid:
````
doSomething(alpha, beta,
    gamma);
doSomethingElse().stream()
                 .forEach(System.out::println);
````

Invalid:
````
doSomething(alpha, beta
    , gamma);
doSomethingElse().stream().
                  forEach(System.out::println);
````
#### [SimplifyBooleanExpression](http://checkstyle.sourceforge.net/config_coding.html#SimplifyBooleanExpression)

Checks for overly complicated boolean expressions. Checks for code like `b == true`, `b || true`, `!false`, etc.

Valid:
````
if (b) {}
if (true) {}
````

Invalid:
````
if (b == true) {}
if (b || true) {}
if (!false) {}
````
#### [SimplifyBooleanReturn](http://checkstyle.sourceforge.net/config_coding.html#SimplifyBooleanReturn)

Checks for overly complicated boolean `return` statements.

Valid:
````
return !valid();
````

Invalid:
````
if (valid()) {
    return false;
} else {
    return true;
}
````
#### [SingleSpaceSeparator](http://checkstyle.sourceforge.net/config_whitespace.html#SingleSpaceSeparator)

Checks that non-whitespace characters on the same line are separated by no more than one whitespace.

Valid:
````
if (a < 0) {}
public long toNanos(long d) { return d; };
````

Invalid:
````
if  (a < 0) {}
public long toNanos(long d)  { return d;             };
````
#### [StaticVariableName](http://checkstyle.sourceforge.net/config_naming.html#StaticVariableName)

Checks the format of `static`, non-`final` variable names.

Identifiers must match `^[a-z][a-zA-Z0-9]*$`.
#### [StringLiteralEquality](http://checkstyle.sourceforge.net/config_coding.html#StringLiteralEquality)

Checks that string literals are not used with `==` or `!=`.

Valid:
````
if ("something".equals(x)) {}
````

Invalid:
````
if (x == "something") {}
````
#### [SuppressWarnings](http://checkstyle.sourceforge.net/config_annotation.html#SuppressWarnings)

Prevents the use of `@SuppressWarnings` for the following checks:

* [ConstantName](#constantname)
* [CovariantEquals](#covariantequals)
* [EqualsHashCode](#equalshashcode)
* [NoClone](#noclone)
* [OneTopLevelClass](#onetoplevelclass)
* [OuterTypeFilename](#outertypefilename)
* [PackageDeclaration](#packagedeclaration)
* [TypeName](#typename)
* [VisibilityModifier](#visibilitymodifier)
#### [SuppressWarningsFilter](http://checkstyle.sourceforge.net/config_annotation.html#SuppressWarningsFilter)

Allows the use of the `@SuppressWarnings` annotation.
#### [SuppressWarningsHolder](http://checkstyle.sourceforge.net/config_annotation.html#SuppressWarningsHolder)

Used by Checkstyle to hold the checks to be suppressed from `@SuppressWarnings(...)` annotations.
#### [ThrowsCount](http://checkstyle.sourceforge.net/config_design.html#ThrowsCount)

Restricts non-private methods to only `throws` 4 distinct Exception types. Exceptions should be hierarchical to allow catching suitable root Exceptions.

See [Effective Java], 2nd Edition, Chapter 9: Exceptions

Valid:
````
void doSomething() throws IllegalStateException, DowsingServiceException,
    BalancedBudgetException, ManagementInterferanceException {}
````

Invalid:
````
void doSomething() throws IllegalStateException,
    DowsingNotPermittedException, DowsingServiceNotReadyException,
    BalancedBudgetException, ManagementInterferanceException {}
````
#### [TodoComment](http://checkstyle.sourceforge.net/config_misc.html#TodoComment)

Checks for remaining `TODO` and `FIXME` comments left in code. Their presence indicates that the program isn't finished yet.
#### [TrailingComment](http://checkstyle.sourceforge.net/config_misc.html#TrailingComment)

Checks for comments at the end of lines.

Valid:
````
// comment on line by itself
    // comment after white space
if (a < 1) {
    //
} // comment on closing brace
int[] a = new int[2](
    1, 2
); // comment on closing parenthesis of statement
````

Invalid:
````
int a = 1; // comment in line with statement
if (a < 1) { // comment on line with if statement
    //
}
int[] a = new int[2](
    1, // first value - invalid comment
    2  // second value - also invalid comment
);
````
#### [Translation](http://checkstyle.sourceforge.net/config_misc.html#Translation)

Checks that all `messages*.properties` files all have the same set of keys.
#### [TypecastParenPad](http://checkstyle.sourceforge.net/config_whitespace.html#TypecastParenPad)

Checks that there are no spaces within the typecasting parentheses.

Valid:
````
String s = (String) list.get(2);
````

Invalid:
````
String s = (String ) list.get(2);
String s = ( String) list.get(2);
String s = ( String ) list.get(2);
````
#### [TypeName](http://checkstyle.sourceforge.net/config_naming.html#TypeName)

> This check cannot be suppressed.

Checks the format of `class`, `interface`, `enum` identifiers, including annotations.

Identifiers must match `^[A-Z][a-zA-Z0-9]*$`.
#### [UncommentedMain](http://checkstyle.sourceforge.net/config_misc.html#UncommentedMain)

Checks for `public static void main()` methods that may have been left over from testing. Allowed in classes whose names end in `Main` or `Application`.
#### [UniqueProperties](http://checkstyle.sourceforge.net/config_misc.html#UniqueProperties)

Checks `*.properties` files for duplicate property keys.
#### [UnnecessaryParentheses](http://checkstyle.sourceforge.net/config_coding.html#UnnecessaryParentheses)

Checks for the use of unnecessary parentheses.

Valid:
````
if (a < 1) {}
````

Invalid:
````
if ((a < 1)) {}
````
#### [UnnecessarySemicolonAfterOuterTypeDeclaration](https://checkstyle.sourceforge.io/config_coding.html#UnnecessarySemicolonAfterOuterTypeDeclaration)

Checks if unnecessary semicolon is used after type declaration.

Valid:
````java
class A {

   class Nested {

   }; // OK, nested type declarations are ignored

}

interface B {

}

enum C {

}

@interface D {

}
````

Invalid:
````java
class A {

   class Nested {

   }; // OK, nested type declarations are ignored

}; // violation

interface B {

}; // violation

enum C {

}; // violation

@interface D {

}; // violation
````
#### [UnusedImports](http://checkstyle.sourceforge.net/config_imports.html#UnusedImports)

Checks for unused imports. Does not inspect wildcard imports, which should be blocked by [AvoidStarImport](#avoidstarimport) anyway.

Imports are unused if:

* They are not referenced in the file.
* It duplicates another import.
* It import from the `java.lang` package.
* It imports a class from the same package.
* It is only references from the Javadoc.
#### [UpperEll](http://checkstyle.sourceforge.net/config_misc.html#UpperEll)

Checks that `long` numeric literal values are marked by an upper-case ell ('L'). The lower-case ell ('l') can be mistaken for the numeral one ('1').

Valid:
````
long id = 12345L;
````

Invalid:
````
long id = 12345l;
````
#### [VariableDeclarationUsageDistance](http://checkstyle.sourceforge.net/config_coding.html#VariableDeclarationUsageDistance)

Checks that a variable declaration and its first usage are not more than 3 lines. Blocks of initialisation methods don't count toward this total.

See the official [Checkstyle documentation](http://checkstyle.sourceforge.net/config_coding.html#VariableDeclarationUsageDistance) for examples.
#### [VisibilityModifier](http://checkstyle.sourceforge.net/config_design.html#VisibilityModifier)

> This check cannot be suppressed.

Checks the visibility of class members to help enforce encapsulation. Only `static final` fields, immutable (see list below) fields or field with special annotation (see list below), may be public.

The following are considered immutable when `final`, and can be `public`:

* java.lang.String
* java.lang.Integer
* java.lang.Byte
* java.lang.Character
* java.lang.Short
* java.lang.Boolean
* java.lang.Long
* java.lang.Double
* java.lang.Float
* java.lang.StackTraceElement
* java.math.BigInteger
* java.math.BigDecimal
* java.io.File
* java.util.Locale
* java.util.UUID
* java.net.URL
* java.net.URI
* java.net.Inet4Address
* java.net.Inet6Address
* java.net.InetSocketAddress

Fields with the following annotations may be `public`:

* org.junit.Rule
* org.junit.ClassRule
* com.google.common.annotations.VisibleForTesting

Valid:
````
class Foo {

    public final Long id;

    public final String name;

    private String description;

    @VisibleForTesting
    public State state;

    Foo(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }
}
````

Invalid:
````
class Foo {

    public Long id;

    public String name;

    private String description;

    public State state;

    Foo(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }
}
````
#### [WhitespaceAfter](http://checkstyle.sourceforge.net/config_whitespace.html#WhitespaceAfter)

Checks that commas (','), statement terminators (';') and typecasts are all followed by a space.

Valid:
````
doSomething(1, 2, 3);
if (a > 1) { return true; }
String name = (String) list.get(9);
````

Inalid:
````
doSomething(1,2,3);
if (a > 1) { return true;}
String name = (String)list.get(9);
````
#### [WhitespaceAround](http://checkstyle.sourceforge.net/config_whitespace.html#WhitespaceAround)

Checks that tokens are surrounded by whitespace.

### Sevntu



## Disabled Checks

These checks are not enabled. Notes are included for each explaining why.

### Checkstyle

#### [AbstractClassName](http://checkstyle.sourceforge.net/config_naming.html#AbstractClassName)

Prevents some more meaningful abstract class names
#### [ArrayTrailingComma](http://checkstyle.sourceforge.net/config_coding.html#ArrayTrailingComma)

Couldn't get my IDE's (IntelliJ) code style to match.
#### [AvoidStarImport](http://checkstyle.sourceforge.net/config_imports.html#AvoidStarImport)

Ref: Clean Code, Robert C. Martin, J1: Avoid Long Import Lists by Using Wildcards
#### [AvoidStaticImport](http://checkstyle.sourceforge.net/config_imports.html#AvoidStaticImport)

Ref: Clean Code, Robert C. Martin, J2: Don't Inherit Constants
Recommends using a static import to access constants from another class over inheriting them.
#### [EmptyLineSeparator](http://checkstyle.sourceforge.net/config_whitespace.html#EmptyLineSeparator)


#### [FinalLocalVariable](http://checkstyle.sourceforge.net/config_coding.html#FinalLocalVariable)

Doesn't recognise Lombok's `val` as being `final`.
#### [Header](http://checkstyle.sourceforge.net/config_header.html#Header)

Shouldn't need to list in every file, simply listing in project root should be enough.
#### [IllegalInstantiation](http://checkstyle.sourceforge.net/config_coding.html#IllegalInstantiation)

Not really suitable for a template ruleset as it requires an explicit list of classes to apply to.
#### [IllegalTokenText](http://checkstyle.sourceforge.net/config_coding.html#IllegalTokenText)

Generic rule; doesn't embody a 'quality' check.
#### [ImportControl](http://checkstyle.sourceforge.net/config_imports.html#ImportControl)

Generic rule; doesn't embody a 'quality' check.
#### [ImportOrder](http://checkstyle.sourceforge.net/config_imports.html#ImportOrder)

Generic rule; doesn't embody a 'quality' check.
#### [Indentation](http://checkstyle.sourceforge.net/config_misc.html#Indentation)

Couldn't get my IDE's (IntelliJ) code style to match.
#### [InterfaceMemberImpliedModifier](http://checkstyle.sourceforge.net/config_modifier.html#InterfaceMemberImpliedModifier)

Makes interfaces overly verbose.
#### [JavadocMethod](http://checkstyle.sourceforge.net/config_javadoc.html#JavadocMethod)

Only exceptional cases should need to be documented.
#### [JavadocTagContinuationIndentation](http://checkstyle.sourceforge.net/config_javadoc.html#JavadocTagContinuationIndentation)

Couldn't get my IDE's (IntelliJ) code style to match.
#### [JavadocVariable](http://checkstyle.sourceforge.net/config_javadoc.html#JavadocVariable)

Member variables should usually be named such that it is clear what they are. Comments for clarification should be the exception.
#### [MissingCtor](http://checkstyle.sourceforge.net/config_coding.html#MissingCtor)

Would not see constructors created using Lombok's `@NoArgsConstructor`.
#### [MissingOverride](http://checkstyle.sourceforge.net/config_annotation.html#MissingOverride)

The javadoc compiler automatically inherits the javadoc from the overridden method, it doesn't need to be told to do so.
#### [OuterTypeNumber](http://checkstyle.sourceforge.net/config_sizes.html#OuterTypeNumber)

Already covered by the [OneTopLevelClass](#onetoplevelclass) check.
#### [ParameterAssignment](http://checkstyle.sourceforge.net/config_coding.html#ParameterAssignment)

[FinalParameters](#finalparameters) already protects against assigning values to parameters.
#### [RedundantImport](http://checkstyle.sourceforge.net/config_imports.html#RedundantImport)

[UnusedImports](#unusedimports) performs all the same checks and more.
#### [RedundantModifier](http://checkstyle.sourceforge.net/config_modifier.html#RedundantModifier)

conflicts with InterfaceMemberImpliedModifier which is now prefered given changes to interfaces in Java 9
#### [Regexp](http://checkstyle.sourceforge.net/config_regexp.html#Regexp)

Generic rule; doesn't embody a 'quality' check.
#### [RegexpHeader](http://checkstyle.sourceforge.net/config_header.html#RegexpHeader)

Generic rule; doesn't embody a 'quality' check.
#### [RegexpMultiline](http://checkstyle.sourceforge.net/config_regexp.html#RegexpMultiline)

Generic rule; doesn't embody a 'quality' check.
#### [RegexpOnFilename](http://checkstyle.sourceforge.net/config_regexp.html#RegexpOnFilename)

Generic rule; doesn't embody a 'quality' check.
#### [RegexpSingleline](http://checkstyle.sourceforge.net/config_regexp.html#RegexpSingleline)

Generic rule; doesn't embody a 'quality' check.
#### [RegexpSinglelineJava](http://checkstyle.sourceforge.net/config_regexp.html#RegexpSinglelineJava)

Generic rule; doesn't embody a 'quality' check.
#### [SingleLineJavadoc](http://checkstyle.sourceforge.net/config_javadoc.html#SingleLineJavadoc)

I don't use single line javadoc blocks.
#### [SummaryJavadoc](http://checkstyle.sourceforge.net/config_javadoc.html#SummaryJavadoc)

Generic rule; doesn't embody a 'quality' check.
#### [SuperClone](http://checkstyle.sourceforge.net/config_coding.html#SuperClone)

Overridding the `clone()` method is not allowed by the [NoClone](#noclone) check.
#### [SuperFinalize](http://checkstyle.sourceforge.net/config_coding.html#SuperFinalize)

[NoFinalizer](#nofinalizer) prevents use of `finalize()`.
#### [WriteTag](http://checkstyle.sourceforge.net/config_javadoc.html#WriteTag)

Generic rule; doesn't embody a 'quality' check.

### Sevntu

As the sevntu check are considered experimental not all those that are not enabled are listed here. Only where they are disabled due to a conflict with my 'style' or there is another irreconcilable difference that prevents them from being enabled, will they be documented to prevent repeated investigations.

#### [AvoidConditionInversion](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/AvoidConditionInversionCheck.html)

Should already be covered by [SimplifyBooleanExpression](simplifybooleanexpression).
#### [AvoidConstantAsFirstOperandInCondition](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/AvoidConstantAsFirstOperandInConditionCheck.html)

Checks that condition expressions don't become less readable by attempting to use a constant on the left-hand-side of a comparison.

Valid:
````
if (a == 12) {}
````

Invalid:
````
if (12 == a) {}
````
#### [AvoidDefaultSerializableInInnerClasses](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/AvoidDefaultSerializableInInnerClassesCheck.html)

Prevent the use of default serialization methods on inner classes. If an inner class needs to implement the Serializable interface, then it *must* implement both `writeObject()` and `readObject()` methods.
#### [AvoidHidingCauseException](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/AvoidHidingCauseExceptionCheck.html)

Ensures that an exception is re-thrown properly and is not swallowed by a `catch` block.

Valid:
````
try {
    doSomething();
} catch (MyException e) {
    throw new MyOtherException(e);
}
````

Invalid:
````
try {
    doSomething();
} catch (MyException e) {
    throw new MyOtherException();
}
````
#### [AvoidModifiersForTypes](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/AvoidModifiersForTypesCheck.html)

Generic rule; doesn't embody a 'quality' check.
#### [AvoidNotShortCircuitOperatorsForBoolean](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/AvoidNotShortCircuitOperatorsForBooleanCheck.html)

Prevents the use of boolean operators that don't allow short-circuiting the expression. (e.g. '|', '&', '|=' and '&=')

Valid:
````
if ((a < b) || (b > getExpensiveValue())) {}
````

Invalid:
````
if ((a < b) | (b > getExpensiveValue())) {}
````
#### [CauseParameterInException](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/CauseParameterInExceptionCheck.html)

Should already be covered by [AvoidHidingCauseException](#avoidhidingcauseexception).
#### [ChildBlockLength](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/ChildBlockLengthCheck.html)

Appears to be broken as of `1.21.0`.
#### [ConfusingCondition](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/ConfusingConditionCheck.html)

Checks that the expression with the `if` condition in an `if-then-else` statement is not negated.

Valid:
````
if (isValid()) {
    handleValidCondition();
} else {
    handleInvalidCondition();
}
````

Invalid:
````
if (!isValid()) {
    handleInvalidCondition();
} else {
    handleValidCondition();
}
````
#### [ConstructorWithoutParams](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/ConstructorWithoutParamsCheck.html)

Exception class constructors must accept parameters for message and/or cause. This check is applied to classes whose name ends with `Exception`.
#### [CustomDeclarationOrder](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/CustomDeclarationOrderCheck.html)

The [DeclarationOrder](#declarationorder) check already imposes an order for class elements.
#### [DiamondOperatorForVariableDefinition](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/DiamondOperatorForVariableDefinitionCheck.html)

Checks that the diamond operator is used where possible.

Valid:
````
Map<Long, String> idTable = new HashMap<>();
````

Invalid:
````
Map<Long, String> idTable = new HashMap<Long, String>();
````
#### [EitherLogOrThrow](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/EitherLogOrThrowCheck.html)

Checks that when an exception is caught, that if it is logged then it is not also re-thrown. Log or throw; one or the other or neither, but not both.

Accepts `java.util.logging.Logger` and `org.slf4j.Logger`.
#### [EmptyPublicCtorInClass](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/EmptyPublicCtorInClassCheck.html)

This Check looks for useless empty public constructors. Class constructor is considered useless by this Check if and only if class has exactly one ctor, which is public, empty(one that has no statements) and default.

Valid:
````java
class ValidPrivateCtor {
    private ValidPrivateCtor() {
    }
}

class ValidOverloadedCtor {
    public ValidOverloadedCtor() {
    }
    public ValidOverloadedCtor(int i) {
    }
}
````

Invalid:
````java
class Invalid {
     public Invalid() {
     }
}
````
#### [EnumValueName](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/naming/EnumValueNameCheck.html)

Checks that Enum Values match the pattern: `^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$`

Valid:
````
enum Valid {

    ALPHA, BETA, GAMMA_RAY;
}
````

Invalid:
````
enum InvalidConstants {

    alpha, Beta;
}
````
#### [FinalizeImplementation](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/FinalizeImplementationCheck.html)

Checks that the `finalize()` implementation doesn't ignore the base class implementation, and doesn't *only* call the base class implementation.

Valid:
```java
class Valid {
    protected void finalize() {
        try {
            doSomething();
        } finally {
            super.finalize();
        }
    }
}
```

Invalid:
```java
class InvalidNoEffect1 {
    protected void finalize() {
    }
}
class InvalidNoEffect2 {
    protected void finalize() {
        doSomething();
    }
}
class InvalidUseless {
    protected void finalize() {
        super.finalize();
    }
}
class InvalidPublic {
    public void finalize() {
        try {
            doSomething();
        } finally {
            super.finalize();
        }
    }
}
```
#### [ForbidAnnotation](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/annotation/ForbidAnnotationCheck.html)

Generic rule; doesn't embody a 'quality' check.
#### [ForbidCCommentsInMethods](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/ForbidCCommentsInMethodsCheck.html)

Prevents the use of `/* C-style */` comments inside methods.

Valid:
````
void doSomething() {
    // a comment
}
````

Invalid:
````
void doSomething() {
    /* invalid */
}
````
#### [ForbidCertainImports](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/ForbidCertainImportsCheck.html)

Generic rule; doesn't embody a 'quality' check.
#### [ForbidInstantiation](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/ForbidInstantiationCheck.html)

Generic rule; doesn't embody a 'quality' check.
#### [ForbidReturnInFinallyBlock](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/ForbidReturnInFinallyBlockCheck.html)

Prevent the use of a `return` statement in the `finally` block.

Invalid:
````
try {
    doSomething();
{ catch (IOException e) {
    // log error
} finally (
    return true; // invalid
}
````
#### [ForbidThrowAnonymousExceptions](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/ForbidThrowAnonymousExceptionsCheck.html)

[IllegalThrows](#illegalthrows) performs a similar check.
#### [ForbidWildcardAsReturnType](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/ForbidWildcardAsReturnTypeCheck.html)

Prevents declaring a method from returning a wildcard type as its return value.

Valid:
````
<E> List<E> getList() {}
````

Invalid:
````
<E> List<? extends E> getList() {}
````
#### [LogicConditionNeedOptimization](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/LogicConditionNeedOptimizationCheck.html)

Prevent the placement of variables or fields after methods in an expression.

Valid:
````
if (property && getProperty()) {}
````

Invalid:
````
if (getProperty() && property) {}
````
#### [MapIterationInForEachLoop](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/MapIterationInForEachLoopCheck.html)

Checks for unoptimised iterations over `Map`s. Check use of `map.values()`, `map.keySet()` and `map.entrySet()` against the use of the iterator produced to verify if another could be better.
#### [MoveVariableInsideIfCheck](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/MoveVariableInsideIfCheck.html)

Checks if a variable is declared outside an `if` block that is only used within that block.

Valid:
````
if (condition) {
    String variable = input.substring(1);
    return method(variable);
}
return "";
````

Invalid:
````
String variable = input.substring(1);
if (condition) {
    return method(variable);
}
return "";
````
#### [NameConventionForJunit4TestClasses](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/NameConventionForJunit4TestClassesCheck.html)

Checks the names of JUnit test classes. Classes checked are those that have at least one method annotated with `Test` or `org.junit.Test`.

Test class names must match: `.+Test\\d*|.+Tests\\d*|Test.+|Tests.+|.+IT|.+ITs|.+TestCase\\d*|.+TestCases\\d*`
#### [NestedSwitch](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/NestedSwitchCheck.html)

Checks that `switch` statements are not nested within one another.

Valid:
````
void doSomething(int a, int b) {

    switch(a) {
        case 1:
            doMore(b);
            break;
        case 2:
            // ..
        }
    }
}

void doMore(int b) {

    switch(b) {
        case 1:
            //
        case 2:
            //
    }
}
````

Invalid:
````
void doSomething(int a, int b) {

    switch(a) {
        case 1:
            switch(b) {
                case 1:
                    //
                case 2:
                    //
            }
            break;
        case 2:
            // ..
        }
    }
}
````
#### [NoMainMethodInAbstractClass](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/NoMainMethodInAbstractClassCheck.html)

Prevents a `main` method from existing in an `abstract` class.
#### [NumericLiteralNeedsUnderscore](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/NumericLiteralNeedsUnderscoreCheck.html)

Checks that numeric literals use underscores ('_') if over a certain length.

* Decimals

    * 7 or more digits must use the underscore
    * No more than 3 digits between underscores

* Hex

    * 5 or more digits must use the underscore
    * No more than 4 digits between underscores

* Binary

    * 9 or more digits must use the underscore
    * No more than 8 digits between underscores
#### [OverridableMethodInConstructor](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/OverridableMethodInConstructorCheck.html)

Prevents calls to overridable methods from constuctors including other methods that perform the same functions. (i.e. `Cloneable.clone()` and `Serializable.readObject()`)

Invalid:
````
abstract class Base {
    Base() {
        overrideMe();
    }
}
class Child extends Base {
    final int x;
    Child(int x) {
        this.x = x;
    }
    void overrideMe() {
        System.out.println(x);
    }
}
new Child(42); // prints "0"
````
#### [PublicReferenceToPrivateType](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/PublicReferenceToPrivateTypeCheck.html)

Checks that a type is not exposed outside its declared scope.

Invalid:
````
public class OuterClass {
    public InnerClass inner = new InnerClass();
    public SiblingClass sibling = new SiblingClass();
    public InnerClass getValue() { return new InnerClass(); }
    public SiblingClass getSibling() { return new SiblingClass(); }
    private class InnerClass {}
}
class SiblingClass {}
````
#### [RedundantReturn](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/RedundantReturnCheck.html)

Checks for redundant return statements.

Invalid:
````
HelloWorld() {
    doStuff();
    return;
}
void doStuff() {
    doMoreStuff();
    return;
}
````
#### [RequiredParameterForAnnotation](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/annotation/RequiredParameterForAnnotationCheck.html)

Generic rule; doesn't embody a 'quality' check.
#### [ReturnBooleanFromTernary](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/ReturnBooleanFromTernaryCheck.html)

Ternary statements shouldn't have `Boolean` values as results.

Valid:
````
Boolean set = isSet();
Boolean notReady = !isReady();
````

Invalid:
````
Boolean set = isSet() ? True : False;
Boolean notReady = isReady() ? False : True;
````
#### [ReturnNullInsteadOfBoolean](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/ReturnNullInsteadOfBooleanCheck.html)

The `Boolean` type is meant to only represent a binary state: TRUE or FALSE. It is not a ternary value: TRUE, FALSE, null.

Invalid:
````
Boolean isEnabled() {
    if (level > 0) {
        return True;
    }
    if (level < 0) {
        return False;
    }
    return null;
}
````
#### [SimpleAccessorNameNotation](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/SimpleAccessorNameNotationCheck.html)

allow use of non-bean property-like naming
#### [SingleBreakOrContinue](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/SingleBreakOrContinueCheck.html)

Checks that there is at most one `continue` or `break` statement within a looping block (e.g. `for`, `while`, ...)
#### [StaticMethodCandidate](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/design/StaticMethodCandidateCheck.html)

Can't handle private methods called by reflection, which may cause issues with Spring and other DI frameworks.
#### [TernaryPerExpressionCount](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/TernaryPerExpressionCountCheck.html)

Checks that there is at most one ternary statments (`?:`) within an expression.

Invalid:
````
String x = value != null ? "A" : "B" + value == null ? "C" : "D"
````
#### [UniformEnumConstantName](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/naming/UniformEnumConstantNameCheck.html)

Checks that all the values of an `enum` follow the same naming pattern.

Valid:
````
public enum EnumOne {
    FirstElement, SecondElement, ThirdElement;
}

public enum EnumTwo {
    FIRST_ELEMENT, SECOND_ELEMENT, THIRD_ELEMENT;
}
````

Invalid:
````
public enum EnumThree {
    FirstElement, SECOND_ELEMENT, ThirdElement;
}
````
#### [UselessSingleCatch](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/UselessSingleCatchCheck.html)

Checks for catch blocks that are useless. i.e. that catch all exceptions and then just rethrow them.

Invalid:
````
try {
    doSomething();
} catch (Exception e) {
    throw e;
}
````
#### [UselessSuperCtorCall](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/UselessSuperCtorCallCheck.html)

Checks for useless calls to the `super()` method in constructors.

Invalid:
````
class Dummy {
    Dummy() {
        super();
    }
}
class Derived extends Base {
    Derived() {
        super();
    }
}
````
#### [WhitespaceBeforeArrayInitializer](http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/com/github/sevntu/checkstyle/checks/coding/WhitespaceBeforeArrayInitializerCheck.html)

This checks enforces whitespace before array initializer.

Valid:
````java
int[] ints = new int[] {
    0, 1, 2, 3
};

int[] tab = new int[]
                {0, 1, 2, 3};
````

Invalid:
````java
int[] ints = new int[]{0, 1, 2, 3};
````

[Effective Java]: http://amzn.to/2aSz6GE