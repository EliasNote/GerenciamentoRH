package com.esand.gerenciamentorh.controller.service;

import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Avaliacao;
import com.esand.gerenciamentorh.model.entidades.Pagamento;

import java.time.YearMonth;

public class PagamentoService {
    private final Dao<Pagamento> pagamentoDao = new Dao<>();
    private final Dao<Avaliacao> avaliacaoDao = new Dao<>();

    public Pagamento criarPagamento(String cpf, YearMonth competencia, Double salarioLiquido,
                                    Integer horasExtra, Integer minutosExtra,
                                    Integer horasFalta, Integer minutosFalta) {
        Pagamento pagamento = pagamentoDao.buscarPorFuncionarioECompetencia(cpf, competencia);

        if (pagamento == null) {
            pagamento = new Pagamento();
        }

        pagamento.setCompetencia(competencia);
        pagamento.setSalarioLiquido(salarioLiquido);
        pagamento.setHorasExtras(horasExtra + minutosExtra/60.0);
        pagamento.setHorasFaltas(horasFalta + minutosFalta/60.0);

        return pagamento;
    }

    public String salvarPagamento(Pagamento pagamento) {
        try {
            Avaliacao avaliacao = avaliacaoDao.salvar(pagamento.getAvaliacao());

            if (pagamento.getId() == null) {
                pagamentoDao.salvar(pagamento);
                avaliacao.setPagamento(pagamento);
                avaliacaoDao.atualizar(avaliacao);
            } else {
                pagamentoDao.atualizar(pagamento);
            }

            return "Pagamento salvo com sucesso!";
        } catch (Exception e) {
            return "Erro ao salvar pagamento: " + e.getMessage();
        }
    }
}
