package com.esand.gerenciamentorh.entidades;

import jakarta.persistence.Embeddable;

@Embeddable
public class Avaliacao {
    private Double pontuacao;
    private String comentarios;

    public Avaliacao(Double pontuacao, String comentarios) {
        this.pontuacao = pontuacao;
        this.comentarios = comentarios;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
