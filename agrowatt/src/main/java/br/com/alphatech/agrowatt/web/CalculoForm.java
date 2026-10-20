package br.com.alphatech.agrowatt.web;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/** Dados da tela de novo cálculo. A massa chega como texto para aceitar o padrão brasileiro. */
public class CalculoForm {

    static final double MASSA_MINIMA_KG = 0.01;
    /** Limitado pela coluna volume_biogas_m3 DECIMAL(10,4) do banco (até 999.999,9999 m³). */
    static final double MASSA_MAXIMA_KG = 1_000_000;

    /** Ponto como separador de milhar no padrão brasileiro: "1.000", "12.345.678". */
    private static final java.util.regex.Pattern MILHAR_COM_PONTO =
            java.util.regex.Pattern.compile("\\d{1,3}(\\.\\d{3})+");

    @NotNull(message = "Escolha um resíduo da lista")
    private Integer idResiduo;

    @NotBlank(message = "Informe a massa do resíduo")
    private String massa;

    @Pattern(regexp = "kg|t", message = "Unidade inválida")
    private String unidade = "kg";

    /** Massa convertida para quilos; null se o texto não for número. */
    public Double massaEmKg() {
        Double valor = converter(massa);
        if (valor == null) {
            return null;
        }
        return "t".equals(unidade) ? valor * 1000 : valor;
    }

    @AssertTrue(message = "A massa deve ser um número entre 0,01 kg e 1.000.000 kg")
    public boolean isMassaValida() {
        if (massa == null || massa.isBlank()) {
            return true;
        }
        Double kg = massaEmKg();
        return kg != null && kg >= MASSA_MINIMA_KG && kg <= MASSA_MAXIMA_KG;
    }

    /** Converte "1.234,5", "1,5", "1.000" ou "2.5" em número. Retorna null se não for número. */
    static Double converter(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }
        String normalizado = texto.trim();
        if (normalizado.contains(",")) {
            normalizado = normalizado.replace(".", "").replace(',', '.');
        } else if (MILHAR_COM_PONTO.matcher(normalizado).matches()) {
            normalizado = normalizado.replace(".", "");
        }
        try {
            double valor = Double.parseDouble(normalizado);
            return Double.isFinite(valor) ? valor : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getIdResiduo() { return idResiduo; }
    public void setIdResiduo(Integer idResiduo) { this.idResiduo = idResiduo; }
    public String getMassa() { return massa; }
    public void setMassa(String massa) { this.massa = massa; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
}
