module com.esand.gerenciamentorh {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.h2database;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.esand.gerenciamentorh to javafx.fxml;
    opens com.esand.gerenciamentorh.entidades to org.hibernate.orm.core, javafx.base;
    opens com.esand.gerenciamentorh.dto to org.hibernate.orm.core, javafx.base;
    opens com.esand.gerenciamentorh.database to javafx.fxml;


    exports com.esand.gerenciamentorh;
    exports com.esand.gerenciamentorh.database;
}