# kemitix-checkstyle-ruleset

Provides an extensive Checkstyle ruleset for use with Apache's `maven-checkstyle-plugin`.

The ruleset includes checks from both the core Checkstyle library and from the Sevntu-Checkstyle library.

* [Requirements](#requirements)
* [Usage](#usage)
* [All Checks](#all-checks)
* [Enabled Checks](#enabled-checks)
    * [Checkstyle](#checkstyle)
    * [Sevntu](#sevntu)
    * [Kemitix](#kemitix)
* [Disabled Checks](#disabled-checks)
    * [Checkstyle](#checkstyle-1)
    * [Sevntu](#sevntu-1)

## Requirements

* [maven-checkstyle-plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/) 2.17+
* [Checkstyle](http://checkstyle.sourceforge.net/) 7.0+
* [Sevntu-checkstyle](http://sevntu-checkstyle.github.io/sevntu.checkstyle/) 1.21.0+

## Usage

To use this ruleset add the plugin `kemitix-checktyle-ruleset-maven-plugin`.
The `maven-checkstyle-plugin` will be included automatically.

The following levels implement increasingly strict rulesets:

* 0-disabled
* 1-layout
* 2-naming
* 3-javadoc
* 4-tweaks
* 5-complexity

### Change from 2.x

In 2.x, the level was specified as the goal to invoke. In 3.x, there is only the 'check' goal.
The level is now specified as a configuration parameter. See the example below.

### Example

````
<properties>
    <kemitix-checkstyle-ruleset.version>2.1.0</kemitix-checkstyle-ruleset.version>
    <kemitix-checkstyle-ruleset.level>5-complexity</kemitix-checkstyle-ruleset.level>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>net.kemitix</groupId>
            <artifactId>kemitix-checkstyle-ruleset-maven-plugin</artifactId>
            <version>${kemitix-checkstyle-ruleset.version}</version>
            <configuration>
                <level>${kemitix-checkstyle-ruleset.level}</level>
            </configuration>
            <executions>
                <execution>
                    <phase>validate</phase>
                    <goals>
                        <goal>check</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
````

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

### Kemitix

%s

## Disabled Checks

These checks are not enabled. Notes are included for each explaining why.

### Checkstyle

%s

### Sevntu

As the sevntu check are considered experimental not all those that are not enabled are listed here. Only where they are disabled due to a conflict with my 'style' or there is another irreconcilable difference that prevents them from being enabled, will they be documented to prevent repeated investigations.

%s

[Effective Java]: http://amzn.to/2aSz6GE
