package com.esand.gerenciamentorh.dto;

import lombok.Getter;

@Getter
public enum Campos {
    SALARIO_BRUTO("Salário Bruto"),
    HORAS_EXTRAS("Horas Extras"),
    HORAS_FALTAS("Horas Faltas");

    private final String descricao;

    Campos(String descricao) {
        this.descricao = descricao;
    }
}
