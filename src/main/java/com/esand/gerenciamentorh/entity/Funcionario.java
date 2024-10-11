package com.esand.gerenciamentorh.entity;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private Double salario;
    private List<Beneficio> beneficios = new ArrayList<>();
    private List<Double> avaliacoes;

    public Funcionario() {
    }

    public Funcionario(Long id, String nome, String sobrenome, String cpf, Double salario, List<Beneficio> beneficios, List<Double> avaliacoes) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.salario = salario;
        this.beneficios = beneficios;
        this.avaliacoes = avaliacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public List<Beneficio> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<Beneficio> beneficios) {
        this.beneficios = beneficios;
    }

    public List<Double> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Double> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}
