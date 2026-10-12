USE calculadora_biocombustao;

INSERT INTO residuos (
    nome,
    percentual_st,
    percentual_sv,
    rendimento_metano,
    percentual_metano,
    ppm,
    pe,
    referencia
)
VALUES
(
    'Frutas e vegetais',
    20.00, 80.00, 0.4200, 60.00,
    420.00, 4.19,
    'Bouallagui et al., 2005'
),
(
    'Restos de alimentos (cozinhas)',
    20.00, 80.00, 0.5300, 60.00,
    530.00, 5.29,
    'Zhang et al., 2014'
),
(
    'Residuos domiciliares',
    20.00, 80.00, 0.3500, 60.00,
    350.00, 3.49,
    'Ferrer et al., 2011'
),
(
    'Esterco de gado',
    20.00, 80.00, 0.1840, 60.00,
    184.00, 1.83,
    'Merlin; Boileau, 2013'
),
(
    'Esterco de frango',
    20.00, 80.00, 0.2590, 60.00,
    259.00, 2.58,
    'Merlin; Boileau, 2013'
);
