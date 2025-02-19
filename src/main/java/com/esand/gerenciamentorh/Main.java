package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.controller.service.FuncionarioService;
import com.esand.gerenciamentorh.controller.service.calculo.impostos.Fgts;
import com.esand.gerenciamentorh.controller.service.calculo.impostos.Inss;
import com.esand.gerenciamentorh.controller.service.calculo.impostos.Irpf;
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
import java.util.*;

import static com.esand.gerenciamentorh.controller.util.EnumView.*;

public class Main extends Application {

    private final LoginService loginService = new LoginService();
    private final BeneficioService beneficioService = new BeneficioService();
    private final FuncionarioService funcionarioService = new FuncionarioService();

    @Override
    public void start(Stage stage) throws IOException {
        inicializarAdmin();
        inicializarBeneficios();
        inicializarFuncionarios();

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
            List<Beneficio> beneficios = Arrays.asList(
                    new Beneficio(null, "Vale Transporte", "Auxílio para transporte", 150.0, null),
                    new Beneficio(null, "Vale Refeição", "Auxílio para alimentação", 200.0, null),
                    new Beneficio(null, "Assistência Médica", "Plano de saúde", 500.0, null),
                    new Beneficio(null, "Seguro de Vida", "Seguro de vida em grupo", 100.0, null),
                    new Beneficio(null, "Auxílio Creche", "Reembolso para despesas com creche", 250.0, null),
                    new Beneficio(null, "Plano Odontológico", "Cobertura odontológica", 300.0, null)
            );
            beneficios.forEach(beneficioService::salvar);
        }
    }

    private void inicializarFuncionarios() {
        if (funcionarioService.buscarTodos().isEmpty()) {
            List<Beneficio> todosBeneficios = beneficioService.buscarTodos();

            Funcionario joao = criarFuncionario("João", "Silva", "12345678900", "Desenvolvedor", 5000.0, Arrays.asList(todosBeneficios.get(0), todosBeneficios.get(1)), LocalDate.of(2020, 1, 15));
            Funcionario maria = criarFuncionario("Maria", "Oliveira", "98765432100", "Analista", 6000.0, todosBeneficios.subList(0, 4), LocalDate.of(2019, 5, 20));
            Funcionario carlos = criarFuncionario("Carlos", "Santos", "45678912300", "Gerente", 8000.0, todosBeneficios, LocalDate.of(2018, 3, 10));
            Funcionario ana = criarFuncionario("Ana", "Pereira", "78912345600", "Analista Sênior", 7000.0, new ArrayList<>(), LocalDate.of(2019, 8, 15));
            Funcionario pedro = criarFuncionario("Pedro", "Gomes", "32165498700", "Estagiário", 2000.0, Collections.singletonList(todosBeneficios.get(0)), LocalDate.of(2022, 10, 1));
            Funcionario lucas = criarFuncionario("Lucas", "Rodrigues", "65498732100", "Arquiteto", 9000.0, todosBeneficios.subList(2, 5), LocalDate.of(2017, 7, 12));
            Funcionario patricia = criarFuncionario("Patricia", "Fernandes", "11223344550", "Desenvolvedora Júnior", 4000.0, Arrays.asList(todosBeneficios.get(1), todosBeneficios.get(3)), LocalDate.of(2023, 1, 10));
            Funcionario roberto = criarFuncionario("Roberto", "Almeida", "99887766550", "Analista de Sistemas", 7500.0, todosBeneficios.subList(1, 5), LocalDate.of(2016, 9, 18));

            List<Funcionario> funcionarios = Arrays.asList(joao, maria, carlos, ana, pedro, lucas, patricia, roberto);
            funcionarios.forEach(funcionarioService::salvar);
        }
    }

    private Funcionario criarFuncionario(String nome, String sobrenome, String cpf, String cargo, Double salario, List<Beneficio> beneficios, LocalDate dataAdmissao) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setSobrenome(sobrenome);
        funcionario.setCpf(cpf);
        funcionario.setCargo(cargo);
        funcionario.setSalario(salario);
        funcionario.setBeneficios(beneficios);
        funcionario.setDataAdmissao(dataAdmissao);
        return funcionario;
    }
}