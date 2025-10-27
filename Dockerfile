# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-21 AS builder
LABEL authors="JOSELINE"

WORKDIR /app

# Copia primero solo pom.xml para aprovechar cache de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Luego copia el resto del código
COPY src ./src

# Compila con encoding explícito
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"
RUN mvn clean package -DskipTests -B

# Etapa de ejecución
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]