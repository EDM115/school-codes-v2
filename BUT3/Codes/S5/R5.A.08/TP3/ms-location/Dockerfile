FROM openjdk:17-jdk-alpine

VOLUME /tmp

RUN apk add --no-cache bash

COPY wait-for-it.sh /wait-for-it.sh
COPY target/*.jar app.jar

ENTRYPOINT ["/wait-for-it.sh", "user-db:3306", "--timeout=0", "--strict", "--", "java", "-jar", "/app.jar"]
