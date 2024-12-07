package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.cadastro.CadastroBeneficioController;
import com.esand.gerenciamentorh.controller.editar.EditarBeneficioController;
import com.esand.gerenciamentorh.model.dao.*;
import com.esand.gerenciamentorh.dto.BeneficioDto;
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
    private TableView<BeneficioDto> tabela;
    @FXML
    private TableColumn<BeneficioDto, String> tipoColumn;
    @FXML
    private TableColumn<BeneficioDto, Double> valorColumn;
    @FXML
    private TableColumn<BeneficioDto, String> descricaoColumn;

    private ObservableList<BeneficioDto> beneficios = FXCollections.observableArrayList();
    private BeneficioDao beneficioDao = new BeneficioDao();

    public void initialize() {
        List<BeneficioDto> beneficioDtos = new ArrayList<>();

        beneficioDao.buscarTodos().forEach(beneficio -> {
            beneficioDtos.add(
                    new BeneficioDto(beneficio.getTipo(), beneficio.getDescricao(), beneficio.getValor())
            );
        });

        beneficios.addAll(beneficioDtos);

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
        EditarBeneficioController.beneficio = beneficioDao.buscarGenerico(tabela.getSelectionModel().getSelectedItem().getTipo());
        EditarBeneficioController.visualizarBeneficioController = this;
        loadFXML("editarBeneficio.fxml", new Stage());
    }

    public void excluir() {
        BeneficioDto beneficioDto = tabela.getSelectionModel().getSelectedItem();

        if (beneficioDao.buscarGenerico(beneficioDto.getTipo()).getFuncionarios().isEmpty()) {
            beneficioDao.deletar(beneficioDto.getTipo());
            beneficios.remove(beneficioDto);
        }
    }

    public void atualizarTabela() {
        beneficios.clear();
        List<BeneficioDto> beneficioDtos = beneficioDao.buscarTodos().stream()
                .map(beneficio ->
                    new BeneficioDto(beneficio.getTipo(), beneficio.getDescricao(), beneficio.getValor())
                ).toList();

        beneficios.addAll(beneficioDtos);
        tabela.refresh();
    }
}