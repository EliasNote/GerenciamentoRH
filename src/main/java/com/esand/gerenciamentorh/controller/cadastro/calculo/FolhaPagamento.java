package com.esand.gerenciamentorh.controller.cadastro.calculo;

import com.esand.gerenciamentorh.model.dto.CampoDto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FolhaPagamento {
    private final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
    private final List<CampoDto> itens;

    public FolhaPagamento(List<CampoDto> itens) {
        this.itens = itens;
    }

    public double getTotalProventos() {
        return itens.stream()
                .mapToDouble(item -> parse(item.getProventos()))
                .sum();
    }

    public double getTotalDescontos() {
        return itens.stream()
                .mapToDouble(item -> parse(item.getDescontos()))
                .sum();
    }

    private double parse(String valor) {
        try {
            return nf.parse(valor).doubleValue();
        } catch (Exception e) {
            System.out.println("Falha na conversão dos valores da folha de pagamento");
            return 0.0;
        }
    }
}
