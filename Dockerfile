
# Etapa 1: Build da aplicação
FROM maven:3.9.7-amazoncorretto-17 AS build

# Diretório de trabalho no estágio de build
WORKDIR /app

# Copiar arquivos do projeto
COPY pom.xml /app
COPY src /app/src

ARG DB.PASSWORD

# Configurar as variáveis no container
ENV DB.PASSWORD=${DB.PASSWORD}

# Executar o build do Maven
RUN mvn clean package -DskipTests

# Etapa 2: Imagem para execução
FROM openjdk:17-jdk-slim

# Diretório de trabalho no estágio final
WORKDIR /app

# Copiar o JAR gerado no estágio de build
COPY --from=build /app/target/ToDo-1.1.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
