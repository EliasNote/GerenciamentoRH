package com.esand.gerenciamentorh.controller.cadastro.calculo.impostos;

import com.esand.gerenciamentorh.controller.cadastro.calculo.CalculoStrategy;

public class Fgts implements CalculoStrategy {

    public static double aliquota;

    @Override
    public double calcular(double baseCalculo) {
        return baseCalculo * aliquota;
    }

    @Override
    public double calcular(double baseCalculo, Integer hora, Integer minuto) {
        return 0;
    }
}
