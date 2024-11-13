package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.dao.BeneficioDao;
import com.esand.gerenciamentorh.dao.FuncionarioDao;
import com.esand.gerenciamentorh.dao.LoginDao;
import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Beneficio;
import com.esand.gerenciamentorh.entidades.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

import static com.esand.gerenciamentorh.Utils.showErrorMessage;

public class CadastroController {
    @FXML
    private ComboBox<String> beneficios;
    @FXML
    private ListView<String> beneficiosSelecionados;
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

        ObservableList<String> beneficiosCombo = FXCollections.observableArrayList();
        beneficiosBanco.forEach(beneficio -> beneficiosCombo.add(beneficio.getTipo()));

        beneficios.setItems(beneficiosCombo);
        beneficios.setPromptText("Selecione um benefício");
    }

    @FXML
    protected void salvar() {
        String nome = nomeField.getText();
        String sobrenome = sobrenomeField.getText();
        String cpf = cpfField.getText();
        String cargo = cpfField.getText();
        Double salario;

        if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || cargo.isEmpty() || salarioField.getText().trim().isEmpty()) {
            errorLabel.setText("Todas as informações devem ser preenchidas");
            return;
        }

        try {
            salario = Double.parseDouble(salarioField.getText().trim());
        } catch (NumberFormatException e) {
            errorLabel.setText("Salário deve ser um número válido");
            return;
        }

        insertFuncionario(nome, sobrenome, cpf, cargo, salario);
    }

    public void insertFuncionario(String nome, String sobrenome, String cpf, String cargo, Double salario) {
        Funcionario funcionario = new Funcionario(
                null,
                nome,
                sobrenome,
                cpf,
                Funcionario.Departamento.PRODUCAO,
                cargo,
                salario,
                null,
                LocalDate.now()
        );

        if (funcionarioDao.existePorCpf(cpf)) {
            errorLabel.setText("Funcionário com esse CPF já está cadastrado");
            return;
        }

        funcionarioDao.salvar(funcionario);
    }

    @FXML
    public void selecionar() {
    }
}