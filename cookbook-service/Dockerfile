FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} cookbook-service.jar

ENTRYPOINT ["java","-jar","/cookbook-service.jar"]