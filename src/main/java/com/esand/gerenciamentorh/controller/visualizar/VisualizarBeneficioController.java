package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.cadastro.CadastroBeneficioController;
import com.esand.gerenciamentorh.controller.editar.EditarBeneficioController;
import com.esand.gerenciamentorh.model.dao.*;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.esand.gerenciamentorh.controller.Utils.loadFXML;

public class VisualizarBeneficioController {
    @FXML
    private TableView<Beneficio> tabela;
    @FXML
    private TableColumn<Beneficio, String> tipoColumn;
    @FXML
    private TableColumn<Beneficio, Double> valorColumn;
    @FXML
    private TableColumn<Beneficio, String> descricaoColumn;

    private ObservableList<Beneficio> beneficios = FXCollections.observableArrayList();
    private Dao<Beneficio> beneficioDao = new Dao();

    public void initialize() {
        beneficios.addAll(beneficioDao.buscarTodos(Beneficio.class));

        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.setItems(beneficios);
    }

    public void cadastrar() {
        CadastroBeneficioController.visualizarBeneficioController = this;
        loadFXML("cadastroBeneficio.fxml", new Stage());
    }

    public void editar() {
        EditarBeneficioController.beneficio = beneficioDao.buscarBeneficioPorTipo(tabela.getSelectionModel().getSelectedItem().getTipo());
        EditarBeneficioController.visualizarBeneficioController = this;
        loadFXML("editarBeneficio.fxml", new Stage());
    }

    public void excluir() {
        Beneficio beneficioDto = tabela.getSelectionModel().getSelectedItem();

        if (beneficioDao.buscarBeneficioPorTipo(beneficioDto.getTipo()).getFuncionarios().isEmpty()) {
            beneficioDao.deletarPorTipo(beneficioDto.getTipo());
            beneficios.remove(beneficioDto);
        }
    }

    public void atualizarTabela() {
        beneficios.clear();
        beneficios.addAll(beneficioDao.buscarTodos(Beneficio.class));
        tabela.refresh();
    }
}
