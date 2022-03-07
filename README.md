## Atypon Recipes Spring Boot App

This Maven project as a solid starter project for Atypon Recipe Apis

## Environment Setup

You will need to install the following to run the application locally:

* Java 11+
* Maven 3+

## Running the applications

This starter contains one runnable applications: `api`. You can run `api` as any other Spring Boot
application (http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html).

To install the required dependencies:

```
> mvn clean install
```

### Running the `api` application

The `api` application can be run as a Spring Boot app from the command line:

```
   > cd atypon-api-java-ms
   > mvn spring-boot:run -Dspring-boot.run.profiles=dev 
```


## Tests

### Unit

The unit tests run automatically during the `mvn test` phase as part of the `mvn clean install` lifecycle. To run the
tests without doing a full build, run:

```
mvn test
```

## Atypon Recipe APIs are

`/api/recipes/search`
search recipes.

`/api/recipe` 
get recipe information by recipe title.

`/api/{id}/recipe`
get recipe information by recipe id.

`/api/exclude` 
custom api to calculate the calories after excluding an ingredient or more.

You can access the api Swagger docs from http://localhost:8090/api/v2/api-docs 
Just copy content to https://editor.swagger.io/
