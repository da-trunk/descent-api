# descent-api

Demo project for [spring-data-naked](../spring-data-naked).

# Using this project

## Requirements

* JDK17+ (for `descent-api-graphql`)
  * JDK8+ (for everything else)
* Maven 3.0.3+
* Docker

1. Clone: `git clone https://github.com/da-trunk/descent-api.git`
1. Build: `cd descent-api && mvn install -DskipTests`
    * Note: if you don't have JDK17+, avoid building the one project which depends on it: `mvn install -DskipTests -pl '!descent-api-graphql'`
1. Configure testcontainers:
    1. Create a file named `.testcontainers.properties` in your home directory.
    1. Give `testcontainers` permission to keep containers running when configured to do so by populating this file with the following contents (TODO: [formatting](https://gist.github.com/clintel/1155906)):
    
        ```
        testcontainers.reuse.enable=true
        ```
        
1. Start a database, create schema, and deploy some randomly generated test content: `mvn spring-boot:run -pl .\si-job-db`
1. Start a spring-data-rest server: `mvn spring-boot:run -pl .\si-job-server`
1. Open browser and navigate to [http://localhost:9080/api](http://localhost:9080/api)
1. Stop the server: *CTRL+C*
1. Start a java-graphql server: `mvn spring-boot:run -pl .\si-job-graphql` (this requires JDK17+)
1. Navigate to [http://localhost:9080/graphiql](http://localhost:9080/graphiql)

# Testing

So far, I've only used this to experiment with entity models.  There are some tests in `descent-api-server` which run at the EntityManager (JPA / Hibernate) level and can be useful when trying things there without the complexity of the above layers.  But, they currently require that a DB must is running.