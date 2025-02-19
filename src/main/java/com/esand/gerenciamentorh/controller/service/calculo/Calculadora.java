package com.esand.gerenciamentorh.controller.service.calculo;

import java.util.LinkedHashMap;
import java.util.Map;

public class Calculadora {
    public static final CalculoEnum INSS = CalculoEnum.INSS;
    public static final CalculoEnum IRPF = CalculoEnum.IRPF;
    public static final CalculoEnum FGTS = CalculoEnum.FGTS;
    public static final CalculoEnum HORAS_EXTRAS = CalculoEnum.HORAS_EXTRAS;
    public static final CalculoEnum HORAS_FALTAS = CalculoEnum.HORAS_FALTAS;

    public Map<CalculoEnum, Double> calcularImpostos(double baseCalculo) {
        Map<CalculoEnum, Double> resultados = new LinkedHashMap<>();

        double inss = INSS.getStrategy().calcular(baseCalculo);
        resultados.put(INSS, inss);

        double irpf = IRPF.getStrategy().calcular(baseCalculo - inss);
        resultados.put(IRPF, irpf);

        double fgts = FGTS.getStrategy().calcular(baseCalculo);
        resultados.put(FGTS, fgts);

        return resultados;
    }

    public double calcularHorasExtras(double baseCalculo, int hora, int minuto) {
        return HORAS_EXTRAS.getStrategy().calcular(baseCalculo, hora, minuto);
    }

    public double calcularHorasFaltas(double baseCalculo, int hora, int minuto) {
        return HORAS_FALTAS.getStrategy().calcular(baseCalculo, hora, minuto);
    }
}
