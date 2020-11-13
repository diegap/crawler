# crawler
Command line crawler

#### Tech stack
- Java 1.8
- [Gradle](https://docs.gradle.org/5.5.1/userguide/userguide.html) 5.6.1 (wrapper provided in sources)
- [AssertJ](https://assertj.github.io/doc/) 3.18.0
- [jSoup](https://jsoup.org/) 1.11.2

#### System requirements
In order to build you need to install:
   - JDK 1.8 (recommended installation via [SDKMAN!](https://sdkman.io/usage))

#### Build
    $ ./gradlew clean build

#### Provided binary
The tool is provided as a binary jar in the project root:

    $ ./java -jar crawler.jar </path/to/origin.html> </path/to/target.html> <element-id-to-find>

> Output:
```xpath2
/html[0]/body[1]/div[0]/div[1]/div[2]/div[0]/div[0]/div[1]
```

> Output 2:
```textmate
element not found
```