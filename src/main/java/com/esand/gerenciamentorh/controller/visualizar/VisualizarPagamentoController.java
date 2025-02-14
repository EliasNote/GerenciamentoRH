package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.service.PagamentoService;
import com.esand.gerenciamentorh.model.dto.PagamentoDto;
import com.esand.gerenciamentorh.model.entidades.Funcionario;
import com.esand.gerenciamentorh.model.entidades.Pagamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.esand.gerenciamentorh.controller.util.Utils.*;

public class VisualizarPagamentoController {

    @FXML private Spinner<Integer> mes, ano;
    @FXML private TableView<PagamentoDto> tabela;
    @FXML private TableColumn<Pagamento, String> nomeColumn, cpfColumn;
    @FXML private TableColumn<Pagamento, Double> salarioColumn, inssColumn, irpfColumn, fgtsColumn, salarioLiquidoColumn;

    private ObservableList<PagamentoDto> funcionarios = FXCollections.observableArrayList();
    private PagamentoService pagamentoService = new PagamentoService();

    public void initialize() {
        mes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, LocalDate.now().getMonthValue()));
        ano.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2000, LocalDate.now().getYear(), LocalDate.now().getYear()));

        configurarColunasTabela();
        carregarPagamentos();
    }

    private void configurarColunasTabela() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salarioBruto"));
        inssColumn.setCellValueFactory(new PropertyValueFactory<>("inss"));
        irpfColumn.setCellValueFactory(new PropertyValueFactory<>("irpf"));
        fgtsColumn.setCellValueFactory(new PropertyValueFactory<>("fgts"));
        salarioLiquidoColumn.setCellValueFactory(new PropertyValueFactory<>("salarioLiquido"));
    }

    public void carregarPagamentos() {
        List<Pagamento> pagamentos =  pagamentoService.buscarPagamentoPorCompetencia(YearMonth.of(ano.getValue(), mes.getValue())));


        for (Pagamento pagamento : pagamentos) {
            funcionarios.add(
                    new PagamentoDto(
                            pagamento.getNome(),
                            pagamento.getCpf(),
                            Double.parseDouble(getTextoFormatado(pagamento.getSalarioBruto())),
                            Double.parseDouble(getTextoFormatado(pagamento.getInss())),
                            Double.parseDouble(getTextoFormatado(pagamento.getIrpf())),
                            Double.parseDouble(getTextoFormatado(pagamento.getFgts())),
                            Double.parseDouble(getTextoFormatado(pagamento.getSalarioLiquido()))
                    )
            );
        }

        tabela.setItems(funcionarios);
    }

    @FXML
    private void excluir() {
        Pagamento pagamentoSelecionado = pagamentoService.buscarPagamentoPorCompetencia();
        if (pagamentoSelecionado != null) {
            pagamentoService.deletar(pagamentoSelecionado);
            funcionarios.remove(pagamentoSelecionado);
        }
    }
}
