package com.esand.gerenciamentorh.controller.cadastro.impostos;

public class Impostos {
    private static double[] faixasInss = {0, 1412.00, 1412.01, 2666.68, 2666.69, 4000.03, 4000.04, 7786.02};
    private static double[] aliquotasInss = {0.075, 0.09, 0.12, 0.14};

    private static double[] faixasIrpf = {0, 2259.20, 2259.21, 2826.65, 2826.66, 3751.05, 3751.06, 4664.68, 4664.69, 0};
    private static double[] aliquotasIrpf = {0, 0.075, 0.15, 0.225, 0.275};
    private static double[] parcelasDeducaoIrpf = {0, 169.44, 381.44, 662.77, 896.00};

    public static double calcularInss(double baseCalculo) {
        double total = 0.0;

        for (int i = 0; i < faixasInss.length; i += 2) {
            double faixaInferior = faixasInss[i];
            double faixaSuperior = faixasInss[i+1];
            double aliquota = aliquotasInss[i/2];
            double faixa = retornarFaixaInss(baseCalculo, faixaInferior, faixaSuperior);

            total += faixa == 0 ? 0 : (faixa - faixaInferior) * aliquota;
        }

        return total;
    }

    public static double retornarFaixaInss(double baseCalculo, double valorInferior, double valorSuperior) {
        return baseCalculo >= valorSuperior ? valorSuperior : baseCalculo > valorInferior ? baseCalculo : 0;
    }

    public static double calcularIrpf(double baseCalculo) {
        for (int i = 0; i < faixasIrpf.length; i += 2) {
            double faixaInferior = faixasIrpf[i];
            double faixaSuperior = faixasIrpf[i+1];
            double aliquota = aliquotasIrpf[i/2];
            double parcelaDeduzir = parcelasDeducaoIrpf[i/2];

            if (baseCalculo >= faixaInferior && baseCalculo <= faixaSuperior) {
                return baseCalculo * aliquota - parcelaDeduzir;
            }
        }

        return baseCalculo * aliquotasIrpf[aliquotasIrpf.length-1] - parcelasDeducaoIrpf [parcelasDeducaoIrpf .length-1];
    }
}
