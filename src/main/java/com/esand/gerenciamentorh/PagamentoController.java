package com.esand.gerenciamentorh;

import com.esand.gerenciamentorh.database.DataBase;
import com.esand.gerenciamentorh.dto.CampoDto;
import com.esand.gerenciamentorh.dto.Campos;
import com.esand.gerenciamentorh.entidades.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.converter.DateTimeStringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import static com.esand.gerenciamentorh.Utils.showErrorMessage;

public class PagamentoController {

    @FXML
    public TableColumn<CampoDto, String> camposColuna;
    @FXML
    public TableColumn<CampoDto, String> proventosColuna;
    @FXML
    public TableColumn<CampoDto, String> descontosColuna;
    @FXML
    private DatePicker competenciaData;
    @FXML
    private Button competenciaButton;
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
    private Button calcularButton;
    @FXML
    private Spinner<Integer> hora1;
    @FXML
    private Spinner<Integer> hora2;
    @FXML
    private Spinner<Integer> minuto1;
    @FXML
    private Spinner<Integer> minuto2;

    private ObservableList<CampoDto> listaFolha = FXCollections.observableArrayList();

    public void initialize() throws Exception {
        camposColuna.setCellValueFactory(new PropertyValueFactory<CampoDto, String>("campos"));
        proventosColuna.setCellValueFactory(new PropertyValueFactory<CampoDto, String>("proventos"));
        descontosColuna.setCellValueFactory(new PropertyValueFactory<CampoDto, String>("descontos"));

        iniciarSpinners();
        carregarTodosEmpregados();
    }

    public void salvarHoras() {
        listaFolha.removeIf(campo ->
                campo.getCampos().equals(Campos.HORAS_EXTRAS.getDescricao()) ||
                campo.getCampos().equals(Campos.HORAS_FALTAS.getDescricao())
        );

        if (hora1.getValue() > 0 || minuto1.getValue() > 0) {
            listaFolha.add(new CampoDto(
                    Campos.HORAS_EXTRAS.getDescricao(),
                    hora1.getValue().toString(),
                    minuto1.getValue().toString()
            ));
        }

        if (hora2.getValue() > 0 || minuto2.getValue() > 0) {
            listaFolha.add(new CampoDto(
                    Campos.HORAS_FALTAS.getDescricao(),
                    hora2.getValue().toString(),
                    minuto2.getValue().toString()
            ));
        }
    }

    public void iniciarSpinners() {
        SpinnerValueFactory<Integer> hora1Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> hora2Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> minuto1Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> minuto2Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        hora1.setValueFactory(hora1Factory);
        hora2.setValueFactory(hora2Factory);
        minuto1.setValueFactory(minuto1Factory);
        minuto2.setValueFactory(minuto2Factory);
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

                nomeLabel.setMaxWidth(135);

                selecionarButton.setOnAction(e -> carregarFuncionario(funcionario.getCpf()));

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                hBox.getChildren().addAll(nomeLabel, spacer, selecionarButton);
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
            listaFolha.removeIf(campo ->
                    campo.getCampos().equals(Campos.SALARIO_BRUTO.getDescricao())
            );

            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f WHERE f.cpf = :cpf", Funcionario.class);
            query.setParameter("cpf", cpf);
            funcionario = query.getSingleResult();
            carregarCampoFolha(funcionario);

            nome.setText(funcionario.getNome() + " " + funcionario.getSobrenome());
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
            gerarCampos(funcionario);
            tabelaFolha.setItems(listaFolha);
        } catch (Exception e) {
            showErrorMessage("Erro ao carregar dados da folha: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void gerarCampos(Funcionario funcionario) {
        listaFolha.add(new CampoDto(
                Campos.SALARIO_BRUTO.getDescricao(),
                funcionario.getSalario().toString(),
                "0,00"
            )
        );

        if (!funcionario.getBeneficios().isEmpty()) {
            funcionario.getBeneficios().forEach(x -> {
                listaFolha.add(new CampoDto(
                        x.getTipo(),
                        x.getValor().toString(),
                        "0,00"
                ));
            });
        }
    }
}