Employee Checkin Challenge

Sistema backend de check-in e check-out de funcionários usando Java Spring Boot.

REQUISITOS:
- Java 21
- Maven 3.9+
- Docker
- Banco de dados PostgreSQL (ou outro configurado no application.yaml)

BUILD E EXECUÇÃO LOCAL:

1. Navegue até o diretório do backend:
   cd employee-checkin-api

2. Build com Maven:
   mvn clean package -DskipTests

   O JAR será gerado em:
   target/employee-checkin-0.0.1-SNAPSHOT.jar

3. Executar o backend:
   java -jar target/employee-checkin-0.0.1-SNAPSHOT.jar

   A aplicação estará disponível em: http://localhost:8080

BUILD E EXECUÇÃO COM DOCKER:

1. Build da imagem Docker:
   docker build -t employee-checkin:0.0.1-SNAPSHOT .

2. Executar o container:
   docker run -p 8080:8080 employee-checkin:0.0.1-SNAPSHOT

   A aplicação estará disponível em: http://localhost:8080

DEPLOY NO RENDER:

- Tipo de serviço: Web Service
- Ambiente: Docker
- Docker Build Context Directory: employee-checkin-api/
- Dockerfile Path: employee-checkin-api/Dockerfile
- Health Check Path: /healthz
- Start Command: não é necessário (o CMD do Dockerfile já executa o JAR)
- Auto-Deploy: ativar conforme necessidade

OBSERVAÇÕES:

- Certifique-se de que o banco de dados esteja acessível e configurado corretamente.
- O JAR gerado é um Spring Boot Executable JAR, contendo todas as dependências.
- Para problemas de build ou deploy, verifique os logs do Maven e Docker.
