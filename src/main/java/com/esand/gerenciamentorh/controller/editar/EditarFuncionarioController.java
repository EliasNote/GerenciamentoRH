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

public class EditarFuncionarioController {
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

        String nome = nomeField.getText();
        String sobrenome = sobrenomeField.getText();
        String cpf = cpfField.getText();
        String cargo = cargoField.getText();
        Double salario;

        if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || cargo.isEmpty() || salarioField.getText().isEmpty()) {
            errorLabel.setText("Todas as informações devem ser preenchidas");
            return;
        }

        try {
            salario = Double.parseDouble(salarioField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Salário deve ser um número válido");
            return;
        }

        List<Beneficio> beneficiosSelecionados = this.beneficios.getItems().stream()
                .filter(CheckBox::isSelected)
                .map(x -> beneficioService.buscarBeneficioPorTipo(x.getText()))
                .toList();

        Funcionario funcionario = this.funcionario;

        funcionario.setNome(nome);
        funcionario.setSobrenome(sobrenome);
        funcionario.setCpf(cpf);
        funcionario.setCargo(cargo);
        funcionario.setSalario(salario);
        funcionario.setBeneficios(beneficiosSelecionados);

        funcionarioService.atualizar(funcionario);

        visualizarFuncionarioController.atualizarTabela();
    }

    private void carregarFuncionario() {
        nomeField.setText(funcionario.getNome());
        sobrenomeField.setText(funcionario.getSobrenome());
        cpfField.setText(funcionario.getCpf());
        cargoField.setText(funcionario.getCargo());
        salarioField.setText(funcionario.getSalario().toString());
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
