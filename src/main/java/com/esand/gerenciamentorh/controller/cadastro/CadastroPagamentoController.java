package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.controller.cadastro.calculo.CalculoStrategy;
import com.esand.gerenciamentorh.controller.cadastro.calculo.CalculoEnum;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Fgts;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Inss;
import com.esand.gerenciamentorh.controller.cadastro.calculo.impostos.Irpf;
import com.esand.gerenciamentorh.model.dao.*;
import com.esand.gerenciamentorh.model.dto.CampoDto;
import com.esand.gerenciamentorh.model.entidades.Avaliacao;
import com.esand.gerenciamentorh.model.entidades.Beneficio;
import com.esand.gerenciamentorh.model.entidades.Pagamento;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.esand.gerenciamentorh.controller.Utils.loadFXML;

public class CadastroPagamentoController {
    @FXML
    private TableColumn<CampoDto, String> camposColuna;
    @FXML
    private TableColumn<CampoDto, String> informadoColuna;
    @FXML
    private TableColumn<CampoDto, String> proventosColuna;
    @FXML
    private TableColumn<CampoDto, String> descontosColuna;
    @FXML
    private TableView<CampoDto> tabelaFolha;
    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private TextField cargo;
    @FXML
    private TextField dataAdmissao;
    @FXML
    private ListView<HBox> listaNomes;
    @FXML
    private Spinner<Integer> horaExtra;
    @FXML
    private Spinner<Integer> horaFalta;
    @FXML
    private Spinner<Integer> minutoExtra;
    @FXML
    private Spinner<Integer> minutoFalta;
    @FXML
    private Spinner<Integer> ano;
    @FXML
    private Spinner<Integer> mes;
    @FXML
    private Label salvarLabel;
    @FXML
    private Label salarioLiquido;

    private static final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

    private ObservableList<CampoDto> listaFolha = FXCollections.observableArrayList();
    private Dao<Beneficio> beneficioDao = new Dao();
    private Dao<Funcionario> funcionarioDao = new Dao();
    private Dao<Pagamento> pagamentoDao = new Dao();
    private Dao<Avaliacao> avaliacaoDao = new Dao();

    protected static Double avaliacaoNota;
    protected static String avaliacaoObservacao;

    public static final String SALARIO_BRUTO = "Salário Bruto";
    public static final String HORAS_EXTRAS = "Horas Extras";
    public static final String HORAS_FALTAS = "Horas Faltas";
    public static final String TOTAL = "Total";
    public static final CalculoEnum INSS = CalculoEnum.INSS;
    public static final CalculoEnum IRPF = CalculoEnum.IRPF;
    public static final CalculoEnum FGTS = CalculoEnum.FGTS;


    public void initialize() throws Exception {
        camposColuna.setCellValueFactory(new PropertyValueFactory<>("campos"));
        informadoColuna.setCellValueFactory(new PropertyValueFactory<>("informado"));
        proventosColuna.setCellValueFactory(new PropertyValueFactory<>("proventos"));
        descontosColuna.setCellValueFactory(new PropertyValueFactory<>("descontos"));

        iniciarSpinners();
        carregarTodosEmpregados();
    }

    public void salvarHoras() {
        Funcionario funcionario = funcionarioDao.buscarFuncionarioPorCpf(cpf.getText());

        removerCampos("horas");

        if (horaExtra.getValue() > 0 || minutoExtra.getValue() > 0) {
            listaFolha.add(new CampoDto(
                    HORAS_EXTRAS,
                    horaExtra.getValue().toString() + ":" + minutoExtra.getValue().toString(),
                    String.format("%,.2f",
                            ((funcionario.getSalario() / 220) * horaExtra.getValue() + (minutoExtra.getValue() / 60.0)) * 1.5),
                    String.format("%,.2f", 0.00)
            ));
        }

        if (horaFalta.getValue() > 0 || minutoFalta.getValue() > 0) {
            listaFolha.add(new CampoDto(
                    HORAS_FALTAS,
                    horaFalta.getValue().toString() + ":" + minutoFalta.getValue().toString(),
                    String.format("%,.2f", 0.00),
                    String.format("%,.2f",
                            (funcionario.getSalario() / 220) * horaFalta.getValue() + (minutoFalta.getValue() / 60.0))

            ));
        }

        calcularTotal();
    }

