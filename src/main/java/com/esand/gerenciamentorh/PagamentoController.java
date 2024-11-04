package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.dto.FolhaPagamentoDto;
import com.esand.gerenciamentorh.entidades.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;

import static com.esand.gerenciamentorh.Utils.showErrorMessage;

public class PagamentoController {

    @FXML
    public TableColumn<FolhaPagamentoDto, String> camposColuna;
    @FXML
    public TableColumn<FolhaPagamentoDto, String> proventosColuna;
    @FXML
    public TableColumn<FolhaPagamentoDto, String> descontosColuna;
    @FXML
    private DatePicker competenciaData;
    @FXML
    private Button competenciaButton;
    @FXML
    private TableView<FolhaPagamentoDto> tabelaFolha;
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
    private Button calcularButton;

    private ObservableList<FolhaPagamentoDto> listaFolha = FXCollections.observableArrayList();

    public void initialize() {
        camposColuna.setCellValueFactory(new PropertyValueFactory<FolhaPagamentoDto, String>("campos"));
        proventosColuna.setCellValueFactory(new PropertyValueFactory<FolhaPagamentoDto, String>("proventos"));
        descontosColuna.setCellValueFactory(new PropertyValueFactory<FolhaPagamentoDto, String>("descontos"));

        carregarTodosEmpregados();
    }



    public void carregarTodosEmpregados() {
        EntityManager em = DataBase.getEntityManager();
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();

        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
            funcionarios.addAll(query.getResultList());

            for (Funcionario funcionario : funcionarios) {
                HBox hBox = new HBox();
                Label nomeLabel = new Label(funcionario.getNome() + " " + funcionario.getSobrenome());
                Button selecionarButton = new Button("Selecionar");

                selecionarButton.setOnAction(e -> carregarFuncionario(funcionario.getCpf()));

                hBox.getChildren().addAll(nomeLabel, selecionarButton);
                listaNomes.getItems().add(hBox);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }

    }


    private void carregarFuncionario(String cpf) {
        EntityManager em = DataBase.getEntityManager();
        Funcionario funcionario = null;

        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f WHERE f.cpf = :cpf", Funcionario.class);
            query.setParameter("cpf", cpf);
            funcionario = query.getSingleResult();
            carregarCampoFolha(funcionario);

            nome.setText(funcionario.getNome());
            this.cpf.setText(funcionario.getCpf());
            departamento.setText(funcionario.getDepartamento().toString());
            cargo.setText(funcionario.getCargo());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = funcionario.getDataAdmissao().format(formatter);
            dataAdmissao.setText(dataFormatada);
        } catch (NoResultException e) {
            showErrorMessage("Nenhum funcionário encontrado com o CPF: " + cpf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private void carregarCampoFolha(Funcionario funcionario) {
        EntityManager em = DataBase.getEntityManager();

        try {
            listaFolha.add(new FolhaPagamentoDto(
                    "Salário bruto",
                    funcionario.getSalario().toString(),
                    "0,00"
            ));

            if (!funcionario.getBeneficios().isEmpty()) {
                funcionario.getBeneficios().forEach(x -> {
                    listaFolha.add(new FolhaPagamentoDto(
                            x.getTipo(),
                            x.getValor().toString(),
                            "0,00"

                    ));
                });
            }

            tabelaFolha.setItems(listaFolha);
        } catch (Exception e) {
            showErrorMessage("Erro ao carregar dados da folha: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
