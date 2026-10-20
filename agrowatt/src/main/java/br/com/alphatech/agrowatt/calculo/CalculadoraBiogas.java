package br.com.alphatech.agrowatt.calculo;

import java.util.Objects;

/**
 * Estima o potencial energético de um resíduo a partir da sua massa:
 * massa → sólidos totais → sólidos voláteis → metano → biogás → energia.
 */
public final class CalculadoraBiogas {

    private final Premissas premissas;

    public CalculadoraBiogas(Premissas premissas) {
        this.premissas = Objects.requireNonNull(premissas, "premissas");
    }

    public ResultadoCalculo calcular(ParametrosResiduo residuo, double massaKg) {
        Objects.requireNonNull(residuo, "residuo");
        if (massaKg <= 0) {
            throw new IllegalArgumentException("Massa deve ser maior que zero");
        }
        double massaSt = massaKg * residuo.percentualSt() / 100;
        double massaSv = massaSt * residuo.percentualSv() / 100;
        double metano = massaSv * residuo.rendimentoMetano();
        double biogas = metano / (residuo.percentualMetano() / 100);
        double energia = metano * premissas.pciMetanoKwhM3();
        return aPartirDoArmazenado(massaSt, massaSv, metano, biogas, energia);
    }

    /** Completa as grandezas derivadas a partir dos valores gravados na tabela {@code resultados}. */
    public ResultadoCalculo aPartirDoArmazenado(double massaStKg, double massaSvKg, double metanoM3,
                                                double biogasM3, double energiaKwh) {
        Grandezas dia = new Grandezas(
                massaStKg,
                massaSvKg,
                metanoM3,
                biogasM3,
                energiaKwh,
                energiaKwh * premissas.eficienciaEletricaMin(),
                energiaKwh * premissas.eficienciaEletricaMax(),
                biogasM3 * premissas.dieselLPorM3Biogas(),
                metanoM3 * premissas.densidadeMetanoKgM3() * premissas.gwpMetano());
        return new ResultadoCalculo(dia, dia.vezes(premissas.diasMes()));
    }
}
