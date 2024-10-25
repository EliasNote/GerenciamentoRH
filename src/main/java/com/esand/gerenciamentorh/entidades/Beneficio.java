package com.esand.gerenciamentorh.entidades;

public class Beneficio {
    private Long id;
    private String tipo;
    private String descricao;
    private Double valor;

    public Beneficio(Long id, String tipo, String descricao, Double valor) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
