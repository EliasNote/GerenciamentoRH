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
    private String salarioBruto;
    private String inss;
    private String irpf;
    private String fgts;
    private String salarioLiquido;
}
