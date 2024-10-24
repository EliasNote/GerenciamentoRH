package com.esand.gerenciamentorh.entity;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private Departamento departamento;
    private Double salario;
    private List<Beneficio> beneficios = new ArrayList<>();

    public Funcionario() {
    }

    public enum Departamento {
        RH,
        PRODUCAO,
        GERENCIA
    }

    public Funcionario(Long id, String nome, String sobrenome, Departamento departamento, String cpf, Double salario, List<Beneficio> beneficios) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.departamento = departamento;
        this.cpf = cpf;
        this.salario = salario;
        this.beneficios = beneficios;
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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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
}
