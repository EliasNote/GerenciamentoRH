package com.esand.gerenciamentorh.controller.cadastro;

import com.esand.gerenciamentorh.controller.cadastro.calculo.Calculadora;
import com.esand.gerenciamentorh.controller.cadastro.calculo.CalculoEnum;
import com.esand.gerenciamentorh.controller.cadastro.calculo.FolhaPagamento;
import com.esand.gerenciamentorh.controller.service.FuncionarioService;
import com.esand.gerenciamentorh.controller.service.PagamentoService;
import com.esand.gerenciamentorh.model.dto.CampoDto;
import com.esand.gerenciamentorh.model.entidades.Pagamento;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static com.esand.gerenciamentorh.controller.util.Utils.loadFXML;
import static com.esand.gerenciamentorh.controller.EnumView.*;

public class CadastroPagamentoController {

    private static final NumberFormat NF = NumberFormat.getInstance(new Locale("pt", "BR"));

    @FXML private TableColumn<CampoDto, String> camposColuna, informadoColuna, proventosColuna, descontosColuna;
    @FXML private TableView<CampoDto> tabelaFolha;
    @FXML private TextField nome, cpf, cargo, dataAdmissao;
    @FXML private ListView<HBox> listaNomes;
    @FXML private Spinner<Integer> horaExtra, horaFalta, minutoExtra, minutoFalta, ano, mes;
    @FXML private Label salvarLabel, salarioLiquido, lbInss, lbIrpf, lbFgts;

    private ObservableList<CampoDto> listaFolha = FXCollections.observableArrayList();

    protected static Double avaliacaoNota;
    protected static String avaliacaoObservacao;

    public static final String SALARIO_BRUTO = "Salário Bruto";
    public static final String HORAS_EXTRAS = "Horas Extras";
    public static final String HORAS_FALTAS = "Horas Faltas";
    public static final String TOTAL = "Total";
    private static final String VALUE_FORMAT = "%,.2f";

    private final PagamentoService pagamentoService = new PagamentoService();
    private final FuncionarioService funcionarioService = new FuncionarioService();
    private final Calculadora calculadora = new Calculadora();


    public void initialize() throws Exception {
        configurarColunasTabela();
        iniciarSpinners();
        carregarFuncionarios();
    }

    private void configurarColunasTabela() {
        camposColuna.setCellValueFactory(new PropertyValueFactory<>("campos"));
        informadoColuna.setCellValueFactory(new PropertyValueFactory<>("informado"));
        proventosColuna.setCellValueFactory(new PropertyValueFactory<>("proventos"));
        descontosColuna.setCellValueFactory(new PropertyValueFactory<>("descontos"));
    }

    public void salvarHoras() {
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf.getText());

        listaFolha.removeIf(campo ->
                campo.getCampos().equals(HORAS_EXTRAS) ||
                campo.getCampos().equals(HORAS_FALTAS) ||
                campo.getCampos().equals(TOTAL)
        );

        adicionarHorasExtras(funcionario);
        adicionarHorasFaltas(funcionario);

