FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy entire parent project (needed for multi-module build)
COPY ../pom.xml ./pom.xml
COPY ../resume-core-service ./resume-core-service
COPY ../.mvn ./.mvn
COPY ../mvnw ./mvnw

# Give execute permission
RUN chmod +x mvnw

# Build only the core service module
RUN ./mvnw -pl resume-core-service -am clean package -DskipTests

# Run the jar
EXPOSE 8080
CMD ["java", "-jar", "resume-core-service/target/resume-core-service-1.0.0-SNAPSHOT.jar"]
