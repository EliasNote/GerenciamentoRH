package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.database.DataBase;
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
    public TableColumn camposColuna;
    @FXML
    public TableColumn proventosColuna;
    @FXML
    public TableColumn descontosColuna;
    @FXML
    private DatePicker competenciaData;
    @FXML
    private Button competenciaButton;
    @FXML
    private TableView<String> tabelaFolha;
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

    private ObservableList<String[]> listaFolha;

    public void initialize() {
        camposColuna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        proventosColuna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        descontosColuna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));

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

    private void carregarCampoFolha() {
        EntityManager em = DataBase.getEntityManager();
        Funcionario funcionario = null;

        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f WHERE f.cpf = :cpf", Funcionario.class);
            query.setParameter("cpf", cpf);
            funcionario = query.getSingleResult();

        } catch (NoResultException e) {
            showErrorMessage("Nenhum funcionário encontrado com o CPF: " + cpf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
