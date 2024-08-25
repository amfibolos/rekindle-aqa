# <p align="center"> <span style="color:red;">rekindle-book-store-aqa
## <p align="center"> <span style="color:red;">Rekindle Bookstore playground for automated quality assurance

## <span style="color:green;"> Built With

* [Kotlin](https://kotlinlang.org/)
* [Gradle](https://gradle.org/)
* [Rest Assured](https://rest-assured.io/)
* [Guice](https://github.com/google/guice)
* [JUnit5](https://junit.org/junit5/)

## <span style="color:green;"> Coming soon

* [Allure Report](https://allurereport.org/)
* [Serenity BDD](https://serenity-bdd.info/)
* [Cucumber](https://cucumber.io/)
* [Pact](https://docs.pact.io/)
* [Playwright with TS](https://playwright.dev/)
* [Docker](https://www.docker.com/)
* [Jenkins](https://www.jenkins.io/)
  
# <span style="color:yellow;"> Getting Started

## <span style="color:magenta;"> Gradle
* <span style="color:lime;">The project uses [Gradle 8.5](https://services.gradle.org/distributions/gradle-8.5-bin.zip)
  as its build tool
* <span style="color:lime;">Create GRADLE_HOME system variable and add it to your PATH
* <span style="color:lime;">Alternatively you can use Gradle wrapper which comes with the project

## <span style="color:magenta;"> Java
<span style="color:lime;">The project uses [Java 21](https://corretto.aws/downloads/resources/21.0.1.12.1/amazon-corretto-21.0.1.12.1-windows-x64-jdk.zip)

* <span style="color:lime;">This project was build and tested on amazon **_coretto 21.0.1_**
* <span style="color:lime;">Create JAVA_HOME system variable and add it to your PATH

## <span style="color:gold;"> Documentation

### <span style="color:gold;"> 1. Domain Module

* <span style="color:coral;">application submodule
```
contains generic applicatin oriented abstract code
```
* <span style="color:coral;">core submodule
```
contains domain-specific business code
```
### <span style="color:gold;"> 2. Rest Module
* <span style="color:coral;">rest-assured-adapter submodule
```
contains implementation of application module using Rest-Assured framework 
```
### <span style="color:gold;"> 3. AQA Module
* <span style="color:coral;">rest-junit-aqa submodule
```
contains tests implementing rest-assured-adapter with dependency injection
supplied by Guice framework
```


# <span style="color:violet;"> UNDER CONSTRUCTION...