package com.esand.gerenciamentorh.controller.editar;

import com.esand.gerenciamentorh.controller.service.BeneficioService;
import com.esand.gerenciamentorh.controller.service.FuncionarioService;
import com.esand.gerenciamentorh.controller.visualizar.VisualizarFuncionarioController;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

import static com.esand.gerenciamentorh.controller.util.Utils.closeFxml;

public class EditarFuncionarioController {
    @FXML private DatePicker dataField;
    @FXML private TextField nomeField, sobrenomeField, cpfField, cargoField, salarioField;
    @FXML private Label errorLabel;
    @FXML private ListView<CheckBox> beneficios;

    public static Funcionario funcionario;
    public static VisualizarFuncionarioController visualizarFuncionarioController;

    private final FuncionarioService funcionarioService = new FuncionarioService();
    private final BeneficioService beneficioService = new BeneficioService();
    private ObservableList<CheckBox> lista = FXCollections.observableArrayList();

    public void initialize() {
        carregarFuncionario();
        carregarBeneficios();
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

        Funcionario funcionarioExistente = funcionarioService.buscarPorCpf(cpfField.getText());
        if (funcionarioExistente != null && !funcionarioExistente.getId().equals(funcionario.getId())) {
            errorLabel.setText("Funcionário com esse CPF já está cadastrado");
            return;
        }

        atualizarFuncionario(salario);
        funcionarioService.atualizar(funcionario);
        visualizarFuncionarioController.atualizarTabela();

        closeFxml();
    }

    private void atualizarFuncionario(Double salario) {
        List<Beneficio> beneficiosSelecionados = this.beneficios.getItems().stream()
                .filter(CheckBox::isSelected)
                .map(x -> beneficioService.buscarBeneficioPorTipo(x.getText()))
                .toList();

        funcionario.setNome(nomeField.getText());
        funcionario.setSobrenome(sobrenomeField.getText());
        funcionario.setCpf(cpfField.getText());
        funcionario.setCargo(cargoField.getText());
        funcionario.setSalario(salario);
        funcionario.setBeneficios(beneficiosSelecionados);
        funcionario.setDataAdmissao(dataField.getValue());
    }

    private Double parseSalario() {
        try {
            return Double.parseDouble(salarioField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean validarCampos() {
        return !nomeField.getText().isEmpty() &&
                !sobrenomeField.getText().isEmpty() &&
                !cpfField.getText().isEmpty() &&
                !cargoField.getText().isEmpty() &&
                !salarioField.getText().isEmpty() &&
                dataField.getValue() != null;
    }

    private void carregarFuncionario() {
        nomeField.setText(funcionario.getNome());
        sobrenomeField.setText(funcionario.getSobrenome());
        cpfField.setText(funcionario.getCpf());
        cargoField.setText(funcionario.getCargo());
        salarioField.setText(funcionario.getSalario().toString());
        dataField.setValue(funcionario.getDataAdmissao());
    }

    private void carregarBeneficios() {
        List<Beneficio> beneficiosBanco = beneficioService.buscarTodos();
        List<String> beneficiosFuncionario = new ArrayList<>();
        funcionario.getBeneficios().forEach(x ->
                beneficiosFuncionario.add(x.getTipo())
        );

        beneficiosBanco.forEach(beneficio -> {
            CheckBox checkBox = new CheckBox(beneficio.getTipo());
            if (beneficiosFuncionario.contains(checkBox.getText())) {
                checkBox.setSelected(true);
            }
            lista.add(checkBox);
        });

        beneficios.setItems(lista);
    }
}