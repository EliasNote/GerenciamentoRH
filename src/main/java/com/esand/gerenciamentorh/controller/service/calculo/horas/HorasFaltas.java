package com.esand.gerenciamentorh.controller.service.calculo.horas;

import com.esand.gerenciamentorh.controller.service.calculo.CalculoStrategy;

public class HorasFaltas implements CalculoStrategy {
    @Override
    public double calcular(double baseCalculo) {
        return 0;
    }

    @Override
    public double calcular(double baseCalculo, Integer hora, Integer minuto) {
        return (baseCalculo / 220) * hora + (minuto / 60.0);
    }
}
