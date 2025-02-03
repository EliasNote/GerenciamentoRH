package com.esand.gerenciamentorh.controller.cadastro.calculo.impostos;

import com.esand.gerenciamentorh.controller.cadastro.calculo.CalculoStrategy;

public class Inss implements CalculoStrategy {

    public static double[] faixas;
    public static double[] aliquotas;

    @Override
    public double calcular(double baseCalculo) {
        double total = 0.0;

        for (int i = 0; i < faixas.length; i += 2) {
            double faixaInferior = faixas[i];
            double faixaSuperior = faixas[i+1];
            double aliquota = aliquotas[i/2];
            double faixa = retornarFaixa(baseCalculo, faixaInferior, faixaSuperior);

            total += faixa == 0 ? 0 : (faixa - faixaInferior) * aliquota;
        }

        return total;
    }

    @Override
    public double calcular(double baseCalculo, Integer hora, Integer minuto) {
        return 0;
    }

    private double retornarFaixa(double baseCalculo, double valorInferior, double valorSuperior) {
        return baseCalculo >= valorSuperior ? valorSuperior : baseCalculo > valorInferior ? baseCalculo : 0;
    }
}
