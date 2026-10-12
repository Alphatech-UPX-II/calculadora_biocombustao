CREATE DATABASE calculadora_biocombustao;

-- Etapa 1 — Criar o banco de dados
-- Um banco de dados é um espaço lógico que reúne as tabelas de um projeto.
-- Para a nossa aplicação, usaremos o nome calculadora_biocombustao.

-- Explicando cada parte
-- Comando						Significado
-- CREATE						Criar algo novo.
-- DATABASE						Indica que vamos criar um banco de dados.
-- calculadora_biocombustao		Nome escolhido para o banco.
-- ;							Indica o final do comando SQL.

USE calculadora_biocombustao;

-- O que significa USE?
-- É o comando que informa ao MySQL qual banco será utilizado como contexto para os próximos comandos.
-- magine que você tem vários armários: primeiro criou o armário da calculadora; agora está dizendo que vai trabalhar dentro dele.

CREATE TABLE residuos (
    id_residuo INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    percentual_st DECIMAL(5,2) NOT NULL,
    percentual_sv DECIMAL(5,2) NOT NULL,
    rendimento_metano DECIMAL(10,4) NOT NULL,
    percentual_metano DECIMAL(5,2) NOT NULL
    ADD COLUMN ppm DECIMAL(10,2),
ADD COLUMN pe DECIMAL(10,2);
);

-- 1. CREATE TABLE residuos
-- Cria uma tabela com o nome residuos. É nela que cadastraremos as matérias-primas.

-- 2. id_residuo INT AUTO_INCREMENT PRIMARY KEY
-- Essa linha cria um identificador único para cada resíduo.

-- INT: o identificador será um número inteiro.

-- AUTO_INCREMENT: o MySQL gera o próximo número automaticamente.

-- PRIMARY KEY: define a chave primária, que identifica cada registro.
-- Assim, o primeiro resíduo pode receber o ID 1, e o segundo, o ID 2.

-- 3. nome VARCHAR(100) NOT NULL
-- Armazena o nome do resíduo.
-- VARCHAR(100): permite texto de até 100 caracteres.
-- NOT NULL: impede que o campo fique sem valor.

-- 4. percentual_st DECIMAL(5,2) NOT NULL
-- Armazena o percentual de sólidos totais na massa úmida.
-- Por exemplo, 20% de sólidos totais significa que, em 100 kg de material úmido, existem 20 kg de sólidos totais.

-- 5. percentual_sv DECIMAL(5,2) NOT NULL
-- Armazena o percentual de sólidos voláteis em relação aos sólidos totais. Essa fração é importante para estimar a quantidade de matéria orgânica potencialmente biodegradável.

-- 6. rendimento_metano DECIMAL(10,4) NOT NULL
-- Armazena o rendimento específico de metano. Para o modelo proposto, a unidade será m³ de CH₄ por kg de sólidos voláteis.
-- O valor precisa ser obtido de uma fonte técnica adequada ao resíduo e às condições consideradas.

-- 7. percentual_metano DECIMAL(5,2) NOT NULL
-- Armazena a porcentagem de metano no biogás. Esse parâmetro permite estimar o volume total de biogás a partir do volume de metano.

SHOW TABLES;

DESCRIBE residuos;
