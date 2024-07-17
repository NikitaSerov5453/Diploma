FROM openjdk:21-jdk-slim-bullseye
WORKDIR /app
COPY /target/Diploma-0.0.1-SNAPSHOT.jar /app/Diploma.jar
ENTRYPOINT ["java", "-jar", "Diploma.jar"]