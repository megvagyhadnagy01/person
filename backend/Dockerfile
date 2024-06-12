# Use Maven to build the application with OpenJDK 17
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use a smaller base image for the final image
FROM eclipse-temurin:17-jre-focal

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
