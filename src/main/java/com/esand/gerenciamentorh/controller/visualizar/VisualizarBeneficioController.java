package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.cadastro.CadastroBeneficioController;
import com.esand.gerenciamentorh.controller.editar.EditarBeneficioController;
import com.esand.gerenciamentorh.controller.service.BeneficioService;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.esand.gerenciamentorh.controller.util.Utils.loadFXML;
import static com.esand.gerenciamentorh.controller.util.EnumView.*;

public class VisualizarBeneficioController {
    @FXML private TableView<Beneficio> tabela;
    @FXML private TableColumn<Beneficio, String> tipoColumn, descricaoColumn;
    @FXML private TableColumn<Beneficio, Double> valorColumn;

    private ObservableList<Beneficio> beneficios = FXCollections.observableArrayList();
    private BeneficioService beneficioService = new BeneficioService();

    public void initialize() {
        beneficios.addAll(beneficioService.buscarTodos());

        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.setItems(beneficios);
    }

    public void cadastrar() {
        CadastroBeneficioController.visualizarBeneficioController = this;
        loadFXML(BENEFICIO_CADASTRO.getPath(), new Stage());
    }

    public void editar() {
        Beneficio beneficioDto = tabela.getSelectionModel().getSelectedItem();

        if (beneficioDto != null) {
            EditarBeneficioController.beneficio = beneficioService.buscarBeneficioPorTipo(beneficioDto.getTipo());
            EditarBeneficioController.visualizarBeneficioController = this;
            loadFXML(BENEFICIO_EDITAR.getPath(), new Stage());
        }
    }

    public void excluir() {
        Beneficio beneficioDto = tabela.getSelectionModel().getSelectedItem();

        if (beneficioDto != null && beneficioService.buscarBeneficioPorTipo(beneficioDto.getTipo()).getFuncionarios().isEmpty()) {
            beneficioService.deletarPorTipo(beneficioDto.getTipo());
            beneficios.remove(beneficioDto);
        }
    }

    public void atualizarTabela() {
        beneficios.clear();
        beneficios.addAll(beneficioService.buscarTodos());
        tabela.refresh();
    }
}
