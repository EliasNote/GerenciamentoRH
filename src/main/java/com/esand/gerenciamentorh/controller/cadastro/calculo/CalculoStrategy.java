package com.esand.gerenciamentorh.controller.cadastro.calculo;

public interface CalculoStrategy {
    double calcular(double baseCalculo);
    double calcular(double baseCalculo, Integer hora, Integer minuto);
}
