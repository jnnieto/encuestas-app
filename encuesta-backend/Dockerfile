FROM openjdk:18-ea-11-alpine3.15
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app-encuesta-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app-encuesta-0.0.1-SNAPSHOT.jar"]
