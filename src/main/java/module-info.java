module com.esand.gerenciamentorh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;

    opens com.esand.gerenciamentorh to javafx.fxml;
    opens com.esand.gerenciamentorh.entidades to javafx.base;
    exports com.esand.gerenciamentorh;
}