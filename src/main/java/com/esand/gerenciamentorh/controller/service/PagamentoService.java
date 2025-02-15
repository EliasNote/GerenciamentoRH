package com.esand.gerenciamentorh.controller.service;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Avaliacao;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import com.esand.gerenciamentorh.model.entidades.Pagamento;

import java.time.YearMonth;
import java.util.List;

public class PagamentoService {
    private final Dao<Pagamento> pagamentoDao = new Dao<>();
    private final Dao<Avaliacao> avaliacaoDao = new Dao<>();

    public Pagamento criarPagamento(Funcionario funcionario, YearMonth competencia, Double salarioLiquido,
                                    Integer horasExtra, Integer minutosExtra,
                                    Integer horasFalta, Integer minutosFalta,
                                    Double inss, Double irpf, Double fgts, Avaliacao avaliacao) {
        Pagamento pagamento = pagamentoDao.buscarPorFuncionarioECompetencia(funcionario.getCpf(), competencia);

        if (pagamento == null) {
            pagamento = new Pagamento();
            pagamento.setFuncionario(funcionario);
        }

        pagamento.setCompetencia(competencia);
        pagamento.setSalarioLiquido(salarioLiquido);
        pagamento.setHorasExtras(horasExtra + minutosExtra/60.0);
        pagamento.setHorasFaltas(horasFalta + minutosFalta/60.0);
        pagamento.setInss(inss);
        pagamento.setIrpf(irpf);
        pagamento.setFgts(fgts);
        pagamento.setAvaliacao(avaliacao);

        System.out.println("\n\n\n\n\n" + pagamento + "\n\n\n\n\n");

        return pagamento;
    }

    public String salvarPagamento(Pagamento pagamento) {
        try {
            if (pagamento.getAvaliacao() != null) {
                pagamento.getAvaliacao().setPagamento(pagamento);
            }

            if (pagamento.getId() == null) {
                pagamentoDao.salvar(pagamento);
            } else {
                pagamentoDao.atualizar(pagamento);
            }

            return "Pagamento salvo com sucesso!";
        } catch (Exception e) {
            return "Erro ao salvar pagamento: " + e.getMessage();
        }
    }

    public List<Pagamento> buscarPagamentoPorCompetencia(YearMonth competencia) {
        return pagamentoDao.buscarPagamentosPorCompetencia(competencia);
    }

    public void deletar(String cpf) {
        pagamentoDao.deletarPorCpf(Pagamento.class, cpf);
    }
}
