package com.esand.gerenciamentorh.controller.service.calculo.impostos;

import com.esand.gerenciamentorh.controller.service.calculo.CalculoStrategy;

public class Irpf implements CalculoStrategy {

    public static double[] faixas;
    public static double[] aliquotas;
    public static double[] parcelasDeducao;

    @Override
    public double calcular(double baseCalculo) {
        for (int i = 0; i < faixas.length; i += 2) {
            double faixaInferior = faixas[i];
            double faixaSuperior = faixas[i+1];
            double aliquota = aliquotas[i/2];
            double parcelaDeduzir = parcelasDeducao[i/2];

            if (baseCalculo >= faixaInferior && baseCalculo <= faixaSuperior) {
                return baseCalculo * aliquota - parcelaDeduzir;
            }
        }

        return baseCalculo * aliquotas[aliquotas.length-1] - parcelasDeducao [parcelasDeducao .length-1];
    }

    @Override
    public double calcular(double baseCalculo, Integer hora, Integer minuto) {
        return 0;
    }
}
