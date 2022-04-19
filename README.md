
#Running project
## Requirements
- JDK 17 (developed with Amazon Corretto 17)
- Project uses Lombok for code generation. If opened and run from IDE it might require additional plugins/settings. For intellij Idea that will be lombok plugin and enabling `Enable annotation processing`


## Building
Project is based on Spring boot + maven. It can be build using included `mvnw`

- For building and running tests `mvnw clean test`
- For building, runing tests, and generating target `mvnw clean install`
- To run project `mvnw spring-boot:run`


## Changing run parameters 
Parameters can be changed in `resources/application.properties` file. 
