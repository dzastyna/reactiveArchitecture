FROM openjdk:8-jdk-alpine
ADD target/arqlibrarian-issues.rest-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
EXPOSE 6702