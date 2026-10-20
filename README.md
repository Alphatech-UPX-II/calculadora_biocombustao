# Calculadora de Biocombustão

## Sobre o projeto
Projeto acadêmico desenvolvido na Facens com o objetivo de estimar o potencial de produção de biogás, metano e energia a partir de resíduos orgânicos, com foco na biodigestão anaeróbia.

## Objetivos
- Calcular a massa de sólidos totais (ST) e sólidos voláteis (SV).
- Estimar o volume de metano e biogás produzido.
- Calcular o potencial energético estimado.
- Armazenar os cálculos e resultados para consultas futuras.

## Tecnologias utilizadas
- **Java 21 + Spring Boot:** aplicação web (acessada pelo navegador).
- **Thymeleaf + Bootstrap:** telas da aplicação.
- **MySQL:** banco de dados `calculadora_biocombustao`.
- **MySQL Workbench:** modelagem e conferência do banco.
- **Spring Data JPA + Flyway:** acesso ao banco e criação automática das tabelas.
- **JUnit 5:** testes automatizados.

## Estrutura do repositório
| Pasta | Conteúdo |
|---|---|
| `database/` | Scripts SQL do banco, criados no MySQL Workbench |
| `agrowatt/` | Aplicação Java (código, telas, testes e migrações do Flyway) |

## Como executar
Instruções completas em [`agrowatt/README.md`](agrowatt/README.md). Em resumo, dentro da pasta `agrowatt`:

```bash
./mvnw spring-boot:test-run   # demonstração, sem precisar de MySQL
./mvnw spring-boot:run        # com MySQL (informe a senha em DB_PASSWORD)
./mvnw test                   # testes automatizados
```

Abra http://localhost:8080.

## Banco de dados
O banco `calculadora_biocombustao` possui três tabelas principais:

- `residuos`: armazena os tipos de resíduos e seus parâmetros.
- `calculos`: registra os dados de entrada de cada simulação.
- `resultados`: armazena os resultados calculados.

Na aplicação Java, foi acrescentada a tabela `premissas`, com as constantes do cálculo (poder calorífico do metano, eficiência do gerador etc.) e suas fontes. As tabelas são criadas pelo Flyway a partir dos scripts em `agrowatt/src/main/resources/db/migration/`, que seguem os scripts da pasta `database/`.

## Sustentabilidade
O projeto está relacionado aos Objetivos de Desenvolvimento Sustentável:

- **ODS 7:** Energia Limpa e Acessível.
- **ODS 13:** Ação Contra a Mudança Global do Clima.

## Observação
Os resultados são estimativas baseadas nos parâmetros cadastrados. Os dados técnicos e suas referências devem ser validados antes de serem utilizados como valores definitivos.

---
**Projeto acadêmico — Facens | Análise e Desenvolvimento de Sistemas**
