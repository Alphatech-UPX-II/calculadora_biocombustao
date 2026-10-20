package br.com.alphatech.agrowatt.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "premissas")
public class Premissa {

    @Id
    private String chave;
    private Double valor;
    private String descricao;
    private String fonte;

    protected Premissa() {
    }

    public String getChave() { return chave; }
    public Double getValor() { return valor; }
    public String getDescricao() { return descricao; }
    public String getFonte() { return fonte; }
}
