package com.esand.gerenciamentorh.controller.cadastro.calculo;

import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Fgts;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Inss;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Irpf;

public enum CalculoEnum {
    INSS(new Inss()),
    IRPF(new Irpf()),
    FGTS(new Fgts());

    public CalculoStrategy strategy;

    CalculoEnum(CalculoStrategy calculoStrategy) {
        this.strategy = calculoStrategy;
    }

    public CalculoStrategy getStrategy() {
        return strategy;
    }
}
