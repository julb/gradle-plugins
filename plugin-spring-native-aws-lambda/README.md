# Spring native AWS Lambda Plugin

## Description

This plugin 

If the [Gradle spring-aot plugin](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#spring-aot-gradle) is applied on the project, it provides a `lambdaDistZip` task which :
* Generate a shell `bootstrap` file
* Package a Lambda ZIP archive, deployable on AWS, containing the `bootstrap` and the native executable.

## How to use

Add the following to your `build.gradle`:

```groovy
plugins {
    id 'me.julb.gradleplugins.spring-native-aws-lambda' version '1.0.8'
}

awsLambdaDistZip {
    // ...
}

```
