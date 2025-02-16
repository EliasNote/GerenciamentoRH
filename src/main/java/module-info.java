module com.esand.gerenciamentorh {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.h2database;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jbcrypt;
    requires org.hibernate.validator;
    requires jakarta.validation;

    opens com.esand.gerenciamentorh to javafx.fxml;
    opens com.esand.gerenciamentorh.model.entidades to org.hibernate.orm.core, javafx.base;
    opens com.esand.gerenciamentorh.model.dto to org.hibernate.orm.core, javafx.base;
    opens com.esand.gerenciamentorh.model.dao to javafx.fxml;


    exports com.esand.gerenciamentorh;
    exports com.esand.gerenciamentorh.model.dao;
    exports com.esand.gerenciamentorh.controller;
    opens com.esand.gerenciamentorh.controller to javafx.fxml;
    exports com.esand.gerenciamentorh.controller.cadastro;
    opens com.esand.gerenciamentorh.controller.cadastro to javafx.fxml;
    exports com.esand.gerenciamentorh.controller.editar;
    opens com.esand.gerenciamentorh.controller.editar to javafx.fxml;
    exports com.esand.gerenciamentorh.controller.visualizar;
    opens com.esand.gerenciamentorh.controller.visualizar to javafx.fxml;
    exports com.esand.gerenciamentorh.controller.util;
    opens com.esand.gerenciamentorh.controller.util to javafx.fxml;
}