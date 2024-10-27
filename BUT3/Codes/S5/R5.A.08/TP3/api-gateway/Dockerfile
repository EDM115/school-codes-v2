FROM openjdk:17-jdk-alpine

VOLUME /tmp

RUN apk add --no-cache bash

COPY wait-for-it.sh /wait-for-it.sh
COPY target/*.jar app.jar

ENTRYPOINT ["/wait-for-it.sh", "ms-user:8081", "--timeout=0", "--strict", "--", "/wait-for-it.sh", "ms-location:8082", "--timeout=0", "--strict", "--", "java", "-jar", "/app.jar"]
