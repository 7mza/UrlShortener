# url-shortener

#### description:

Simplistic url shortener implemented using 3 tier architecture

* Ctrl tier: with Spring MVC exposing a CRUD REST API **on /api/short_url/**
    * Simple validation on URL format (size, valid url content)
    * @ControllerAdvice to transform all spring exceptions to custom business exceptions
* Service tier
    * automatic generation of short_url
    * duplication handling (same url = same short_url)
* Data tier: with Spring DATA + in memory H2 SQL DB

API is documented following Swagger IDL, in a programmatic way, directly in API interface.

Unit & integration testing made with Junit5, AssertJ & Mockito

#### test, build & run locally:

```
./mvnw (or mvn) clean package spring-boot:run
```

#### swagger API doc:

[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

#### testing redirection :

use **/api/short_url/** to populate DB then use **GET:/short_url/{short_url}** to test redirection

[http://localhost:8080/short_url/{short_url}](http://localhost:8080/short_url/{short_url})

#### checking in-mem H2 DB

[http://localhost:8080/h2](http://localhost:8080/h2)

Connection informations can be found in [./src/main/resources/application.yml]("./src/main/resources/application.yml)

#### deploy

A version is deployed on IBM cloud foundry for testing purposes, **CF is out of scope of this demo**

swagger : [http://bhamza.eu-gb.mybluemix.net/swagger-ui/](http://bhamza.eu-gb.mybluemix.net/swagger-ui/)

redirect : [http://bhamza.eu-gb.mybluemix.net/short_url/{short_url}](http://bhamza.eu-gb.mybluemix.net/short_url/{short_url})

db: [http://bhamza.eu-gb.mybluemix.net/h2](http://bhamza.eu-gb.mybluemix.net/h2)

#### TODO

* ci/cd pipeline with github + GCP
* Spring security for Auth
* github badges (test coverage, ...etc)
* 