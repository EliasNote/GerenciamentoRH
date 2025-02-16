package com.esand.gerenciamentorh.controller.util;

import com.esand.gerenciamentorh.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.esand.gerenciamentorh.controller.util.EnumView.*;

public class Utils {
    private static final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
    private static final String TITULO = "Gerenciamento de Funcionários";
    private static final String VALUE_FORMAT = "%,.2f";

    public static String setTextoFormatado(double valor) {
        return String.format(VALUE_FORMAT, valor);
    }

    public static double getTextoFormatado(String textoFormatado) {
        try {
            return nf.parse(textoFormatado).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao converter texto formatado para número", e);
        }
    }

    public static void loadChildrenFXML(Pane contentPane, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Utils.class.getResource(FXML_PATH.getPath() + fxml));
            Pane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadFXML(String fxml, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(FXML_PATH.getPath() + fxml));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(new Image(Main.class.getResourceAsStream(ICON.getPath())));
            stage.setTitle(TITULO);
            stage.setScene(scene);
            stage.show();
            centerStage(stage);
        } catch (Exception e) {
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
