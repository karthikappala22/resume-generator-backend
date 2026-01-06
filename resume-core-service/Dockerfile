FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy Maven wrapper and give execute permission
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

# Copy pom and download dependencies
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Copy source and build
COPY src src
RUN ./mvnw package -DskipTests

# Run app
EXPOSE 8080
CMD ["java", "-jar", "target/resume-core-service-1.0.0-SNAPSHOT.jar"]
