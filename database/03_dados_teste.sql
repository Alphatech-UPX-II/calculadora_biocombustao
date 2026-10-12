USE calculadora_biocombustao;

CREATE TABLE resultados (
    id_resultado INT AUTO_INCREMENT PRIMARY KEY,
    id_calculo INT NOT NULL UNIQUE,
    massa_st_kg DECIMAL(10,2) NOT NULL,
    massa_sv_kg DECIMAL(10,2) NOT NULL,
    volume_metano_m3 DECIMAL(10,4) NOT NULL,
    volume_biogas_m3 DECIMAL(10,4) NOT NULL,
    energia_kwh DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (id_calculo)
        REFERENCES calculos(id_calculo)
);

SHOW TABLES;

DESCRIBE resultados;
