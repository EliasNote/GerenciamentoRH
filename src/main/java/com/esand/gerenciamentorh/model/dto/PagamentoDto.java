package com.esand.gerenciamentorh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PagamentoDto {
    private String nome;
    private String cpf;
    private double salarioBruto;
    private double inss;
    private double irpf;
    private double fgts;
    private double salarioLiquido;
}
