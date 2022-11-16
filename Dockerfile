FROM openjdk:17-jdk-slim-buster
ADD /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
