package br.com.alphatech.agrowatt.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultados")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado")
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_calculo")
    private Calculo calculo;

    @Column(name = "massa_st_kg")
    private Double massaStKg;

    @Column(name = "massa_sv_kg")
    private Double massaSvKg;

    @Column(name = "volume_metano_m3")
    private Double volumeMetanoM3;

    @Column(name = "volume_biogas_m3")
    private Double volumeBiogasM3;

    @Column(name = "energia_kwh")
    private Double energiaKwh;

    protected Resultado() {
    }

    Resultado(Calculo calculo, double massaStKg, double massaSvKg, double volumeMetanoM3,
              double volumeBiogasM3, double energiaKwh) {
        this.calculo = calculo;
        this.massaStKg = massaStKg;
        this.massaSvKg = massaSvKg;
        this.volumeMetanoM3 = volumeMetanoM3;
        this.volumeBiogasM3 = volumeBiogasM3;
        this.energiaKwh = energiaKwh;
    }

    public Integer getId() { return id; }
    public Double getMassaStKg() { return massaStKg; }
    public Double getMassaSvKg() { return massaSvKg; }
    public Double getVolumeMetanoM3() { return volumeMetanoM3; }
    public Double getVolumeBiogasM3() { return volumeBiogasM3; }
    public Double getEnergiaKwh() { return energiaKwh; }
}
