# Aggregate Javadoc Plugin

## Description

This plugin enables to aggregate Javadoc of all subprojects.
It provides a `aggregateJavadoc` task and generates in a output dir aggregated javadoc.

## How to use

Add the following to your `build.gradle`:

```groovy
plugins {
    id 'me.julb.gradleplugins.aggregatejavadoc' version '1.0.5'
}

aggregateJavadoc {
    // Title of the Javadoc.
    title = 'Aggregated Javadoc title'

    // Output directory where to generate Javadoc.
    outputDir = 'build/aggregated-javadoc'

    // Array of project names to include. If null, include all.
    include = null

    // Array of project names to include. If null, do not exclude anything.
    exclude = null
}
```
