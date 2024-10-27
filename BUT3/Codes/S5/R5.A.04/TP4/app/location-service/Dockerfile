FROM maven:amazoncorretto as stage1

# set working directory
WORKDIR /opt/demo

# copy project files
COPY . .

# compile the source code and package it in a jar file
RUN mvn clean package -Dmaven.test.skip=true

# the base image
FROM amazoncorretto:17-alpine-jdk

# Create a non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# the JAR file path
ARG JAR_FILE=target/*.jar

# Copy the JAR file from the build context into the Docker image
COPY --from=stage1 /opt/demo/${JAR_FILE} application.jar

# Change ownership of the application directory
RUN chown appuser /application.jar

# Switch to the non-root user
USER appuser

# Set the default command to run the Java application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]