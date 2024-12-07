package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.model.dao.BeneficioDao;
import com.esand.gerenciamentorh.model.dao.LoginDao;
import com.esand.gerenciamentorh.model.database.DataBase;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import com.esand.gerenciamentorh.model.entidades.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private LoginDao loginDao = new LoginDao();
    private BeneficioDao beneficioDao = new BeneficioDao();

    @Override
    public void start(Stage stage) throws IOException {
        inicializarAdmin();
        inicializarBeneficios();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gerenciamento de Funcionários");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void inicializarAdmin() {
        if (loginDao.buscarTodos().isEmpty()) {
            Login login = new Login();
            login.setCpf("admin");
            login.setSenha("admin");

            loginDao.salvar(login);
        }
    }

    private void inicializarBeneficios() {
        if (beneficioDao.buscarTodos().isEmpty()) {
            List<Beneficio> beneficios = List.of(
                    new Beneficio(null, "Vale Transporte", "Auxílio para transporte", 150.0, null),
                    new Beneficio(null, "Vale Refeição", "Auxílio para alimentação", 200.0, null),
                    new Beneficio(null, "Assistência Médica", "Plano de saúde", 500.0, null),
                    new Beneficio(null, "Seguro de Vida", "Seguro de vida em grupo", 100.0, null)
            );

            for (Beneficio beneficio : beneficios) {
                beneficioDao.salvar(beneficio);
            }
        }
    }
}