package com.esand.gerenciamentorh.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {

    private static final String PATH = "/com/esand/gerenciamentorh/view/";

    public static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void loadChildrenFXML(Pane contentPane, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Utils.class.getResource(PATH + fxml));
            Pane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFXML(String fxml, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(PATH + fxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            centerStage(stage);
            stage.show();
        } catch (RuntimeException e) {
            showErrorMessage("Erro ao carregar a interface." + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void centerStage(Stage stage) {
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        double windowWidth = stage.getWidth();
        double windowHeight = stage.getHeight();

        double x = (screenWidth - windowWidth) / 2;
        double y = (screenHeight - windowHeight) / 2;

        stage.setX(x);
        stage.setY(y);
    }
}
