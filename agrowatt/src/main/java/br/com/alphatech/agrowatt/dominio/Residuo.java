package br.com.alphatech.agrowatt.dominio;

import br.com.alphatech.agrowatt.calculo.ParametrosResiduo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "residuos")
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_residuo")
    private Integer id;

    private String nome;

    @Column(name = "percentual_st")
    private Double percentualSt;

    @Column(name = "percentual_sv")
    private Double percentualSv;

    @Column(name = "rendimento_metano")
    private Double rendimentoMetano;

    @Column(name = "percentual_metano")
    private Double percentualMetano;

    /** Potencial de produção de metano (L CH4/kg SV). */
    private Double ppm;

    /** Potencial energético (kWh/kg SV). */
    private Double pe;

    private String referencia;

    protected Residuo() {
    }

    public ParametrosResiduo paraParametros() {
        return new ParametrosResiduo(nome, percentualSt, percentualSv, rendimentoMetano, percentualMetano);
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public Double getPercentualSt() { return percentualSt; }
    public Double getPercentualSv() { return percentualSv; }
    public Double getRendimentoMetano() { return rendimentoMetano; }
    public Double getPercentualMetano() { return percentualMetano; }
    public Double getPpm() { return ppm; }
    public Double getPe() { return pe; }
    public String getReferencia() { return referencia; }
}
