## Atypon Recipes Spring Boot App

This Maven project as a solid starter project for

## Environment Setup

You will need to install the following to run the application locally:

* Java 11+
* Maven 3+
* Docker tools
* Optional: Postgres Admin

## Creating Docker Solr Image in Local

Use [solr](https://github.com/bitnami/bitnami-docker-solr) repo to build docker image locally

## Running the applications

This starter contains one runnable applications: `api`, and `admin`. You can run `api` as any other Spring Boot
application (http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html).
Before you start `api`, you need to start the database and Solr.

To install the required dependencies:

```
> mvn clean install
```

### Running the database and Solr servers

We use Docker containers to run the Postgres database and Solr servers. They are configured within
the `docker-compose.yml` in the root project directory. View
the [database versioning page on Confluence](https://confluence.tools.weightwatchers.com/display/PB/Database+Versioning+with+Liquibase)
for an understanding of how Liquibase is configured.

To start the database and Solr instance from the command line:

```
> docker-compose up
```

The Postgres database is available at `localhost:9001/postgres`. The Solr admin console is available
at `localhost:9002/solr`.

To stop the containers:

```
> docker-compose down
```

### Running the `api` application

The `api` application can be run as a Spring Boot app from the command line:

```
   > cd api
   > mvn spring-boot:run -Drun.arguments="--spring.profiles.active=local"
```

You can access the api Swagger docs from `https://localhost:8445/api/swagger-ui.html`
> Note: Default debug port open on `8085`

## Tests

### Unit

The unit tests run automatically during the `mvn test` phase as part of the `mvn clean install` lifecycle. To run the
tests without doing a full build, run:

```
mvn test
```

### Integration

The integration tests do not run automatically because they take much longer to complete. The integration tests only run
during the `mvn verify` phase when the `integration-test` phase is active. To run the integration tests:

```
mvn verify -P integration-test 
```

## Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9000) with:

```shell
$ docker-compose -f docker/sonar/sonar.yml up -d
```

You can run a Sonar analysis with using
the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven
plugin.

First, run the integration test so that jacoco code coverage gets generated using following command -

```shell
$ mvn clean verify -P integration-test -pl api
```

Then, run a Sonar analysis on API/Admin project respectively:

```shell
$ cd api
$ mvn initialize sonar:sonar
```

Or

```shell
$ mvn initialize sonar:sonar -pl api
```

> Please make sure you do not run `mvn clean` with `sonar:sonar` goal as it will delete `surefire`, `failsafe` and `jacoco` files and directories required for sonar plugin to perform the analysis

You can also run sonar goal on the parent project as well, but that will generate consolidated report for the entire
code base

```shell
$ mvn clear verify -P integration-test && mvn initialize sonar:sonar
```

## Vulnerability Scan

To scan Api for vulnerabilities you need to follow these steps

1- go atypon-recipes-java-ms/atypon-api-java-ms directory on yor local machine and build broadleaf-api-local

```shell
$ docker build \
    --build-arg JAR_FILE=target/api.jar \
    --build-arg INST_FILE=target/agents/spring-instrument.jar \
    --build-arg NR_JAR_FILE=target/newrelic/newrelic.jar \
    --build-arg NR_CONFIG_FILE=target/newrelic/newrelic.yml \
    -t broadleaf-api-local .

$ docker build \
    --build-arg JAR_FILE=target/admin.jar \
    --build-arg INST_FILE=target/agents/spring-instrument.jar \
    --build-arg NR_JAR_FILE=target/newrelic/newrelic.jar \
    --build-arg NR_CONFIG_FILE=target/newrelic/newrelic.yml \
    -t broadleaf-admin-local .
```

2- install [trivy](https://aquasecurity.github.io/trivy/v0.18.3/installation/) then run these 2 command

```shell
$ trivy --exit-code 0 --severity CRITICAL,HIGH -f json -o api-security-report.json --no-progress --light broadleaf-api-local
$ trivy --exit-code 0 --severity CRITICAL,HIGH -f json -o admin-security-report.json --no-progress --light broadleaf-admin-local
```

3- you will find two reports api-security-report.json and admin-security-report.json which contain CRITICAL and HIGH
vulnerabilities

## Github actions

self-hosted runner on

- JDK 11 (available at `jdk11` location)
  and
- Maven 3.6.3 (available at `/home/runner/apache-maven-3.6.3` location)

to the github runner itself. Based on choice we can modify the environment variables like so in the github workflow
files -

```yaml
  - name: Set environment variables
    id: JDK_MAVEN_ENV
    run: |
      echo "JAVA_HOME=/jdk8" >> $GITHUB_ENV
      echo "MAVEN_HOME=/home/runner/apache-maven-3.6.3" >> $GITHUB_ENV
  - name: Update System path
    id: JDK_MAVEN_PATH_SET
    run: |
      echo "$JAVA_HOME/bin" >> $GITHUB_PATH
      echo "$MAVEN_HOME/bin" >> $GITHUB_PATH
```
