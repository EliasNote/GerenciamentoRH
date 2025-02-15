package com.esand.gerenciamentorh.model.dto;

public class PagamentoDto {
    private String nome;
    private String cpf;
    private String salarioBruto;
    private String inss;
    private String irpf;
    private String fgts;
    private String salarioLiquido;

    public PagamentoDto() {
    }

    public PagamentoDto(String nome, String cpf, String salarioBruto, String inss, String irpf, String fgts, String salarioLiquido) {
        this.nome = nome;
        this.cpf = cpf;
        this.salarioBruto = salarioBruto;
        this.inss = inss;
        this.irpf = irpf;
        this.fgts = fgts;
        this.salarioLiquido = salarioLiquido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(String salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public String getInss() {
        return inss;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    public String getIrpf() {
        return irpf;
    }

    public void setIrpf(String irpf) {
        this.irpf = irpf;
    }

    public String getFgts() {
        return fgts;
    }

    public void setFgts(String fgts) {
        this.fgts = fgts;
    }

    public String getSalarioLiquido() {
        return salarioLiquido;
    }

    public void setSalarioLiquido(String salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }
}
