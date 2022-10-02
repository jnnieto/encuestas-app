FROM openjdk:latest
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app-encuesta-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app-encuesta-0.0.1-SNAPSHOT.jar"]
