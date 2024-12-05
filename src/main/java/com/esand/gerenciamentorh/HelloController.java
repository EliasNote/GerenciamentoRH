package com.esand.gerenciamentorh;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import static com.esand.gerenciamentorh.Utils.loadChildrenFXML;

public class HelloController {

    @FXML
    private Pane contentPane;

    public void showVisualizar() {
        loadChildrenFXML(contentPane,"visualizar.fxml");
    }

    public void showPagamento() {
        loadChildrenFXML(contentPane,"pagamento.fxml");
    }

    public void showBeneficios() {
        loadChildrenFXML(contentPane,"beneficio.fxml");
    }

    public void showAcesso() {
        loadChildrenFXML(contentPane,"cadastroAcesso.fxml");
    }
}