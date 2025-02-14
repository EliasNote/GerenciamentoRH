package com.esand.gerenciamentorh.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import static com.esand.gerenciamentorh.controller.util.Utils.loadChildrenFXML;
import static com.esand.gerenciamentorh.controller.EnumView.*;

public class PrincipalController {

    @FXML private Pane contentPane;
    @FXML private Button btnFuncionarios, btnPagamento, btnBeneficios, btnAcesso, btnCalculo;

    private Button activeButton;

    public void showFuncionarios() {
        loadChildrenFXML(contentPane, FUNCIONARIO_VISUALIZAR.getPath());
        setActiveButton(btnFuncionarios);
    }

    public void showCalculo() {
        loadChildrenFXML(contentPane, PAGAMENTO_CADASTRO.getPath());
        setActiveButton(btnCalculo);
    }

    public void showPagamento() {
        loadChildrenFXML(contentPane, PAGAMENTO_VISUALIZAR.getPath());
        setActiveButton(btnPagamento);
    }

    public void showBeneficios() {
        loadChildrenFXML(contentPane, BENEFICIO_VISUALIZAR.getPath());
        setActiveButton(btnBeneficios);
    }

    public void showAcesso() {
        loadChildrenFXML(contentPane, LOGIN_CADASTRO.getPath());
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
