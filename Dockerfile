FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy Maven wrapper and parent pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give permission
RUN chmod +x mvnw

# Copy module
COPY resume-core-service resume-core-service

# Build only the required module
RUN ./mvnw -pl resume-core-service -am clean package -DskipTests

# Run the app
EXPOSE 8080
CMD ["java", "-jar", "resume-core-service/target/resume-core-service-1.0.0-SNAPSHOT.jar"]
