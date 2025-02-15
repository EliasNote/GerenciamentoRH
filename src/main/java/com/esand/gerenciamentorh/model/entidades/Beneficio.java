package com.esand.gerenciamentorh.model.entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Beneficio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String tipo;
    private String descricao;
    private Double valor;

    @ManyToMany(mappedBy = "beneficios")
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Beneficio() {
    }

    public Beneficio(Long id, String tipo, String descricao, Double valor, List<Funcionario> funcionarios) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.funcionarios = funcionarios;
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

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
}
