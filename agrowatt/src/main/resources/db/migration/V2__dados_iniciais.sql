-- Resíduos: dados do script 04_cadastro_parametros.sql do grupo.
-- ppm = potencial de produção de metano (L CH4/kg SV); pe = potencial energético (kWh/kg SV).
-- ST e SV são provisórios (20% e 80%) e serão substituídos na entrega final.
insert into residuos (nome, percentual_st, percentual_sv, rendimento_metano, percentual_metano, ppm, pe, referencia) values
('Frutas e vegetais',              20.00, 80.00, 0.4200, 60.00, 420.00, 4.19, 'Embrapa Documentos 195 (Leitão e Barcellos, 2021), Tab. 1: Bouallagui et al., 2005. ST e SV provisórios'),
('Restos de alimentos (cozinhas)', 20.00, 80.00, 0.5300, 60.00, 530.00, 5.29, 'Embrapa Documentos 195 (Leitão e Barcellos, 2021), Tab. 1: Zhang et al., 2014. ST e SV provisórios'),
('Resíduos domiciliares',          20.00, 80.00, 0.3500, 60.00, 350.00, 3.49, 'Embrapa Documentos 195 (Leitão e Barcellos, 2021), Tab. 1: Ferrer et al., 2011. ST e SV provisórios'),
('Esterco de gado',                20.00, 80.00, 0.1840, 60.00, 184.00, 1.83, 'Embrapa Documentos 195 (Leitão e Barcellos, 2021), Tab. 1: Merlin; Boileau, 2013. ST e SV provisórios'),
('Esterco de frango',              20.00, 80.00, 0.2590, 60.00, 259.00, 2.58, 'Embrapa Documentos 195 (Leitão e Barcellos, 2021), Tab. 1: Merlin; Boileau, 2013. ST e SV provisórios');

insert into premissas (chave, valor, descricao, fonte) values
('PCI_METANO_KWH_M3',       10.0, 'Poder calorífico inferior do metano (kWh por m³)',                  'GIZ/PROBIOGÁS (2015), Tab. 4.3, p. 66'),
('EFICIENCIA_ELETRICA_MIN', 0.25, 'Eficiência elétrica mínima do motogerador',                         'GIZ/PROBIOGÁS (2015), Tab. 9.3, p. 119'),
('EFICIENCIA_ELETRICA_MAX', 0.38, 'Eficiência elétrica máxima do motogerador',                         'GIZ/PROBIOGÁS (2015), Tab. 9.3, p. 119'),
('DIESEL_L_POR_M3_BIOGAS',  0.65, 'Litros de diesel equivalentes a 1 m³ de biogás com 60% de metano',  'Derivado de GIZ/PROBIOGÁS (2015), Tab. 4.3'),
('DENSIDADE_METANO_KG_M3',  0.67, 'Densidade do metano (kg por m³)',                                   'IPCC 2019, Vol. 4, Cap. 10, Eq. 10.23'),
('GWP_METANO',              27,   'Potencial de aquecimento global do metano em 100 anos (não fóssil)', 'IPCC AR6 (2021), Grupo de Trabalho I, Tab. 7.15'),
('DIAS_MES',                30,   'Dias considerados em um mês',                                       'Convenção do sistema');
