package com.esand.gerenciamentorh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CalculoDto {
    private String campos;
    private String informado;
    private String proventos;
    private String descontos;
}
