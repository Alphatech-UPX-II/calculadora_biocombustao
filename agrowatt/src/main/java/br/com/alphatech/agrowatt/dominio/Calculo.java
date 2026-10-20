package br.com.alphatech.agrowatt.dominio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "calculos")
public class Calculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calculo")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_residuo")
    private Residuo residuo;

    @Column(name = "massa_kg")
    private Double massaKg;

    @Column(name = "data_calculo")
    private LocalDateTime dataCalculo;

    @OneToOne(mappedBy = "calculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Resultado resultado;

    protected Calculo() {
    }

    public Calculo(Residuo residuo, double massaKg, LocalDateTime dataCalculo) {
        this.residuo = residuo;
        this.massaKg = massaKg;
        this.dataCalculo = dataCalculo;
    }

    public void registrarResultado(double massaStKg, double massaSvKg, double volumeMetanoM3,
                                   double volumeBiogasM3, double energiaKwh) {
        this.resultado = new Resultado(this, massaStKg, massaSvKg, volumeMetanoM3, volumeBiogasM3, energiaKwh);
    }

    public Integer getId() { return id; }
    public Residuo getResiduo() { return residuo; }
    public Double getMassaKg() { return massaKg; }
    public LocalDateTime getDataCalculo() { return dataCalculo; }
    public Resultado getResultado() { return resultado; }
}
