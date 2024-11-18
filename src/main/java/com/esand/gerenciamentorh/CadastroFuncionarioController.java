package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.dao.BeneficioDao;
import com.esand.gerenciamentorh.dao.FuncionarioDao;
import com.esand.gerenciamentorh.entidades.Beneficio;
import com.esand.gerenciamentorh.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class CadastroFuncionarioController {
    @FXML
    private ListView<CheckBox> beneficios;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField sobrenomeField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField salarioField;
    @FXML
    private TextField cargoField;

    private FuncionarioDao funcionarioDao = new FuncionarioDao();
    private BeneficioDao beneficioDao = new BeneficioDao();
    private ObservableList<CheckBox> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        List<Beneficio> beneficiosBanco = beneficioDao.buscarTodos();

        beneficiosBanco.forEach(beneficio -> lista.add(new CheckBox(beneficio.getTipo())));

        beneficios.setItems(lista);
    }

    @FXML
    protected void salvar() {
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
                .map(x -> beneficioDao.buscarPorNome(x.getText()))
                .toList();

        Funcionario funcionario = new Funcionario(
                null,
                nome,
                sobrenome,
                cpf,
                Funcionario.Departamento.PRODUCAO,
                cargo,
                salario,
                beneficiosSelecionados,
                LocalDate.now(),
                null
        );


        if (funcionarioDao.buscarPorCpf(cpf) != null) {
            errorLabel.setText("Funcionário com esse CPF já está cadastrado");
            return;
        }

        funcionarioDao.salvar(funcionario);
    }
}