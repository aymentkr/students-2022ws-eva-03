# Read Me First

The following was discovered as part of building this project:

* The following dependencies are not known to work with Spring Native: 'Spring Web Services, Groovy Templates, Rest
  Repositories HAL Explorer, Spring for GraphQL, Spring HATEOAS, Spring Session, Apache Freemarker, Jersey, Spring
  Configuration Processor, Spring Boot DevTools, Rest Repositories, Vaadin'. As a result, your application may not work
  as expected.

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/gradle-plugin/reference/html/#build-image)
* [Spring Native Reference Guide](https://docs.spring.io/spring-native/docs/0.12.1/reference/htmlsingle/)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)
* [Spring Web Services](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#io.webservices)
* [Groovy Templates](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web.reactive)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web)
* [Mustache](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)
* [Spring HATEOAS](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web.spring-hateoas)
* [Spring Session](https://docs.spring.io/spring-session/reference/)
* [Apache Freemarker](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)
* [Jersey](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web.servlet.jersey)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#using.devtools)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Vaadin](https://vaadin.com/spring)

### Guides

The following guides illustrate how to use some features concretely:

* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Creating CRUD UI with Vaadin](https://spring.io/guides/gs/crud-with-vaadin/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Configure the Spring AOT Plugin](https://docs.spring.io/spring-native/docs/0.12.1/reference/htmlsingle/#spring-aot-gradle)

## Spring Native

This project has been configured to let you generate either a lightweight container or a native executable.

### Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get started with Spring
Native. Docker should be installed and configured on your machine prior to creating the image,
see [the Getting Started section of the reference guide](https://docs.spring.io/spring-native/docs/0.12.1/reference/htmlsingle/#getting-started-buildpacks)
.

To create the image, run the following goal:

```
$ ./gradlew bootBuildImage
```

Then, you can run the app like any other container:

```
$ docker run --rm -p 8080:8080 PizzaProject_Tekary_Aljarrah_Chaleh:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image. The GraalVM
native-image compiler should be installed and configured on your machine,
see [the Getting Started section of the reference guide](https://docs.spring.io/spring-native/docs/0.12.1/reference/htmlsingle/#getting-started-native-build-tools)
.

To create the executable, run the following goal:

```
$ ./gradlew nativeCompile
```

Then, you can run the app as follows:

```
$ build/native/nativeCompile/PizzaProject_Tekary_Aljarrah_Chaleh
```
