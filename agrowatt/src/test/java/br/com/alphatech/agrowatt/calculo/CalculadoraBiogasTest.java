package br.com.alphatech.agrowatt.calculo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;

class CalculadoraBiogasTest {

    static final Premissas PREMISSAS = new Premissas(10.0, 0.25, 0.38, 0.65, 0.67, 27, 30);
    static final ParametrosResiduo ESTERCO_DE_GADO = new ParametrosResiduo("Esterco de gado", 20, 80, 0.184, 60);
    static final ParametrosResiduo FRUTAS_E_VEGETAIS = new ParametrosResiduo("Frutas e vegetais", 20, 80, 0.42, 60);

    final CalculadoraBiogas calculadora = new CalculadoraBiogas(PREMISSAS);

    @Test
    void casoDeReferencia_milQuilosDeEstercoDeGado() {
        Grandezas dia = calculadora.calcular(ESTERCO_DE_GADO, 1000).dia();

        assertThat(dia.massaStKg()).isCloseTo(200.0, within(1e-9));
        assertThat(dia.massaSvKg()).isCloseTo(160.0, within(1e-9));
        assertThat(dia.metanoM3()).isCloseTo(29.44, within(1e-9));
        assertThat(dia.biogasM3()).isCloseTo(49.066667, within(1e-6));
        assertThat(dia.energiaKwh()).isCloseTo(294.4, within(1e-9));
        assertThat(dia.eletricaMinKwh()).isCloseTo(73.6, within(1e-9));
        assertThat(dia.eletricaMaxKwh()).isCloseTo(111.872, within(1e-9));
        assertThat(dia.dieselL()).isCloseTo(31.893333, within(1e-6));
        assertThat(dia.co2eKg()).isCloseTo(532.5696, within(1e-9));
    }

    @Test
    void frutasEVegetais() {
        assertThat(calculadora.calcular(FRUTAS_E_VEGETAIS, 1000).dia().metanoM3()).isCloseTo(67.2, within(1e-9));
    }

    @Test
    void mensalMultiplicaPorTrintaDias() {
        ResultadoCalculo r = calculadora.calcular(ESTERCO_DE_GADO, 1000);

        assertThat(r.mes().metanoM3()).isCloseTo(883.2, within(1e-9));
        assertThat(r.mes().massaStKg()).isCloseTo(6000.0, within(1e-9));
    }

    @Test
    void aPartirDoArmazenadoReconstroiAsGrandezasDerivadas() {
        ResultadoCalculo r = calculadora.aPartirDoArmazenado(200, 160, 29.44, 49.067, 294.4);

        assertThat(r.dia().eletricaMaxKwh()).isCloseTo(111.872, within(1e-9));
        assertThat(r.dia().co2eKg()).isCloseTo(532.5696, within(1e-9));
        assertThat(r.mes().energiaKwh()).isCloseTo(8832.0, within(1e-9));
    }

    @Test
    void massaNaoPositivaFalha() {
        assertThatThrownBy(() -> calculadora.calcular(ESTERCO_DE_GADO, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void percentualForaDaFaixaFalhaComNomeDoResiduo() {
        assertThatThrownBy(() -> new ParametrosResiduo("Resíduo X", 120, 80, 0.2, 60))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Resíduo X");
        assertThatThrownBy(() -> new ParametrosResiduo("Resíduo Y", 20, 80, 0.2, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rendimentoNaoPositivoFalha() {
        assertThatThrownBy(() -> new ParametrosResiduo("Resíduo Z", 20, 80, 0, 60))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
