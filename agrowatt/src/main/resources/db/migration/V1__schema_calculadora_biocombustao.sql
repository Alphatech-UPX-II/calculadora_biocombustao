-- Estrutura do banco calculadora_biocombustao, conforme os scripts do grupo
-- (UPX_Biocombustao: 01_criacao_banco, 02_criacao_tabelas, 03_dados_teste),
-- acrescida da tabela premissas, que guarda as constantes do cálculo.

create table residuos (
    id_residuo        int auto_increment primary key,
    nome              varchar(100)  not null,
    percentual_st     decimal(5,2)  not null,
    percentual_sv     decimal(5,2)  not null,
    rendimento_metano decimal(10,4) not null,
    percentual_metano decimal(5,2)  not null,
    ppm               decimal(10,2),
    pe                decimal(10,2),
    referencia        varchar(255)
);

create table calculos (
    id_calculo   int auto_increment primary key,
    id_residuo   int           not null,
    massa_kg     decimal(10,2) not null,
    data_calculo datetime      default current_timestamp,
    foreign key (id_residuo) references residuos (id_residuo)
);

create table resultados (
    id_resultado     int auto_increment primary key,
    id_calculo       int           not null unique,
    massa_st_kg      decimal(10,2) not null,
    massa_sv_kg      decimal(10,2) not null,
    volume_metano_m3 decimal(10,4) not null,
    volume_biogas_m3 decimal(10,4) not null,
    energia_kwh      decimal(10,2) not null,
    foreign key (id_calculo) references calculos (id_calculo)
);

create table premissas (
    chave     varchar(60)   primary key,
    valor     decimal(12,6) not null,
    descricao varchar(200)  not null,
    fonte     varchar(300)  not null
);
