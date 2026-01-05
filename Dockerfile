# ====== STAGE 1: build con Maven ======
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos pom + código
COPY pom.xml .
COPY src ./src

# Compilar (salida en target/)
RUN mvn clean package -DskipTests

# ====== STAGE 2: imagen liviana de runtime ======
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiamos el JAR generado desde el stage de build
COPY --from=build /app/target/pdf_reader-0.0.1-SNAPSHOT.jar app.jar

# Render inyecta PORT; Spring lo va a usar
ENV PORT=10000
EXPOSE 10000

# Spring Boot leerá server.port=${PORT:8080}
ENTRYPOINT ["java", "-jar", "app.jar"]
