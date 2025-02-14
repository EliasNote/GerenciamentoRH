package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.controller.service.BeneficioService;
import com.esand.gerenciamentorh.controller.service.FuncionarioService;
import com.esand.gerenciamentorh.controller.visualizar.VisualizarFuncionarioController;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class CadastroFuncionarioController {

    @FXML private ListView<CheckBox> beneficios;
    @FXML private Label errorLabel;
    @FXML private TextField nomeField, sobrenomeField, cpfField, salarioField, cargoField;
    @FXML private DatePicker dataField;

    private final FuncionarioService funcionarioService = new FuncionarioService();
    private final BeneficioService beneficioService = new BeneficioService();
    private final ObservableList<CheckBox> lista = FXCollections.observableArrayList();
    public static VisualizarFuncionarioController visualizarFuncionarioController;

    public void initialize() {
        List<Beneficio> beneficiosBanco = beneficioService.buscarTodos();

        beneficiosBanco.forEach(beneficio -> lista.add(new CheckBox(beneficio.getTipo())));

        beneficios.setItems(lista);
    }

    public void salvar() {
        errorLabel.setText("");

        if (!validarCampos()) {
            errorLabel.setText("Todas as informações devem ser preenchidas");
            return;
        }

        Double salario = parseSalario();
        if (salario == null) {
            errorLabel.setText("Salário deve ser um número válido");
            return;
        }

        if (funcionarioService.buscarPorCpf(cpfField.getText()) != null) {
            errorLabel.setText("Funcionário com esse CPF já está cadastrado");
            return;
        }

        Funcionario funcionario = criarFuncionario(salario);
        funcionarioService.salvar(funcionario);
        visualizarFuncionarioController.atualizarTabela();
    }

    private boolean validarCampos() {
        return !nomeField.getText().isEmpty() &&
                !sobrenomeField.getText().isEmpty() &&
                !cpfField.getText().isEmpty() &&
                !cargoField.getText().isEmpty() &&
                !salarioField.getText().isEmpty() &&
                dataField.getValue() != null;
    }

    private Double parseSalario() {
        try {
            return Double.parseDouble(salarioField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Funcionario criarFuncionario(Double salario) {
        List<Beneficio> beneficiosSelecionados = this.beneficios.getItems().stream()
                .filter(CheckBox::isSelected)
                .map(x -> beneficioService.buscarBeneficioPorTipo(x.getText()))
                .toList();

        return new Funcionario(
                null,
                nomeField.getText(),
                sobrenomeField.getText(),
                cpfField.getText(),
                cargoField.getText(),
                salario,
                beneficiosSelecionados,
                dataField.getValue(),
                null
        );
    }
}