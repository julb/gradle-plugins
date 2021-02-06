![Build](https://github.com/julb/gradle-plugins/workflows/Build/badge.svg)

# Julb Gradle Plugins

## Description

This project is a mono-repo providing custom Gradle plugins:

- [me.julb.gradleplugins.additionaljars](./plugin-additional-jars/README.md)
- [me.julb.gradleplugins.aggregatejavadoc](./plugin-aggregate-javadoc/README.md)
- [me.julb.gradleplugins.asciidoctor](./plugin-asciidoctor/README.md)
- [me.julb.gradleplugins.java11](./plugin-java11/README.md)
- [me.julb.gradleplugins.semanticversioning](./plugin-semantic-versioning/README.md)

## How to develop

### Build the project

```bash
$ ./gradlew build
```

### Install the plugins in your Maven Local

```bash
$ ./gradlew publishToMavenLocal
```

### Publish the plugins to Gradle Plugins portal

```bash
$ ./gradlew publishPlugins -Dgradle.publish.key=<key> -Dgradle.publish.secret=<secret>
```

## Contributing

This project is totally open source and contributors are welcome.
