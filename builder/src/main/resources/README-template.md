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
