FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} recipe-service.jar

ENTRYPOINT ["java","-jar","/recipe-service.jar"]