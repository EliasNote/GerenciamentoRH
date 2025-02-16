package com.esand.gerenciamentorh.controller.cadastro;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

import static com.esand.gerenciamentorh.controller.util.Utils.closeFxml;

public class CadastroAvaliacaoController {
    @FXML private Spinner<Double> nota;
    @FXML private TextArea observacao;

    public void initialize() {
        nota.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 0, 1));
        carregarAvaliacao();
    }

    public void carregarAvaliacao() {
        Double avaliacaoNota = CadastroPagamentoController.avaliacaoNota;
        String avaliacaoObservacao = CadastroPagamentoController.avaliacaoObservacao;

        if (avaliacaoNota != null) {
            nota.getValueFactory().setValue(avaliacaoNota);
        }
        if (avaliacaoObservacao != null) {
            observacao.setText(avaliacaoObservacao);
        }
    }

    public void salvar() {
        CadastroPagamentoController.avaliacaoNota = nota.getValue();
        CadastroPagamentoController.avaliacaoObservacao = observacao.getText();

        closeFxml();
    }
}
