# AsciiDoctor Plugin

## Description

This plugin applies the [AsciiDoctor plugin](https://plugins.gradle.org/plugin/org.asciidoctor.jvm.base) and provides a `asciidoctorZip` task to compress the generated docs directory.

## How to use

Add the following to your `build.gradle`:

```groovy
plugins {
    id 'me.julb.gradleplugins.asciidoctor' version '1.0.2'
}
```
