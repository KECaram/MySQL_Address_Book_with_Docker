FROM openjdk:8-jdk-alpine
LABEL maintainer="kecaram@gmail.com"
VOLUME /main-app
ADD target/springboot app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]