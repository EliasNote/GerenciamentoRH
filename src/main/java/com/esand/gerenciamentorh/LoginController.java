package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.entidades.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.esand.gerenciamentorh.Utils.loadFXML;
import static com.esand.gerenciamentorh.Utils.showErrorMessage;

public class LoginController {

    @FXML
    private TextField CPF;

    @FXML
    private TextField Senha;

    @FXML
    public void logar() {
        if (autenticar()) {
            loadFXML("principal.fxml", CPF);
        } else {
            showErrorMessage("CPF ou Senha incorreto");
        }
    }

    private boolean autenticar() {
        EntityManager entityManager = DataBase.getEntityManager();

        try {
            String query = "SELECT l FROM Login l WHERE l.cpf = :cpf AND l.senha = :senha";
            Login login = entityManager.createQuery(query, Login.class)
                    .setParameter("cpf", CPF.getText())
                    .setParameter("senha", Senha.getText())
                    .getSingleResult();
            return login != null;
        } catch (NoResultException e) {
            return false;
        } finally {
            entityManager.close();
        }
    }

}
