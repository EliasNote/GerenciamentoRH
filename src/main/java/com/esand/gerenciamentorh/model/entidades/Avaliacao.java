package com.esand.gerenciamentorh.model.entidades;

import jakarta.persistence.*;

@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Double pontuacao;
    private String observacao;

    @OneToOne(mappedBy = "avaliacao")
    private Pagamento pagamento;

    public Avaliacao() {
    }

    public Avaliacao(Long id, Double pontuacao, String observacao, Pagamento pagamento) {
        this.id = id;
        this.pontuacao = pontuacao;
        this.observacao = observacao;
        this.pagamento = pagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
