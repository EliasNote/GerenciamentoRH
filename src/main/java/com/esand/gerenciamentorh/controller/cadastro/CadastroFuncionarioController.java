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

        String nome = nomeField.getText();
        String sobrenome = sobrenomeField.getText();
        String cpf = cpfField.getText();
        String cargo = cargoField.getText();
        LocalDate data = dataField.getValue();
        Double salario;

        if (
                nome.isEmpty() ||
                sobrenome.isEmpty() ||
                cpf.isEmpty() ||
                cargo.isEmpty() ||
                salarioField.getText().isEmpty() ||
                data == null
        ) {
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


        if (funcionarioService.buscarPorCpf(cpf) != null) {
            errorLabel.setText("Funcionário com esse CPF já está cadastrado");
            return;
        }

        funcionarioService.salvar(
                new Funcionario(
                        null,
                        nome,
                        sobrenome,
                        cpf,
                        cargo,
                        salario,
                        beneficiosSelecionados,
                        data,
                        null
                )
        );

        visualizarFuncionarioController.atualizarTabela();
    }
}