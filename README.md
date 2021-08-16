# rippy

![build](https://github.com/averak/rippy/workflows/build/badge.svg)
![test](https://github.com/averak/rippy/workflows/test/badge.svg)
![Version 1.0](https://img.shields.io/badge/version-1.0-yellow.svg)
[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

This app is schedule adjustment service for students.

## Develop

### Requirements

- Java OpenJDK 11
- Spring Boot 2.5
- Angular 12
- MySQL 8.0

### Usage

If you want to run on Windows, you can use `gradlew.bat` instead of of `gradlew`.

#### How to bulid

```sh
$ ./gradlew build -x test
```

When build successful, you can find .jar file in `app/build/libs`

#### How to run

First, you need to launch mysql with `docker-compose`.

```sh
$ docker-compose up -d
```

Then you can launch application.
Default port is `8080`. If you want to change port, run with `-Dserver.port=XXXX`.

```sh
# 1. run .jar file
$ java -jar rippy-<version>.jar  # -Dspring.profiles.active=<environment>

# 2. run on dev environment
$ ./gradlew bootRun
```

#### How to test

```sh
# 1. all tests
$ ./gradlew test

# 2. only unit tests
$ ./gradlew unitTest

# 3. only integration tests
$ ./gradlew integrationTest
```

### API docs

This project support Swagger UI.

1. Run application
2. Access to [Swagger UI](http://localhost:8080/swagger-ui/)

## Wiki

You can find more details on [specification](https://miro.com/app/board/o9J_l2MWjGA=/).
