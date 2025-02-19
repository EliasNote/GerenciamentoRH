package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.controller.service.FuncionarioService;
import com.esand.gerenciamentorh.controller.service.PagamentoService;
import com.esand.gerenciamentorh.controller.util.calculo.impostos.Fgts;
import com.esand.gerenciamentorh.controller.util.calculo.impostos.Inss;
import com.esand.gerenciamentorh.controller.util.calculo.impostos.Irpf;
import com.esand.gerenciamentorh.controller.service.BeneficioService;
import com.esand.gerenciamentorh.controller.service.LoginService;
import com.esand.gerenciamentorh.model.entidades.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esand.gerenciamentorh.controller.util.EnumView.*;

public class Main extends Application {

    private final LoginService loginService = new LoginService();
    private final BeneficioService beneficioService = new BeneficioService();
    private final FuncionarioService funcionarioService = new FuncionarioService();
    private final PagamentoService pagamentoService = new PagamentoService();

    @Override
    public void start(Stage stage) throws IOException {
        inicializarAdmin();
        inicializarBeneficios();
        inicializarFuncionarios();
        inicializarPagamentos();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(FXML_PATH.getPath() + LOGIN.getPath()));
        Scene scene = new Scene(fxmlLoader.load());
        String css = Main.class.getResource(FXML_PATH.getPath() + STYLE.getPath()).toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream(ICON.getPath())));
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
        if (loginService.buscarTodos().isEmpty()) {
            loginService.salvar(new Login(null, "1", "1"));
        }
    }

    private void inicializarBeneficios() {
        if (beneficioService.buscarTodos().isEmpty()) {
            List<Beneficio> beneficios = List.of(
                    new Beneficio(null, "Vale Transporte", "Auxílio para transporte", 150.0, null),
                    new Beneficio(null, "Vale Refeição", "Auxílio para alimentação", 200.0, null),
                    new Beneficio(null, "Assistência Médica", "Plano de saúde", 500.0, null),
                    new Beneficio(null, "Seguro de Vida", "Seguro de vida em grupo", 100.0, null)
            );

            for (Beneficio beneficio : beneficios) {
                beneficioService.salvar(beneficio);
            }
        }
    }

    private void inicializarFuncionarios() {
        if (funcionarioService.buscarTodos().isEmpty()) {
            List<Funcionario> funcionarios = List.of(
                    new Funcionario(null, "João", "Silva", "12345678901", "Desenvolvedor", 5000.0, new ArrayList<>(), LocalDate.now(), new ArrayList<>()),
                    new Funcionario(null, "Maria", "Oliveira", "10987654321", "Analista", 4500.0, new ArrayList<>(), LocalDate.now(), new ArrayList<>()),
                    new Funcionario(null, "Carlos", "Santos", "11223344556", "Gerente", 7000.0, new ArrayList<>(), LocalDate.now(), new ArrayList<>()),
                    new Funcionario(null, "Ana", "Costa", "22334455667", "Designer", 4000.0, new ArrayList<>(), LocalDate.now(), new ArrayList<>()),
                    new Funcionario(null, "Pedro", "Almeida", "33445566778", "Tester", 3500.0, new ArrayList<>(), LocalDate.now(), new ArrayList<>()),
                    new Funcionario(null, "Paula", "Souza", "44556677889", "Gerente de Projetos", 8000.0, new ArrayList<>(), LocalDate.now(), new ArrayList<>())
            );

            for (Funcionario funcionario : funcionarios) {
                funcionarioService.salvar(funcionario);
            }
        }
    }

    private void inicializarPagamentos() {
        if (pagamentoService.buscarTodos().isEmpty()) {
            List<Funcionario> funcionarios = funcionarioService.buscarTodos();
            for (Funcionario funcionario : funcionarios) {
                Map<String, Double> proventos = new HashMap<>();
                Map<String, Double> descontos = new HashMap<>();
                Avaliacao avaliacao = null;

                if (funcionario.getCpf().equals("12345678901") || funcionario.getCpf().equals("10987654321")) {
                    avaliacao = new Avaliacao(null, 4.5, "Bom desempenho", null);
                }

                Pagamento pagamento = pagamentoService.criarPagamento(funcionario, YearMonth.now(), proventos, descontos, avaliacao);
                pagamentoService.salvarPagamento(pagamento);
            }
        }
    }
}