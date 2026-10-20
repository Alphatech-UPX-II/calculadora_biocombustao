# AGROWATT — Do resíduo ao watt

Calculadora web do potencial energético (biogás) de resíduos agropecuários. UPX II, Centro Universitário Facens, 2026. ODS 7 e ODS 13.

O banco de dados `calculadora_biocombustao` segue a modelagem feita pelo grupo no MySQL Workbench (tabelas `residuos`, `calculos` e `resultados`), mais a tabela `premissas` com as constantes do cálculo.

## Pré-requisitos

- Java 21 ou superior
- MySQL 8 rodando em `localhost:3306` (MySQL Community Server; o MySQL Workbench é opcional)

## Executar com MySQL

```bash
# senha do usuário root do MySQL (Git Bash)
export DB_PASSWORD='sua-senha'
#    PowerShell: $env:DB_PASSWORD = 'sua-senha'

./mvnw spring-boot:run        # Windows CMD/PowerShell: .\mvnw.cmd spring-boot:run
```

O banco `calculadora_biocombustao` é criado automaticamente, e o Flyway cria as tabelas e os dados iniciais. Use um banco novo: se já existir um `calculadora_biocombustao` criado à mão no Workbench, apague-o ou aponte `DB_URL` para outro nome.

Abra http://localhost:8080.

## Executar sem MySQL (demonstração)

```bash
./mvnw spring-boot:test-run
```

Usa o banco H2 em memória. Os dados somem quando a aplicação é encerrada.

## Testes

```bash
./mvnw test
```

## Variáveis de ambiente

| Variável | Padrão |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/calculadora_biocombustao?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=America/Sao_Paulo` |
| `DB_USER` | `root` |
| `DB_PASSWORD` | vazio |

## Estrutura

| Pacote | Responsabilidade |
|---|---|
| `calculo` | fórmulas (Java puro, sem Spring) |
| `dominio` | entidades JPA das tabelas do banco |
| `repositorio` | acesso ao banco |
| `servico` | regras e orquestração |
| `web` | controllers e formulário |