        calcularImpostos(funcionario);
        calcularTotal();
    }

    private void adicionarHorasExtras(Funcionario funcionario) {
        if (horaExtra.getValue() > 0 || minutoExtra.getValue() > 0) {
            double valor = calculadora.calcularHorasExtras(funcionario.getSalario(), horaExtra.getValue(), minutoExtra.getValue());
            listaFolha.add(new CampoDto(
                    HORAS_EXTRAS,
                    horaExtra.getValue().toString() + ":" + minutoExtra.getValue().toString(),
                    getTextoFormatado(valor),
                    getTextoFormatado(0)
            ));
        }
    }

    private void adicionarHorasFaltas(Funcionario funcionario) {
        if (horaFalta.getValue() > 0 || minutoFalta.getValue() > 0) {
            double valor = calculadora.calcularHorasFaltas(funcionario.getSalario(), horaFalta.getValue(), minutoFalta.getValue());
            listaFolha.add(new CampoDto(
                    HORAS_FALTAS,
                    horaFalta.getValue().toString() + ":" + minutoFalta.getValue().toString(),
                    getTextoFormatado(0),
                    getTextoFormatado(valor)

            ));
        }
    }

    private void iniciarSpinners() {
        configurarSpinner(horaExtra, 0, 23, 0);
        configurarSpinner(horaFalta, 0, 23, 0);
        configurarSpinner(minutoExtra, 0, 59, 0);
        configurarSpinner(minutoFalta, 0, 59, 0);
        configurarSpinner(mes, 1, 12, LocalDate.now().getMonthValue());
        configurarSpinner(ano, 1900, LocalDate.now().getYear(), LocalDate.now().getYear());
    }

    private void configurarSpinner(Spinner<Integer> spinner, int i, int i1, int i2) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(i, i1, i2));
    }

    private void carregarFuncionarios() {
        listaNomes.getItems().addAll(
                funcionarioService.criarItensListaFuncionarios(this::carregarFuncionario)
        );
    }

    private void carregarFuncionario(String cpf) {
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        atualizarCamposFuncionario(funcionario);
        carregarCampos(funcionario);
    }

    private void atualizarCamposFuncionario(Funcionario funcionario) {
        nome.setText(funcionario.getNome() + " " + funcionario.getSobrenome());
        cpf.setText(funcionario.getCpf());
        cargo.setText(funcionario.getCargo());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = funcionario.getDataAdmissao().format(formatter);
        dataAdmissao.setText(dataFormatada);
    }

    private void carregarCampos(Funcionario funcionario) {
        removerCampos(true);

        listaFolha.add(new CampoDto(
                        SALARIO_BRUTO,
                        "220:00",
                        getTextoFormatado(funcionario.getSalario()),
                        getTextoFormatado(0)
                )
        );

        adicionarBeneficios(funcionario);
        calcularImpostos(funcionario);
        calcularTotal();
        tabelaFolha.setItems(listaFolha);
    }

    private void adicionarBeneficios(Funcionario funcionario) {
        if (!funcionario.getBeneficios().isEmpty()) {
            funcionario.getBeneficios().forEach(x -> {
                listaFolha.add(new CampoDto(
                        x.getTipo(),
                        getTextoFormatado(x.getValor()),
                        getTextoFormatado(x.getValor()),
                        getTextoFormatado(0)
                ));
            });
        }
    }

    private void calcularImpostos(Funcionario funcionario) {
        double baseCalculoImposto = baseCalculoImposto();

        Map<CalculoEnum, Double> impostos = calculadora.calcularImpostos(baseCalculoImposto);

        impostos.forEach((tipo, valor) -> {
            if (valor != 0) {
                listaFolha.add(
                        new CampoDto(
                                tipo.toString(),
                                getTextoFormatado((valor*100)/baseCalculoImposto),
                                getTextoFormatado(0),
                                getTextoFormatado(valor)
                        )
                );
            }

            atualizarLabelsImpostos(tipo, valor);
        });

        tabelaFolha.setItems(listaFolha);
    }

    private void atualizarLabelsImpostos(CalculoEnum tipo, Double valor) {
        switch (tipo) {
            case INSS -> lbInss.setText(getTextoFormatado(valor));
            case IRPF -> lbIrpf.setText(getTextoFormatado(valor));
            case FGTS -> lbFgts.setText(getTextoFormatado(valor));
        }
    }


    private double baseCalculoImposto() {
        double resultado = 0.00;

        try {
            for (CampoDto campo : listaFolha) {
                if (campo.getCampos().contains(SALARIO_BRUTO) ||
                    campo.getCampos().contains(HORAS_EXTRAS) ||
                    campo.getCampos().contains(HORAS_FALTAS)
                ) {
                    resultado += NF.parse(campo.getProventos()).doubleValue();
                    resultado -= NF.parse(campo.getDescontos()).doubleValue();
                }
            }
        } catch(Exception e) {
            System.out.println("Falha na conversão dos valores da folha de pagamento");
        }

        return resultado;
    }

    private void removerCampos(boolean valor) {
        if (valor) {
            listaFolha.clear();
        } else {
            listaFolha.removeIf(c -> TOTAL.equals(c.getCampos()));
        }

        avaliacaoNota = null;
        avaliacaoObservacao = null;
    }

    private void calcularTotal() {
        FolhaPagamento folha = new FolhaPagamento(listaFolha);
        double proventos = folha.getTotalProventos();
        double descontos = folha.getTotalDescontos();

        salarioLiquido.setText(getTextoFormatado(proventos - descontos));

        listaFolha.add(new CampoDto(
                        TOTAL,
                        getTextoFormatado(proventos - descontos),
                        getTextoFormatado(proventos),
                        getTextoFormatado(descontos)
                )
        );

        salarioLiquido.setText(getTextoFormatado(proventos - descontos));
    }

    private String getTextoFormatado(double valor) {
        return String.format(VALUE_FORMAT, valor);
    }

    public void salvar() {
        try {
            Pagamento pagamento = pagamentoService.criarPagamento(
                    cpf.getText(),
                    YearMonth.of(ano.getValue(), mes.getValue()),
                    Double.parseDouble(salarioLiquido.getText().replace(",", "")),
                    horaExtra.getValue(),
                    minutoExtra.getValue(),
                    horaFalta.getValue(),
                    minutoFalta.getValue()
            );

            salvarLabel.setText(pagamentoService.salvarPagamento(pagamento));
        } catch (Exception e) {
            salvarLabel.setText("Erro ao salvar: " + e.getMessage());
        }
    }

    public void abrirAvaliacoes() {
        if (!nome.getText().isEmpty()) {
            loadFXML(PAGAMENTO_AVALIACAO.getPath(), new Stage());
        }
    }
}
