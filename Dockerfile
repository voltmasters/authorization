FROM amazoncorretto:21-alpine

LABEL maintainer="Subhrodip Mohanta <contact@subhrodip.com>"
LABEL group="com.subhrodip.voltmasters"
LABEL artifact="authorization"
LABEL platform="java"
LABEL name="Authorization Server"
LABEL org.opencontainers.image.source="https://github.com/voltmasters/authorization"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

# If you are changing server port, be sure to change this as well
EXPOSE 8080

#Running the application with `prod` profile
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=prod", "/app.jar" ]
