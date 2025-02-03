package com.esand.gerenciamentorh.controller.cadastro;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

public class CadastroAvaliacaoController {
    @FXML private Spinner<Double> nota;
    @FXML private TextArea observacao;

    public void initialize() {
        nota.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 0, 1));
    }

    public void salvar() {
        CadastroPagamentoController.avaliacaoNota = nota.getValue();
        CadastroPagamentoController.avaliacaoObservacao = observacao.getText();
    }
}
