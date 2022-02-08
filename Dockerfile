FROM openjdk:11
ADD target/niver-api-SNAPSHOT.jar niver-api-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "niver-api-SNAPSHOT.jar"]
