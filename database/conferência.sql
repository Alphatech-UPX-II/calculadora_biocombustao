USE calculadora_biocombustao;

-- Estrutura das tabelas
DESCRIBE residuos;
DESCRIBE calculos;
DESCRIBE resultados;

-- Residuos disponiveis para a calculadora
SELECT
    id_residuo,
    nome,
    percentual_st,
    percentual_sv,
    rendimento_metano,
    percentual_metano
FROM residuos;

-- Historico dos calculos
SELECT
    c.id_calculo,
    c.data_calculo,
    r.nome AS residuo,
    c.massa_kg,
    res.volume_metano_m3,
    res.volume_biogas_m3,
    res.energia_kwh
FROM calculos c
JOIN residuos r
    ON c.id_residuo = r.id_residuo
JOIN resultados res
    ON c.id_calculo = res.id_calculo
ORDER BY c.id_calculo DESC;

