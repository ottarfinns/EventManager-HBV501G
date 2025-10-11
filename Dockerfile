# ===== Build stage (OpenJDK 24) =====
FROM eclipse-temurin:24-jdk AS build
WORKDIR /app

# Copy sources
COPY . .

# Install Maven (if your repo doesn’t include the Maven Wrapper)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Build the jar (skip tests for faster CI)
RUN mvn -q clean package -DskipTests

# ===== Runtime stage (OpenJDK 24) =====
FROM eclipse-temurin:24-jdk
WORKDIR /app

# Copy the built jar (handles any artifact name)
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar

# Render/Heroku-style platforms set $PORT; fall back to 8080 locally
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar app.jar"]
