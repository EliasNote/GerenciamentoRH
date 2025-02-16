package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.cadastro.CadastroBeneficioController;
import com.esand.gerenciamentorh.controller.editar.EditarBeneficioController;
import com.esand.gerenciamentorh.controller.service.BeneficioService;
import com.esand.gerenciamentorh.model.dto.BeneficioDto;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

import static com.esand.gerenciamentorh.controller.util.Utils.*;
import static com.esand.gerenciamentorh.controller.util.EnumView.*;

public class VisualizarBeneficioController {
    @FXML private TableView<BeneficioDto> tabela;
    @FXML private TableColumn<Beneficio, String> tipoColumn, descricaoColumn;
    @FXML private TableColumn<Beneficio, Double> valorColumn;

    private ObservableList<BeneficioDto> beneficios = FXCollections.observableArrayList();
    private BeneficioService beneficioService = new BeneficioService();

    public void initialize() {
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        carregarBeneficios();
    }

    public void cadastrar() {
        CadastroBeneficioController.visualizarBeneficioController = this;
        loadFXML(BENEFICIO_CADASTRO.getPath(), new Stage());
    }

    public void editar() {
        BeneficioDto beneficioDto = tabela.getSelectionModel().getSelectedItem();

        if (beneficioDto != null) {
            EditarBeneficioController.beneficio = beneficioService.buscarBeneficioPorTipo(beneficioDto.getTipo());
            EditarBeneficioController.visualizarBeneficioController = this;
            loadFXML(BENEFICIO_EDITAR.getPath(), new Stage());
        }
    }

    public void excluir() {
        BeneficioDto beneficioDto = tabela.getSelectionModel().getSelectedItem();

        if (beneficioDto != null && beneficioService.buscarBeneficioPorTipo(beneficioDto.getTipo()).getFuncionarios().isEmpty()) {
            beneficioService.deletarPorTipo(beneficioDto.getTipo());
            beneficios.remove(beneficioDto);
        }
    }

    public void atualizarTabela() {
        carregarBeneficios();
        tabela.refresh();
    }

    public void carregarBeneficios() {
        beneficios.clear();

        List<Beneficio> beneficiosBuscados = beneficioService.buscarTodos();

        for (Beneficio beneficio : beneficiosBuscados) {
            beneficios.add(
                    new BeneficioDto(
                            beneficio.getTipo(),
                            setTextoFormatado(beneficio.getValor()),
                            beneficio.getDescricao()
                    )
            );
        }

        tabela.setItems(this.beneficios);
    }
}
