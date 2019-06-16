FROM openjdk:8-jdk-alpine
ADD target/arqlibrarian-registry-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8761