package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.dao.BeneficioDao;
import com.esand.gerenciamentorh.dto.BeneficioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class BeneficioController {
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

    public void editar() {
    }

    public void excluir() {
        BeneficioDto beneficioDto = tabela.getSelectionModel().getSelectedItem();
        beneficioDao.excluirPorTipo(beneficioDto.getTipo());
        beneficios.remove(beneficioDto);
    }
}
