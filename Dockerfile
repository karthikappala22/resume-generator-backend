FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy entire project
COPY pom.xml .
COPY resume-core-service resume-core-service

# Build only core service
RUN mvn -pl resume-core-service -am clean package -DskipTests

# ---------- runtime image ----------
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/resume-core-service/target/resume-core-service-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
