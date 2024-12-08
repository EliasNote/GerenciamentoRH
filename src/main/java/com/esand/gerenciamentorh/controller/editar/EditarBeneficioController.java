package com.esand.gerenciamentorh.controller.editar;


import com.esand.gerenciamentorh.controller.visualizar.VisualizarBeneficioController;
import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditarBeneficioController {
    @FXML
    private TextField tipoField;
    @FXML
    private TextField descricaoField;
    @FXML
    private TextField valorField;
    @FXML
    private Label errorLabel;

    public static Beneficio beneficio;
    private Dao<Beneficio> beneficioDao = new Dao();
    public static VisualizarBeneficioController visualizarBeneficioController;

    public void initialize() {
        carregarBeneficio();
    }

    public void salvar() {
        errorLabel.setText("");

        String tipo = tipoField.getText();
        String descricao = descricaoField.getText();
        Double valor;

        if (tipo.isEmpty() || descricao.isEmpty()) {
            errorLabel.setText("Todas as informações devem ser preenchidas");
            return;
        }

        try {
            valor = Double.parseDouble(valorField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Salário deve ser um número válido");
            return;
        }

        Beneficio beneficio = this.beneficio;

        beneficio.setTipo(tipo);
        beneficio.setDescricao(descricao);
        beneficio.setValor(valor);

        beneficioDao.atualizar(beneficio);

        visualizarBeneficioController.atualizarTabela();
    }

    private void carregarBeneficio() {
        tipoField.setText(beneficio.getTipo());
        descricaoField.setText(beneficio.getDescricao());
        valorField.setText(beneficio.getValor().toString());
    }
}
