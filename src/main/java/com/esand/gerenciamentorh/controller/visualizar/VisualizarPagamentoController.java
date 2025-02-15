package com.esand.gerenciamentorh.controller.visualizar;

import com.esand.gerenciamentorh.controller.service.PagamentoService;
import com.esand.gerenciamentorh.model.dto.PagamentoDto;
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
    @FXML private TableColumn<Pagamento, String> salarioColumn, inssColumn, irpfColumn, fgtsColumn, salarioLiquidoColumn;

    private ObservableList<PagamentoDto> funcionarios = FXCollections.observableArrayList();
    private PagamentoService pagamentoService = new PagamentoService();

    public void initialize() {
        mes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, LocalDate.now().getMonthValue()));
        ano.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2000, LocalDate.now().getYear(), LocalDate.now().getYear()));

        configurarColunasTabela();
        carregarPagamentos();

        mes.valueProperty().addListener((obs, oldValue, newValue) -> carregarPagamentos());
        ano.valueProperty().addListener((obs, oldValue, newValue) -> carregarPagamentos());
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
        funcionarios.clear();

        List<Pagamento> pagamentos =  pagamentoService.buscarPagamentoPorCompetencia(YearMonth.of(ano.getValue(), mes.getValue()));

        for (Pagamento pagamento : pagamentos) {
            funcionarios.add(
                    new PagamentoDto(
                            pagamento.getNome(),
                            pagamento.getCpf(),
                            setTextoFormatado(pagamento.getSalarioBruto()),
                            setTextoFormatado(pagamento.getInss()),
                            setTextoFormatado(pagamento.getIrpf()),
                            setTextoFormatado(pagamento.getFgts()),
                            setTextoFormatado(pagamento.getSalarioLiquido())
                    )
            );
        }

        tabela.setItems(funcionarios);
    }

    @FXML
    private void excluir() {
        PagamentoDto dto = tabela.getSelectionModel().getSelectedItem();

        if (dto != null) {
            pagamentoService.deletar(dto.getCpf());
            funcionarios.remove(dto);
        }
    }
}
