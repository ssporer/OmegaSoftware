FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18-jdk-slim
WORKDIR /app
COPY --from=build /app/target/OmegaSoftware-0.0.1-SNAPSHOT.jar app.jar

# Specify the port your app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
