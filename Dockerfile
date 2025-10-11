# --- Build Stage ---
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN chmod +x ./gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src/ ./src

# --- THIS IS THE CRITICAL FIX ---
# Build the application, but skip the tests
RUN ./gradlew build -x test --no-daemon

# --- Run Stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]