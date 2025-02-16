package com.esand.gerenciamentorh.controller.util.calculo;

public interface CalculoStrategy {
    double calcular(double baseCalculo);
    double calcular(double baseCalculo, Integer hora, Integer minuto);
}
