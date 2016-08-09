# kemitix-checkstyle-ruleset

Provides an extensive Checkstyle ruleset for use with Apache's `maven-checkstyle-plugin`.

The ruleset includes checks from both the core Checkstyle library and from the Sevntu-Checkstyle library.

## Requirements

* [maven-checkstyle-plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/) 2.17+
* [Checkstyle](http://checkstyle.sourceforge.net/) 7.0+
* [Sevntu-checkstyle](http://sevntu-checkstyle.github.io/sevntu.checkstyle/) 1.21.0+

## Usage

To use this ruleset in your `maven-checkstyle-plugin` configuration add `checkstyle`, `sevntu-checkstyle-maven-plugin` and `kemitix-checktyle-ruleset` as dependencies of the `maven-checkstyle-plugin`.

You need to include `checkstyle` as the version bundled with the `maven-checkstyle-plugin` is not up-to-date enough.

````
<properties>
    <checkstyle.version>7.0</checkstyle.version>
    <sevntu-checkstyle-maven-plugin.version>1.21.0</sevntu-checkstyle-maven-plugin.version>
    <kemitix-checkstyle-ruleset.version>1.0.0</kemitix-checkstyle-ruleset.version>
</properties>
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-checkstyle-plugin</artifactId>
        <dependencies>
            <dependency>
                <groupId>com.puppycrawl.tools</groupId>
                <artifactId>checkstyle</artifactId>
                <version>${checkstyle.version}</version>
            </dependency>
            <dependency>
                <groupId>com.github.sevntu.checkstyle</groupId>
                <artifactId>sevntu-checkstyle-maven-plugin</artifactId>
                <version>${sevntu-checkstyle-maven-plugin.version}</version>
            </dependency>
            <dependency>
                <groupId>net.kemitix</groupId>
                <artifactId>kemitix-checkstyle-ruleset</artifactId>
                <version>${kemitix-checkstyle-ruleset.version}</version>
            </dependency>
        </dependencies>
    </plugin><!-- maven-checkstyle-plugin -->
````

## Rules Enabled

### Checkstyle

TODO

### Sevntu

TODO
