package com.esand.gerenciamentorh;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

public class AvaliacaoController {
    @FXML
    private Spinner<Double> nota;
    @FXML
    private TextArea observacao;

    @FXML
    public void initialize() {
        nota.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 5, 0.5));
    }

    public void salvar() {
        PagamentoController.avaliacaoNota = nota.getValue();
        PagamentoController.avaliacaoObservacao = observacao.getText();
    }
}
