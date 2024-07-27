FROM openjdk:17-jdk-alpine
FROM --platform=linux/amd64 amazonlinux:2018.03
ARG JAR_FILE=target/*jar
COPY ./target/car-rental-rest-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]