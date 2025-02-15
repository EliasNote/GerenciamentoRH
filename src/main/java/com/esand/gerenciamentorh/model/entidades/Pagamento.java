package com.esand.gerenciamentorh.model.entidades;

import jakarta.persistence.*;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private YearMonth competencia;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @ElementCollection
    private Map<String, Double> proventos = new HashMap<>();

    @ElementCollection
    private Map<String, Double> descontos = new HashMap<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;

    public Pagamento() {
    }

    public Pagamento(Long id, YearMonth competencia, Funcionario funcionario, Map<String, Double> proventos, Map<String, Double> descontos, Avaliacao avaliacao) {
        this.id = id;
        this.competencia = competencia;
        this.funcionario = funcionario;
        this.proventos = proventos;
        this.descontos = descontos;
        this.avaliacao = avaliacao;
    }

    public String getNome() {
        return funcionario.getNome();
    }

    public String getCpf() {
        return funcionario.getCpf();
    }

    public Double getSalarioBruto() {
        return funcionario.getSalario();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YearMonth getCompetencia() {
        return competencia;
    }

    public void setCompetencia(YearMonth competencia) {
        this.competencia = competencia;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Map<String, Double> getProventos() {
        return proventos;
    }

    public void setProventos(Map<String, Double> proventos) {
        this.proventos = proventos;
    }

    public Map<String, Double> getDescontos() {
        return descontos;
    }

    public void setDescontos(Map<String, Double> descontos) {
        this.descontos = descontos;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }
}