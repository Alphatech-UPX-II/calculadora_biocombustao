package br.com.alphatech.agrowatt.calculo;

/** Resultados de um mesmo período (dia ou mês). */
public record Grandezas(
        double massaStKg,
        double massaSvKg,
        double metanoM3,
        double biogasM3,
        double energiaKwh,
        double eletricaMinKwh,
        double eletricaMaxKwh,
        double dieselL,
        double co2eKg) {

    public Grandezas vezes(double fator) {
        return new Grandezas(massaStKg * fator, massaSvKg * fator, metanoM3 * fator, biogasM3 * fator,
                energiaKwh * fator, eletricaMinKwh * fator, eletricaMaxKwh * fator, dieselL * fator, co2eKg * fator);
    }
}
