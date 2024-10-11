module com.esand.gerenciamentorh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;

    opens com.esand.gerenciamentorh to javafx.fxml;
    exports com.esand.gerenciamentorh;
}