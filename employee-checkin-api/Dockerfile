# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn -B clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/employee-checkin-0.0.1-SNAPSHOT.jar app.jar

# Expõe porta
EXPOSE 8080

# Executa o JAR correto
CMD ["java", "-jar", "app.jar"]
