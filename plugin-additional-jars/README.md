# Additional Jars Gradle Plugin

## Description

This plugin enables to automatically include Source and Javadoc jars in the build for your Gradle Java Projects.

It also allows to set common attributes on your MANIFEST.MF files such as:

| Entry name             |              Value              |
| ---------------------- | :-----------------------------: |
| Implementation-Title   | `project.group:project.version` |
| Implementation-Version |        `project.version`        |
| Implementation-Vendor  |   `project.organizationName`    |
| Implementation-URL     |    `project.organizationUrl`    |
| Specification-Title    | `project.group:project.version` |
| Specification-Version  |        `project.version`        |
| Specification-Vendor   |   `project.organizationName`    |
| Created-By             |     `Gradle gradleVersion`      |
| Built-Date             |         `current date`          |
| Built-By               |  `systemProperty['user.name']`  |
| Built-JDK              |  `systemProperty['java.name']`  |
| Built-Host             |           `hostname`            |
| Source-Compatibility   |  `project.sourceCompatibility`  |
| Target-Compatibility   |  `project.targetCompatibility`  |

## How to use

Add the following to your `build.gradle`:

```groovy
plugins {
    id 'me.julb.gradleplugins.additionaljars' version '1.0.0'
}
```
