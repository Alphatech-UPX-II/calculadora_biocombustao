USE calculadora_biocombustao;

CREATE TABLE calculos (
    id_calculo INT AUTO_INCREMENT PRIMARY KEY,
    id_residuo INT NOT NULL,
    massa_kg DECIMAL(10,2) NOT NULL,
    data_calculo DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_residuo)
        REFERENCES residuos(id_residuo)
);

SHOW TABLES;

DESCRIBE calculos;

SHOW CREATE TABLE calculos;