    private void iniciarSpinners() {
        SpinnerValueFactory<Integer> horaExtra = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> horaFalta = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> minutoExtra = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> minutoFalta = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        SpinnerValueFactory<Integer> mes = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, LocalDate.now().getMonthValue());
        SpinnerValueFactory<Integer> ano = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, LocalDate.now().getYear(), LocalDate.now().getYear());

        this.horaExtra.setValueFactory(horaExtra);
        this.horaFalta.setValueFactory(horaFalta);
        this.minutoExtra.setValueFactory(minutoExtra);
        this.minutoFalta.setValueFactory(minutoFalta);

        this.mes.setValueFactory(mes);
        this.ano.setValueFactory(ano);
    }



    private void carregarTodosEmpregados() {
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
        funcionarios.addAll(funcionarioDao.buscarTodos());

        for (Funcionario funcionario : funcionarios) {
            HBox hBox = new HBox();
            hBox.getStyleClass().add("hbox-custom");

            Label nomeLabel = new Label(funcionario.getNome() + " " + funcionario.getSobrenome());
            nomeLabel.getStyleClass().add("label-custom");
            nomeLabel.setMaxWidth(135);

            Button selecionarButton = new Button("Selecionar");
            selecionarButton.getStyleClass().add("button-custom");
            selecionarButton.setOnAction(e -> carregarFuncionario(funcionario.getCpf()));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            hBox.getChildren().addAll(nomeLabel, spacer, selecionarButton);
            listaNomes.getItems().add(hBox);
        }
    }


    private void carregarFuncionario(String cpf) {
        Funcionario funcionario;

        funcionario = funcionarioDao.buscarFuncionarioPorCpf(cpf);
        carregarCampos(funcionario);

        nome.setText(funcionario.getNome() + " " + funcionario.getSobrenome());
        this.cpf.setText(funcionario.getCpf());
        cargo.setText(funcionario.getCargo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = funcionario.getDataAdmissao().format(formatter);
        dataAdmissao.setText(dataFormatada);
    }

    private void carregarCampos(Funcionario funcionario) {
        removerCampos("salario");
        removerCampos("beneficios");
        removerCampos("horas");
        removerCampos("inss");
        removerCampos("irpf");

        listaFolha.add(new CampoDto(
                        SALARIO_BRUTO,
                        "220:00",
                        String.format("%,.2f", funcionario.getSalario()),
                        String.format("%,.2f", 0.00)
                )
        );

        if (!funcionario.getBeneficios().isEmpty()) {
            funcionario.getBeneficios().forEach(x -> {
                listaFolha.add(new CampoDto(
                        x.getTipo(),
                        String.format("%,.2f", x.getValor()),
                        String.format("%,.2f", x.getValor()),
                        String.format("%,.2f", 0.00)
                ));
            });
        }

        double baseCalculoImposto = baseCalculoImposto();
        double inss = calcularImposto(INSS, baseCalculoImposto);

        listaFolha.add(new CampoDto(
                INSS.toString(),
                String.format("%,.2f", (inss*100)/baseCalculoImposto),
                String.format("%,.2f", 0.00),
                String.format("%,.2f", inss)
        ));

        double irpf = calcularImposto(IRPF, baseCalculoImposto - inss);

        listaFolha.add(new CampoDto(
                IRPF.toString(),
                String.format("%,.2f", (irpf*100)/baseCalculoImposto),
                String.format("%,.2f", 0.00),
                String.format("%,.2f", irpf)
        ));



        calcularTotal();

        tabelaFolha.setItems(listaFolha);
    }

    private double calcularImposto(CalculoEnum imposto, double baseCalculo) {
        return imposto.getStrategy().calcular(baseCalculo);

    }

    private double baseCalculoImposto() {
        double resultado = 0.00;

        try {
            for (CampoDto campo : listaFolha) {
                if (campo.getCampos().contains(SALARIO_BRUTO) ||
                    campo.getCampos().contains(HORAS_EXTRAS) ||
                    campo.getCampos().contains(HORAS_FALTAS)
                ) {
                    resultado += nf.parse(campo.getProventos()).doubleValue();
                    resultado -= nf.parse(campo.getDescontos()).doubleValue();
                }
            }
        } catch(Exception e) {
            System.out.println("Falha na conversão dos valores da folha de pagamento");
        }

        return resultado;
    }

    private void removerCampos(String verificar) {
        List<Beneficio> beneficios = beneficioDao.buscarTodos(Beneficio.class);

        if (verificar != null && verificar.equals("horas")) {
            listaFolha.removeIf(campo ->
                    campo.getCampos().equals(HORAS_EXTRAS) ||
                            campo.getCampos().equals(HORAS_FALTAS)
            );
        } else if (verificar != null && verificar.equals("salario")) {
            listaFolha.removeIf(campo ->
                    campo.getCampos().equals(SALARIO_BRUTO)
            );
        } else if (verificar != null && verificar.equals("beneficios")) {
            beneficios.forEach(beneficio -> {
                listaFolha.removeIf(campo ->
                        campo.getCampos().equals(beneficio.getTipo())
                );
            });
        } else if (verificar != null && verificar.equals("inss")) {
            listaFolha.removeIf(campo ->
                    campo.getCampos().equals(INSS.toString())
            );
        } else if (verificar != null && verificar.equals("irpf")) {
            listaFolha.removeIf(campo ->
                    campo.getCampos().equals(IRPF.toString())
            );
        }

        listaFolha.removeIf(campo ->
                campo.getCampos().equals(TOTAL)
        );

        avaliacaoNota = null;
        avaliacaoObservacao = null;
    }

    private void calcularTotal() {
        double proventos = 0;
        double descontos = 0;

        try {
            for (CampoDto campo : listaFolha) {
                proventos += nf.parse(campo.getProventos()).doubleValue();
                descontos += nf.parse(campo.getDescontos()).doubleValue();
            }
        } catch(Exception e) {
            System.out.println("Falha na conversão dos valores da folha de pagamento");
        }

        removerCampos(null);

        listaFolha.add(new CampoDto(
                        TOTAL,
                        String.format("%,.2f", proventos - descontos),
                        String.format("%,.2f", proventos),
                        String.format("%,.2f", descontos)
                )
        );

        salarioLiquido.setText(String.format("%,.2f", proventos - descontos));
    }

    public void salvar() {
        try {
            Funcionario funcionario = funcionarioDao.buscarFuncionarioPorCpf(cpf.getText());
            YearMonth competencia = YearMonth.of(ano.getValue(), mes.getValue());
            Pagamento pagamento = pagamentoDao.buscarPorFuncionarioECompetencia(funcionario.getCpf(), competencia);

            Avaliacao avaliacao = avaliacaoDao.salvar(new Avaliacao(null, avaliacaoNota, avaliacaoObservacao, null));

            if (pagamento == null) {
                pagamento = new Pagamento();
            }

            pagamento.setCompetencia(competencia);
            pagamento.setFuncionario(funcionario);
            pagamento.setSalarioLiquido(nf.parse(salarioLiquido.getText()).doubleValue());
            pagamento.setHorasExtras(horaExtra.getValue() + (double) minutoExtra.getValue() / 100);
            pagamento.setHorasFaltas(horaFalta.getValue() + (double) minutoFalta.getValue() / 100);
            pagamento.setAvaliacao(avaliacao);

            if (pagamento.getId() != null) {
                pagamentoDao.atualizar(pagamento);
            } else {
                avaliacao.setPagamento(pagamentoDao.salvar(pagamento));
            }

            salvarLabel.setText("Salvo com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            salvarLabel.setText("Falha ao salvar pagamento.");
        }
    }

    public void abrirAvaliacoes() {
        if (!nome.getText().isEmpty()) {
            loadFXML("avaliacao.fxml", new Stage());
        }
    }
}
