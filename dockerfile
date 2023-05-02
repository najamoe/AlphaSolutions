FROM maven:3.8.4-jdk-11 AS build

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Download the Maven dependencies specified in the pom.xml file
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src/ ./src/

# Build the application
RUN mvn package

#This is our base-image that the application will run on, in our case java version 17
  FROM openjdk:17-jdk-alpine


   # Set the working directory to /app
   WORKDIR /app

   # Copy the built JAR file from the build stage to the final image
   COPY --from=build /app/target/my-app.jar .

   # Expose port 8080 for the application
   EXPOSE 8080

   # Set the default command to run the application
   CMD ["java", "-jar", "my-app.jar"]
       ## Set the default command to run the application
       #CMD ["java", "-jar", "/app/build/libs/myapp.jar"]va", "-jar", "myapp.jar"]