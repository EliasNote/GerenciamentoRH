package com.esand.gerenciamentorh;

import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VisualizarController {

    @FXML
    protected void exibir() {
        exibirFuncionarios();
    }

    public void exibirFuncionarios() {
        String selectFuncionariosQuery = "SELECT * FROM funcionario";

        try (Connection conn = DBConnect.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectFuncionariosQuery)
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");
                String cpf = rs.getString("cpf");
                double salario = rs.getDouble("salario");

                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Sobrenome: " + sobrenome);
                System.out.println("CPF: " + cpf);
                System.out.println("Salário: " + salario);
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
