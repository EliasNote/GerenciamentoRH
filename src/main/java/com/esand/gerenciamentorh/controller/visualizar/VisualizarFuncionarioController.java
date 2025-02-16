package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.cadastro.CadastroFuncionarioController;
import com.esand.gerenciamentorh.controller.editar.EditarFuncionarioController;
import com.esand.gerenciamentorh.controller.service.FuncionarioService;
import com.esand.gerenciamentorh.model.dto.FuncionarioDto;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.esand.gerenciamentorh.controller.util.Utils.*;
import static com.esand.gerenciamentorh.controller.util.EnumView.*;

public class VisualizarFuncionarioController {

    @FXML private TableView<FuncionarioDto> tabela;
    @FXML private TableColumn<Funcionario, Long> idColumn;
    @FXML private TableColumn<Funcionario, String> nomeColumn, sobrenomeColumn, cpfColumn, cargoColumn;
    @FXML private TableColumn<Funcionario, String> salarioColumn;
    @FXML private TableColumn<Funcionario, LocalDate> admissaoColumn;

    private ObservableList<FuncionarioDto> funcionarios = FXCollections.observableArrayList();
    private FuncionarioService funcionarioService = new FuncionarioService();

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        sobrenomeColumn.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        admissaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataAdmissao"));

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        carregarFuncionarios();
    }

    public void editar() {
        FuncionarioDto funcionario = tabela.getSelectionModel().getSelectedItem();

        if (funcionario != null) {
            EditarFuncionarioController.funcionario = funcionarioService.buscarPorCpf(funcionario.getCpf());
            EditarFuncionarioController.visualizarFuncionarioController = this;
            loadFXML(FUNCIONARIO_EDITAR.getPath(), new Stage());
        }
    }

    public void excluir() {
        FuncionarioDto funcionario = tabela.getSelectionModel().getSelectedItem();

        if (funcionario != null) {
            funcionarioService.deletar(funcionario.getCpf());
            funcionarios.remove(funcionario);
        }
    }

    public void cadastrar() {
        CadastroFuncionarioController.visualizarFuncionarioController = this;
        loadFXML(FUNCIONARIO_CADASTRO.getPath(), new Stage());
    }

    public void atualizarTabela() {
        carregarFuncionarios();
        tabela.refresh();
    }

    public void carregarFuncionarios() {
        funcionarios.clear();

        List<Funcionario> funcionariosBuscados = funcionarioService.buscarTodos();

        for (Funcionario funcionario : funcionariosBuscados) {
            funcionarios.add(
                    new FuncionarioDto(
                            funcionario.getId(),
                            funcionario.getNome(),
                            funcionario.getSobrenome(),
                            funcionario.getCpf(),
                            funcionario.getCargo(),
                            setTextoFormatado(funcionario.getSalario()),
                            funcionario.getDataAdmissao().toString()
                    )
            );
        }

        tabela.setItems(this.funcionarios);
    }
}