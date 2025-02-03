package com.esand.gerenciamentorh.controller.cadastro.calculo.impostos;

import com.esand.gerenciamentorh.controller.cadastro.calculo.CalculoStrategy;

public class HorasExtras implements CalculoStrategy {
    @Override
    public double calcular(double baseCalculo) {
        return 0;
    }

    @Override
    public double calcular(double baseCalculo, Integer hora, Integer minuto) {
        return ((baseCalculo / 220) * hora + (minuto / 60.0)) * 1.5;
    }
}
