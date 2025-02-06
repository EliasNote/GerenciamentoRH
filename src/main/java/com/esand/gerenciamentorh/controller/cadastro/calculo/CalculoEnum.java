package com.esand.gerenciamentorh.controller.cadastro.calculo;

import com.esand.gerenciamentorh.controller.cadastro.calculo.horas.HorasExtras;
import com.esand.gerenciamentorh.controller.cadastro.calculo.horas.HorasFaltas;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

public enum CalculoEnum {
    INSS(new Inss()),
    IRPF(new Irpf()),
    FGTS(new Fgts()),
    HORAS_EXTRAS(new HorasExtras()),
    HORAS_FALTAS(new HorasFaltas());

    public final CalculoStrategy strategy;

    CalculoEnum(CalculoStrategy strategy) {
        this.strategy = strategy;
    }

    public CalculoStrategy getStrategy() {
        return strategy;
    }
}
