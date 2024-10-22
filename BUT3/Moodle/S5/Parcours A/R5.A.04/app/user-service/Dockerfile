FROM maven:amazoncorretto as stage1

# set working directory
WORKDIR /opt/demo

# copy project files
COPY . .

# compile the source code and package it in a jar file
RUN mvn clean package -Dmaven.test.skip=true

# the base image
FROM amazoncorretto:17-alpine-jdk

# the JAR file path
ARG JAR_FILE=target/*.jar

# Copy the JAR file from the build context into the Docker image
COPY --from=stage1 /opt/demo/${JAR_FILE} application.jar

# Set the default command to run the Java application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]