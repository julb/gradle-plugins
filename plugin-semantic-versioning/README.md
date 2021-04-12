# Semantic Versioning Plugin

## Description

This plugin configures a Gradle project to add semantic versioning tasks useful in CI/CD toolchain.
It relies on the fact that the version of the project is managed through `<project>/gradle.properties` file.

## How to use

Add the following to your `build.gradle`:

```groovy
plugins {
    id 'me.julb.gradleplugins.semanticversioning' version '1.0.4'
}
```

And configure the version in `gradle.properties`:

```properties
version=1.0.0
```

The following tasks will be made available:

- `currentVersion` : this task will print the current project version in `stdout`.

```bash
$ ./gradlew currentVersion
> Task :currentVersion
1.0.0
```

- `currentReleaseVersion` : this task will print the current release project version in `stdout`.

```bash
$ ./gradlew currentReleaseVersion
> Task :currentReleaseVersion
1.0.0

$ ./gradlew changeVersionPatch
> Task :changeVersionPatch
[Julb] Asked to update version with parameter <Patch>
[Julb] New project version is 1.0.1-SNAPSHOT

$ ./gradlew currentReleaseVersion
> Task :currentReleaseVersion
1.0.1
```

- `currentBuildVersion` : this task will print the current project version and the abbreviated git sha1 in `stdout`.

```bash
$ ./gradlew currentBuildVersion
> Task :currentBuildVersion
1.0.0.15c2571
```

- `changeVersionMajor` : this task will update the `gradle.properties` of the project and move the current version to the next major version, with `-SNAPSHOT` appended.

```bash
$ ./gradlew currentVersion
> Task :currentVersion
1.0.0

$ ./gradlew changeVersionMajor
> Task :changeVersionMajor
[Julb] Asked to update version with parameter <Major>
[Julb] New project version is 2.0.0-SNAPSHOT

$ ./gradlew currentVersion
> Task :currentVersion
2.0.0-SNAPSHOT
```

- `changeVersionMinor` : this task will update the `gradle.properties` of the project and move the current version to the next minor version, with `-SNAPSHOT` appended.

```bash
$ ./gradlew currentVersion
> Task :currentVersion
1.0.0

$ ./gradlew changeVersionMinor
> Task :changeVersionMinor
[Julb] Asked to update version with parameter <Minor>
[Julb] New project version is 1.1.0-SNAPSHOT

$ ./gradlew currentVersion
> Task :currentVersion
1.1.0-SNAPSHOT
```

- `changeVersionPatch` : this task will update the `gradle.properties` of the project and move the current version to the next patch version, with `-SNAPSHOT` appended.

```bash
$ ./gradlew currentVersion
> Task :currentVersion
1.0.0

$ ./gradlew changeVersionPatch
> Task :changeVersionPatch
[Julb] Asked to update version with parameter <Patch>
[Julb] New project version is 1.0.1-SNAPSHOT

$ ./gradlew currentVersion
> Task :currentVersion
1.0.1-SNAPSHOT
```

- `changeVersionRelease` : this task will update the `gradle.properties` of the project and remove the `-SNAPSHOT` suffix of the current version.

```bash
$ ./gradlew currentVersion
> Task :currentVersion
1.0.0-SNAPSHOT

$ ./gradlew changeVersionRelease
> Task :changeVersionRelease
[Julb] Asked to update version with parameter <Release>
[Julb] New project version is 1.0.0

$ ./gradlew currentVersion
> Task :currentVersion
1.0.0
```

- `changeVersionCustom` : this task will update the `gradle.properties` of the project and set the version to the given property value of `newVersion`.

```bash
$ ./gradlew currentVersion
> Task :currentVersion
1.0.0-SNAPSHOT

$ ./gradlew changeVersionCustom -PnewVersion=1.2.0-SNAPSHOT
> Task :changeVersionCustom
[Julb] Asked to update version with parameter <Custom>
[Julb] New project version is 1.2.0-SNAPSHOT

$ ./gradlew currentVersion
> Task :currentVersion
1.2.0-SNAPSHOT
```
