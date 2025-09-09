# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper files
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml files for all modules to leverage Docker cache
COPY pom.xml .
COPY crud-framework/pom.xml crud-framework/
COPY crud-framework/crud-abstraction/pom.xml crud-framework/crud-abstraction/
COPY crud-framework/people-crud-plugin/pom.xml crud-framework/people-crud-plugin/
COPY crud-framework/rest-abstraction/pom.xml crud-framework/rest-abstraction/
COPY plugin-host-app/pom.xml plugin-host-app/

# Copy the source code for all modules
COPY ./crud-framework/crud-abstraction/src ./crud-framework/crud-abstraction/src
COPY ./crud-framework/people-crud-plugin/src ./crud-framework/people-crud-plugin/src
COPY ./crud-framework/rest-abstraction/src ./crud-framework/rest-abstraction/src
COPY ./plugin-host-app/src ./plugin-host-app/src

# Build the application, skipping tests
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean install -DskipTests

# Expose the port the Spring Boot application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "plugin-host-app/target/plugin-host-app-0.0.1-SNAPSHOT.jar"]