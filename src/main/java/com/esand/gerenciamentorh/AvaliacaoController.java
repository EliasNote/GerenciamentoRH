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
        // Obtendo os valores do Spinner e do Label
        Double notaValue = nota.getValue(); // Obtém o valor do Spinner
        String observacaoValue = observacao.getText(); // Obtém o texto do Label

        // Imprimindo os valores
        System.out.println("-------------------------------------" + notaValue + "---" + observacaoValue + "------------------------");

        // Armazenando os valores na classe PagamentoController
        PagamentoController.avaliacaoNota = notaValue;
        PagamentoController.avaliacaoObservacao = observacaoValue;
    }
}
