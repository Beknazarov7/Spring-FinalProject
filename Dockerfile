# FROM maven:3.8.5-openjdk-17 AS build
# COPY . .
# RUN mvn clean package -DskipTests

# FROM openjdk:17.0.1-jdk-slim
# COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","demo.jar"]

# Use the Eclipse Temurin Alpine official image
# Use the Eclipse Temurin Alpine official image
FROM eclipse-temurin:21-jdk-alpine

# Install Maven
RUN apk add --no-cache maven

# Set working directory
WORKDIR /app

# Copy local code to the container image
COPY . ./

# Build the app
RUN mvn -B -DoutputFile=target/mvn-dependency-list.log -DskipTests clean dependency:list install

# Run the app
CMD ["sh", "-c", "java -jar target/*.jar"]