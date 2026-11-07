# ---------- Etapa 1: build ----------
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copia primeiro só o necessário para baixar dependências (melhor cache de camadas)
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

# Copia o código-fonte e gera o jar
COPY src src
RUN ./mvnw -B package -DskipTests

# ---------- Etapa 2: runtime ----------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Executa como usuário sem privilégios
RUN useradd --system --uid 1001 spring
USER spring

COPY --from=build /app/target/*.jar app.jar

# O Render injeta a variável PORT; localmente cai no 8080
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java -XX:MaxRAMPercentage=75 -Dserver.port=${PORT} -jar /app/app.jar"]
