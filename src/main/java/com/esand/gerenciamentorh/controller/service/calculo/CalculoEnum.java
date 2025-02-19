package com.esand.gerenciamentorh.controller.service.calculo;

import com.esand.gerenciamentorh.controller.service.calculo.horas.HorasExtras;
import com.esand.gerenciamentorh.controller.service.calculo.horas.HorasFaltas;
import com.esand.gerenciamentorh.controller.service.calculo.impostos.*;

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
