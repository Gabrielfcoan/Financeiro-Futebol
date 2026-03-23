# Stage 1: Build (Maven builds Angular + Spring Boot)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY backend/ ./backend/
COPY frontend/ ./frontend/
WORKDIR /build/backend
RUN mvn package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /build/Discloud/futebol-backend.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
