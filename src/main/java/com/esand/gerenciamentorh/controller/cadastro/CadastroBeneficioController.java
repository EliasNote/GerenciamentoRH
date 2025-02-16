package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.controller.visualizar.VisualizarBeneficioController;
import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.esand.gerenciamentorh.controller.util.Utils.closeFxml;

public class CadastroBeneficioController {

    @FXML private TextField tipoField, descricaoField, valorField;

    @FXML private Label errorLabel;

    private Dao<Beneficio> beneficioDao = new Dao();
    public static VisualizarBeneficioController visualizarBeneficioController;

    @FXML
    private void salvar() {
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

        closeFxml();
    }
}
