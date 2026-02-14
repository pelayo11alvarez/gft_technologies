# ==========================================
# STAGE 1: Building y Testing
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# 1. Only copy the POM first to take advantage of Docker's cache.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copy de source code
COPY src ./src

# 3. App package
RUN mvn clean package -B

# ==========================================
# STAGE 2: Runtime
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# We copy the JAR generated in Stage 1
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port.
EXPOSE 8080

# Command to start the app
ENTRYPOINT ["java", "-jar", "app.jar"]