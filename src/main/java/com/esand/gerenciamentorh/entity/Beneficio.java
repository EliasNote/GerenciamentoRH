package com.esand.gerenciamentorh.entity;

public class Beneficio {
    private Long id;
    private String nome;
    private Double valor;

    public Beneficio(String nome, Double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public Beneficio() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
