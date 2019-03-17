#########################################################
#
# To learn more about this project, visit:
# https://github.com/kcbear/docker-example/wiki/Notes
#
#########################################################


Required JDK 8+

#To build
./gradlew build

# To run the jar:
java -jar build/libs/spring-boot-rest-docker-1.0.0.jar

# Description
After run the jar and started the application, a tomcat web server will be started with port 8080.
application.log file is created in the current directory after started.
This spring boot application expose 2 rest API services and the response as JSON

    1. http://localhost:8080/accounts/
    2. http://localhost:8080/transactions/{accountNumber}

Mocking database data is pre-loaded during startup.

Example:
    listing accounts:       http://localhost:8080/accounts/
    listing transactions:   http://localhost:8080/transactions/1001
