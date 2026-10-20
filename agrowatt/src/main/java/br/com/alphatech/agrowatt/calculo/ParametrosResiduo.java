package br.com.alphatech.agrowatt.calculo;

/** Parâmetros de um resíduo, como cadastrados na tabela {@code residuos}. Percentuais de 0 a 100. */
public record ParametrosResiduo(
        String nome,
        double percentualSt,
        double percentualSv,
        double rendimentoMetano,
        double percentualMetano) {

    public ParametrosResiduo {
        exigirPercentual(nome, "ST", percentualSt);
        exigirPercentual(nome, "SV", percentualSv);
        exigirPercentual(nome, "metano", percentualMetano);
        if (rendimentoMetano <= 0) {
            throw new IllegalArgumentException("Rendimento de metano deve ser positivo: " + nome);
        }
    }

    private static void exigirPercentual(String nome, String rotulo, double valor) {
        if (valor <= 0 || valor > 100) {
            throw new IllegalArgumentException("Percentual de " + rotulo + " fora da faixa (0 a 100]: " + nome);
        }
    }
}
