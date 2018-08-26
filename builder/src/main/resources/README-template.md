# kemitix-checkstyle-ruleset

Provides an extensive Checkstyle ruleset for use with Checkstyle, together with a fully configured maven-tile.

 [![Sonatype Nexus (Release)](https://oss.sonatype.org/content/repositories/releases/net/kemitix/checkstyle/)](file:https://img.shields.io/nexus/r/https/oss.sonatype.org/net.kemitix.checkstyle/ruleset.svg?style=for-the-badge)
 [![Maven Central](https://search.maven.org/#search%%7Cga%%7C1%%7Cg%%3A%%22net.kemitix%%22%%20AND%%20a%%3A%%22mon%%22)](file:https://img.shields.io/maven-central/v/net.kemitix.checkstyle/ruleset.svg?style=for-the-badge)

 [![Coverage](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://img.shields.io/sonar/https/sonarcloud.io/net.kemitix.checkstyle%%3Aruleset/coverage.svg?style=for-the-badge#.svg)
 [![Tech Debt](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://img.shields.io/sonar/https/sonarcloud.io/net.kemitix.checkstyle%%3Aruleset/tech_debt.svg?style=for-the-badge#.svg)
 [![SQALE Rating](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=sqale_rating#.svg)
 [![Alert Status](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=alert_status#.svg)
 [![Reliability](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=reliability_rating#.svg)
 [![Security](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=security_rating#.svg)
 [![SQALE Index](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=sqale_index#.svg)
 [![Vulnerabilities](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=vulnerabilities#.svg)
 [![Bugs](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=bugs#.svg)
 [![Code Smells](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=code_smells#.svg)
 [![NCLOC](https://sonarcloud.io/dashboard?id=net.kemitix.checkstyle%%3Aruleset)](file:https://sonarcloud.io/api/project_badges/measure?project=net.kemitix.checkstyle%%3Aruleset&metric=ncloc#.svg)

 [![Codacy](https://app.codacy.com/project/kemitix/kemitix-checkstyle-ruleset/dashboard)](file:https://img.shields.io/codacy/grade/f1208289122e48daa89c46671293b3c2.svg?style=for-the-badge)
 [![JPeek](http://i.jpeek.org/net.kemitix/kemitix-checkstyle-ruleset/index.html)](file:http://i.jpeek.org/net.kemitix.checkstyle/ruleset/badge.svg)

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
                        <tile>net.kemitix.checkstyle:tile:RELEASE</tile>
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

### Change from 2.x

In 2.x, the level was specified as the goal to invoke. In 3.x, there is only the 'check' goal. The level is now specified as a configuration parameter. See the example below. The kemitix-checkstyle-maven-plugin has also been removed in favour of the maven-tile.

## All Checks

Rule|Level|Source|Enabled|Suppressible
----|-----|------|-------|------------
%s

## Enabled Checks

The following is a list of each of the checks and the expectations each has on your code.

### Checkstyle

Rules are listed in alphabetical order.

%s

### Sevntu

%s

## Disabled Checks

These checks are not enabled. Notes are included for each explaining why.

### Checkstyle

%s

### Sevntu

As the sevntu check are considered experimental not all those that are not enabled are listed here. Only where they are disabled due to a conflict with my 'style' or there is another irreconcilable difference that prevents them from being enabled, will they be documented to prevent repeated investigations.

%s

[Effective Java]: http://amzn.to/2aSz6GE
