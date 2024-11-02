package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.esand.gerenciamentorh.Utils.showErrorMessage;

public class CadastroController {

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

    @FXML
    protected void salvar() {
        String nome = nomeField.getText();
        String sobrenome = sobrenomeField.getText();
        String cpf = cpfField.getText();
        String cargo = cpfField.getText();
        double salario = Double.parseDouble(salarioField.getText());

        insertFuncionario(nome, sobrenome, cpf, cargo, salario);
    }

    public void insertFuncionario(String nome, String sobrenome, String cpf, String cargo, double salario) {
        Funcionario funcionario = new Funcionario(
                null,
                nome,
                sobrenome,
                cpf,
                Funcionario.Departamento.PRODUCAO,
                cargo,
                salario,
                null
        );

        EntityManager em = DataBase.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(funcionario);
            transaction.commit();
//            showSuccessMessage("Funcionário cadastrado com sucesso!");
        } catch (PersistenceException e) {
            if (e.getCause() instanceof PersistenceException) {
                showErrorMessage("Erro de Integridade " + " O CPF " + cpf + " já está cadastrado.");
            } else {
                showErrorMessage("Erro ao Inserir " + " Ocorreu um erro ao inserir o funcionário: " + e.getMessage());
            }
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}