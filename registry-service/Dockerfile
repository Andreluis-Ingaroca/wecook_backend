FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} registry-service.jar

ENTRYPOINT ["java","-jar","/registry-service.jar"]