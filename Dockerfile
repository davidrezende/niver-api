FROM openjdk:11
ADD target/niver-0.0.1-SNAPSHOT.jar niver-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "niver-0.0.1-SNAPSHOT.jar"]
