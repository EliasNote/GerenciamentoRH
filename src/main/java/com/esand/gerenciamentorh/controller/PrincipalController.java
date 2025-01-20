package com.esand.gerenciamentorh.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import static com.esand.gerenciamentorh.controller.Utils.loadChildrenFXML;

public class PrincipalController {

    @FXML
    private Pane contentPane;

    @FXML
    private Button btnFuncionarios;
    @FXML
    private Button btnPagamento;
    @FXML
    private Button btnBeneficios;
    @FXML
    private Button btnAcesso;

    private Button activeButton;

    public void showFuncionarios() {
        loadChildrenFXML(contentPane,"visualizar.fxml");
        setActiveButton(btnFuncionarios);
    }

    public void showPagamento() {
        loadChildrenFXML(contentPane,"pagamento.fxml");
        setActiveButton(btnPagamento);
    }

    public void showBeneficios() {
        loadChildrenFXML(contentPane,"beneficio.fxml");
        setActiveButton(btnBeneficios);
    }

    public void showAcesso() {
        loadChildrenFXML(contentPane,"cadastroAcesso.fxml");
        setActiveButton(btnAcesso);
    }

    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("active");
        }

        activeButton = button;

        activeButton.getStyleClass().add("active");
    }


}
