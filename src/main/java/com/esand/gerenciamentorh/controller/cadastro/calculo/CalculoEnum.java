package com.esand.gerenciamentorh.controller.cadastro.calculo;

import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Fgts;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Inss;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Irpf;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CalculoEnum {
    INSS(new Inss()),
    IRPF(new Irpf()),
    FGTS(new Fgts());

    public final CalculoStrategy strategy;
}
