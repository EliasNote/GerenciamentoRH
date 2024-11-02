package com.esand.gerenciamentorh;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import static com.esand.gerenciamentorh.Utils.loadChildrenFXML;

public class HelloController {

    @FXML
    private Pane contentPane;

    @FXML
    public void initialize() {

    }

    @FXML
    public void showCadastrar() {
        loadChildrenFXML(contentPane,"cadastro.fxml");
    }

    @FXML
    public void showVisualizar() {
        loadChildrenFXML(contentPane,"visualizar.fxml");
    }

    @FXML
    public void showPagamento() {
        loadChildrenFXML(contentPane,"pagamento.fxml");
    }
}