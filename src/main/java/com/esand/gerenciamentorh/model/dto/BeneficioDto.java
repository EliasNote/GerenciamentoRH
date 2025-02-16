package com.esand.gerenciamentorh.model.dto;

public class BeneficioDto {
    private String tipo;
    private String valor;
    private String descricao;

    public BeneficioDto() {
    }

    public BeneficioDto(String tipo, String valor, String descricao) {
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
