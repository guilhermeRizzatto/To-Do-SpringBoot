# Usar uma imagem base com Java
FROM openjdk:17-jdk-slim
FROM maven:3.9.7-amazoncorretto-17 AS build
# Diretório de trabalho no container
WORKDIR /app

COPY src /app/src
COPY pom.xml /app

RUN mvn clean install

# Copiar o arquivo JAR da aplicação
COPY --from=build /app/target/Todo-1.1.jar /app/app.jar

# Porta que a aplicação vai expor (se necessário)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]