# Estágio de Build
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copia apenas os arquivos do wrapper primeiro para aproveitar o cache do Docker
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Dá permissão de execução para o wrapper
RUN chmod +x mvnw
# Baixa as dependências (ajuda a acelerar os builds futuros)
RUN ./mvnw dependency:go-offline

# Agora copia o código fonte e faz o build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
