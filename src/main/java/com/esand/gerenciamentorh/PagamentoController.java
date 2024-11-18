package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.dao.AvaliacaoDao;
import com.esand.gerenciamentorh.dao.BeneficioDao;
import com.esand.gerenciamentorh.dao.FuncionarioDao;
import com.esand.gerenciamentorh.dao.PagamentoDao;
import com.esand.gerenciamentorh.dto.CampoDto;
import com.esand.gerenciamentorh.dto.Campos;
import com.esand.gerenciamentorh.entidades.Avaliacao;
import com.esand.gerenciamentorh.entidades.Beneficio;
import com.esand.gerenciamentorh.entidades.Pagamento;
import com.esand.gerenciamentorh.entidades.Funcionario;
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

import static com.esand.gerenciamentorh.Utils.loadFXML;
import static com.esand.gerenciamentorh.Utils.loadModal;

public class PagamentoController {
    @FXML
    private TableColumn<CampoDto, String> camposColuna;
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
    private TextField departamento;
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

    private ObservableList<CampoDto> listaFolha = FXCollections.observableArrayList();
    private BeneficioDao beneficioDao = new BeneficioDao();
    private FuncionarioDao funcionarioDao = new FuncionarioDao();
    private PagamentoDao pagamentoDao = new PagamentoDao();
    private AvaliacaoDao avaliacaoDao = new AvaliacaoDao();
    private static final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
    public static Double avaliacaoNota;
    public static String avaliacaoObservacao;

    public void initialize() throws Exception {
        camposColuna.setCellValueFactory(new PropertyValueFactory<>("campos"));
        proventosColuna.setCellValueFactory(new PropertyValueFactory<>("proventos"));
        descontosColuna.setCellValueFactory(new PropertyValueFactory<>("descontos"));

        iniciarSpinners();
        carregarTodosEmpregados();
    }

    public void salvarHoras() {
        removerCampos(true);

        if (horaExtra.getValue() > 0 || minutoExtra.getValue() > 0) {
            listaFolha.add(new CampoDto(
                    Campos.HORAS_EXTRAS.getDescricao(),
                    horaExtra.getValue().toString(),
                    minutoExtra.getValue().toString()
            ));
        }

        if (horaFalta.getValue() > 0 || minutoFalta.getValue() > 0) {
            listaFolha.add(new CampoDto(
                    Campos.HORAS_FALTAS.getDescricao(),
                    horaFalta.getValue().toString(),
                    minutoFalta.getValue().toString()
            ));
        }

        calcularTotal();
    }

    public void iniciarSpinners() {
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



    public void carregarTodosEmpregados() {
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
        funcionarios.addAll(funcionarioDao.buscarTodos());

        for (Funcionario funcionario : funcionarios) {
            HBox hBox = new HBox();
            Label nomeLabel = new Label(funcionario.getNome() + " " + funcionario.getSobrenome());
            Button selecionarButton = new Button("Selecionar");

            nomeLabel.setMaxWidth(135);

            selecionarButton.setOnAction(e -> carregarFuncionario(funcionario.getCpf()));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            hBox.getChildren().addAll(nomeLabel, spacer, selecionarButton);
            listaNomes.getItems().add(hBox);
        }
    }


    private void carregarFuncionario(String cpf) {
        Funcionario funcionario;

        funcionario = funcionarioDao.carregarFuncionarioComBeneficios(cpf);
        carregarCampos(funcionario);

        nome.setText(funcionario.getNome() + " " + funcionario.getSobrenome());
        this.cpf.setText(funcionario.getCpf());
        departamento.setText(funcionario.getDepartamento().toString());
        cargo.setText(funcionario.getCargo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = funcionario.getDataAdmissao().format(formatter);
        dataAdmissao.setText(dataFormatada);
    }

    private void carregarCampos(Funcionario funcionario) {
        removerCampos(false);
        removerCampos(true);

        listaFolha.add(new CampoDto(
                        Campos.SALARIO_BRUTO.getDescricao(),
                        String.format("%,.2f", funcionario.getSalario()),
                        String.format("%,.2f", 0.00)
                )
        );

        if (!funcionario.getBeneficios().isEmpty()) {
            funcionario.getBeneficios().forEach(x -> {
                listaFolha.add(new CampoDto(
                        x.getTipo(),
                        String.format("%,.2f", x.getValor()),
                        String.format("%,.2f", 0.00)
                ));
            });
        }

        calcularTotal();

        tabelaFolha.setItems(listaFolha);
    }

    private void removerCampos(Boolean verificar) {
        List<Beneficio> beneficios = beneficioDao.buscarTodos();

        if (verificar != null && verificar) {
            listaFolha.removeIf(campo ->
                    campo.getCampos().equals(Campos.HORAS_EXTRAS.getDescricao()) ||
                            campo.getCampos().equals(Campos.HORAS_FALTAS.getDescricao())
            );
        } else if (verificar != null) {
            listaFolha.removeIf(campo ->
                    campo.getCampos().equals(Campos.SALARIO_BRUTO.getDescricao())
            );

            beneficios.forEach(beneficio -> {
                listaFolha.removeIf(campo ->
                        campo.getCampos().equals(beneficio.getTipo())
                );
            });
        }

        listaFolha.removeIf(campo ->
                campo.getCampos().equals(Campos.TOTAL.getDescricao())
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
                        Campos.TOTAL.getDescricao(),
                        String.format("%,.2f", proventos),
                        String.format("%,.2f", descontos)
                )
        );

        salarioLiquido.setText(String.format("%,.2f", proventos - descontos));
    }

    public void salvar() {
        Pagamento pagamentoBanco = pagamentoDao.buscarPorCpf(cpf.getText());
        Avaliacao avaliacao = avaliacaoDao.salvar(new Avaliacao(null, avaliacaoNota, avaliacaoObservacao, null));

        try {
            Pagamento pagamentoNovo = new Pagamento(
                    null,
                    YearMonth.of(ano.getValue(), mes.getValue()),
                    funcionarioDao.buscarPorCpf(this.cpf.getText()),
                    nf.parse(salarioLiquido.getText()).doubleValue(),
                    horaExtra.getValue() + (double) minutoExtra.getValue() / 100,
                    horaFalta.getValue() + (double) minutoFalta.getValue() / 100,
                    avaliacao
            );

            if (pagamentoBanco != null && pagamentoBanco.getCompetencia().equals(YearMonth.of(ano.getValue(), mes.getValue()))) {
                pagamentoDao.atualizarPagamentoPorId(pagamentoBanco.getId(), pagamentoNovo);
            } else {
                avaliacao.setPagamento(pagamentoDao.salvar(pagamentoNovo));
            }

            salvarLabel.setText("Salvo com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            salvarLabel.setText("Falha ao salvar pagamento.");
        }
    }

    public void abrirAvaliacoes() {
        Stage stage = new Stage();
        loadFXML("avaliacao.fxml", stage);
    }
}
