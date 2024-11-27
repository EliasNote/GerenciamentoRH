package com.esand.gerenciamentorh.dto;

import com.esand.gerenciamentorh.entidades.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BeneficioDto {
    private String tipo;
    private String descricao;
    private Double valor;
}
