package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.controller.visualizar.VisualizarBeneficioController;
import com.esand.gerenciamentorh.model.dao.BeneficioDao;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroBeneficioController {

    @FXML
    private TextField tipoField;

    @FXML
    private TextField descricaoField;

    @FXML
    private TextField valorField;

    @FXML
    private Label errorLabel;

    private BeneficioDao beneficioDao = new BeneficioDao();
    public static VisualizarBeneficioController visualizarBeneficioController;

    @FXML
    private void salvarBeneficio() {
        String tipo = tipoField.getText();
        String descricao = descricaoField.getText();
        Double valor;

        if (tipo.isEmpty() || descricao.isEmpty()) {
            errorLabel.setText("Todos os campos são obrigatórios.");
            return;
        }

        try {
            valor = Double.parseDouble(valorField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Valor inválido.");
            return;
        }

        beneficioDao.salvar(
                new Beneficio(
                        null,
                        tipo,
                        descricao,
                        valor,
                        null)
        );
        errorLabel.setText("Benefício salvo com sucesso!");

        visualizarBeneficioController.atualizarTabela();
    }
}
