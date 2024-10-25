package com.esand.gerenciamentorh.entidades;

import java.util.List;

public class FolhaPagamento {
    private Funcionario funcionario;

    private Double salarioLiquido;

    private List<Beneficio> avaliacoes;

    public FolhaPagamento(Funcionario funcionario, Double salarioLiquido, List<Beneficio> avaliacoes) {
        this.funcionario = funcionario;
        this.salarioLiquido = salarioLiquido;
        this.avaliacoes = avaliacoes;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Double getSalarioLiquido() {
        return salarioLiquido;
    }

    public void setSalarioLiquido(Double salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }

    public List<Beneficio> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Beneficio> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}
