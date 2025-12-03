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

DEPLOY NO RENDER:

- Tipo de serviço: Web Service
- Ambiente: Docker
- Docker Build Context Directory: employee-checkin-api/
- Dockerfile Path: employee-checkin-api/Dockerfile
- Health Check Path: /healthz
- Start Command: não é necessário (o CMD do Dockerfile já executa o JAR)
- Auto-Deploy: ativar conforme necessidade

FRONTEND (React ou Angular, conforme projeto)

REQUISITOS:
- Node.js 18+ (instale em https://nodejs.org)

EXECUÇÃO LOCAL:

1. Navegue até o diretório do frontend:
   cd employee-checkin-frontend  (substitua pelo nome do seu diretório front-end)

2. Instale as dependências:
   npm install

3. Rodar a aplicação:
   npm run dev

   A aplicação estará disponível em: http://localhost:5173

OBSERVAÇÕES:

- Certifique-se de que o backend esteja rodando antes de iniciar o frontend.
- Configure corretamente o CORS no backend para permitir requisições do frontend.
- O JAR gerado do backend é um Spring Boot Executable JAR, contendo todas as dependências.
- Para problemas de build ou deploy, verifique os logs do Maven, Docker e Node.js.

TESTE:

Usuários criados: manager@grupomoura.com, employee01@grupomoura.com e employee02@grupomoura.com

Senha padrão: 123
