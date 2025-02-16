package com.esand.gerenciamentorh.controller.service;

import com.esand.gerenciamentorh.controller.util.calculo.CalculoEnum;
import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.dto.CalculoDto;
import com.esand.gerenciamentorh.model.entidades.Avaliacao;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import com.esand.gerenciamentorh.model.entidades.Pagamento;

import java.text.NumberFormat;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PagamentoService {
    private final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
    private final Dao<Pagamento> pagamentoDao = new Dao<>();
    private List<CalculoDto> itens;

    public void setItens(List<CalculoDto> itens) {
        this.itens = itens;
    }

    public Pagamento criarPagamento(Funcionario funcionario, YearMonth competencia,
                                    Map<String, Double> proventos, Map<String, Double> descontos,
                                    Avaliacao avaliacao) {
        Pagamento pagamento = pagamentoDao.buscarPorCpfECompetencia(funcionario.getCpf(), competencia);

        if (pagamento == null) {
            pagamento = new Pagamento();
            pagamento.setFuncionario(funcionario);
        }

        pagamento.setCompetencia(competencia);
        pagamento.setProventos(proventos);
        pagamento.setDescontos(descontos);
        pagamento.setAvaliacao(avaliacao);

        return pagamento;
    }

    public boolean salvarPagamento(Pagamento pagamento) {
        try {
            if (pagamento.getAvaliacao() != null) {
                pagamento.getAvaliacao().setPagamento(pagamento);
            } else {
                pagamento.setAvaliacao(null);
            }

            if (pagamento.getId() == null) {
                pagamentoDao.salvar(pagamento);
            } else {
                pagamentoDao.atualizar(pagamento);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Pagamento buscarPorCpfECompetencia(String cpf, YearMonth competencia) {
        return pagamentoDao.buscarPorCpfECompetencia(cpf, competencia);
    }

    public List<Pagamento> buscarPorCompetencia(YearMonth competencia) {
        return pagamentoDao.buscarPorCompetencia(competencia);
    }

    public void deletar(String cpf, YearMonth competencia) {
        pagamentoDao.deletar(cpf, competencia);
    }

    public Double getInss(Pagamento pagamento) {
        return pagamento.getDescontos().getOrDefault(CalculoEnum.INSS.toString(), 0.0);
    }

    public Double getFgts(Pagamento pagamento) {
        return pagamento.getDescontos().getOrDefault(CalculoEnum.FGTS.toString(), 0.0);
    }

    public Double getIrpf(Pagamento pagamento) {
        return pagamento.getDescontos().getOrDefault(CalculoEnum.IRPF.toString(), 0.0);
    }

    public Double getSalarioLiquido(Pagamento pagamento) {
        double totalProventos = pagamento.getProventos().values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        double totalDescontos = pagamento.getDescontos().entrySet().stream()
                .filter(entry -> !entry.getKey().equals(CalculoEnum.FGTS.toString()))
                .mapToDouble(Map.Entry::getValue)
                .sum();

        return totalProventos - totalDescontos;
    }

    public double getTotalProventos() {
        return itens.stream()
                .mapToDouble(item -> parse(item.getProventos()))
                .sum();
    }

    public double getTotalDescontos() {
        return itens.stream()
                .filter(item -> !item.getCampos().equals(CalculoEnum.FGTS.toString()))
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
