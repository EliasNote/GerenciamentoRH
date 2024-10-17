module com.esand.gerenciamentorh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;
    requires com.h2database;

    opens com.esand.gerenciamentorh to javafx.fxml;
    exports com.esand.gerenciamentorh;
}