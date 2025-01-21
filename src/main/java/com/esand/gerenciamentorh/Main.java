package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Fgts;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Inss;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Irpf;
import com.esand.gerenciamentorh.model.dao.Dao;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import com.esand.gerenciamentorh.model.entidades.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private Dao<Login> loginDao = new Dao();
    private Dao<Beneficio> beneficioDao = new Dao();
//    private Dao<Campo> campoDao = new Dao();

    @Override
    public void start(Stage stage) throws IOException {
        inicializarAdmin();
        inicializarBeneficios();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = Main.class.getResource("view/Style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/imagens/monitor.png")));
        stage.setTitle("Gerenciamento de Funcionários");

        Irpf.faixas = new double[] {0, 2259.20, 2259.21, 2826.65, 2826.66, 3751.05, 3751.06, 4664.68, 4664.69, 0};
        Irpf.aliquotas = new double[] {0, 0.075, 0.15, 0.225, 0.275};
        Irpf.parcelasDeducao = new double[] {0, 169.44, 381.44, 662.77, 896.00};

        Inss.faixas = new double[] {0, 1518.00, 1518.01, 2793.88, 2793.89, 4190.83, 4190.84, 8157.41};
        Inss.aliquotas = new double[] {0.075, 0.09, 0.12, 0.14};

        Fgts.aliquota = 0.08;

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void inicializarAdmin() {
        if (loginDao.buscarTodos(Login.class).isEmpty()) {
            Login login = new Login();
            login.setLogin("admin@admin.com");
            login.setSenha("admin");

            loginDao.salvarLogin(login);
        }
    }

    private void inicializarBeneficios() {
        if (beneficioDao.buscarTodos(Beneficio.class).isEmpty()) {
            List<Beneficio> beneficios = List.of(
                    // SALVAR INDIVIDUALMENTE NA ENTIDADE CAMPOS
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

//    private void inicializarCampos() {
//        if (campoDao.buscarTodos(Campo.class).isEmpty()) {
//            List<Campo> campos = List.of(
//                    new Campo(null, CadastroPagamentoController.SALARIO_BRUTO, true, true, true, true),
//                    new Campo(null, CadastroPagamentoController.HORAS_EXTRAS, true, true, true, true),
//                    new Campo(null, CadastroPagamentoController.HORAS_FALTAS, false, true, true, true)
//
//            );
//        }
//    }
}