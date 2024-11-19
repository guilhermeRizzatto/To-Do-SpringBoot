# Usar uma imagem base com Java
FROM openjdk:17-jdk-slim

# Diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo JAR da aplicação
COPY target/ToDo-1.1.jar app.jar

# Porta que a aplicação vai expor (se necessário)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]