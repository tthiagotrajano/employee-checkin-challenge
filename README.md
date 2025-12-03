Employee Checkin Challenge

Sistema de check-in e check-out de funcionários.

BACKEND (Java Spring Boot)

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

FRONTEND (React)

REQUISITOS:
- Node.js

EXECUÇÃO LOCAL:

1. Navegue até o diretório do frontend:
   cd employee-checkin-frontend

2. Instale as dependências:
   npm install

3. Rodar a aplicação:
   npm run dev

   A aplicação estará disponível em: http://localhost:5173

TESTE:

Usuários criados: manager@grupomoura.com, employee01@grupomoura.com e employee02@grupomoura.com

Senha padrão: 123
