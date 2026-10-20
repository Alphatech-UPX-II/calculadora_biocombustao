package br.com.alphatech.agrowatt.calculo;

/** Constantes globais do cálculo. Os valores e as fontes vêm da tabela {@code premissas}. */
public record Premissas(
        double pciMetanoKwhM3,
        double eficienciaEletricaMin,
        double eficienciaEletricaMax,
        double dieselLPorM3Biogas,
        double densidadeMetanoKgM3,
        double gwpMetano,
        int diasMes) {
}
