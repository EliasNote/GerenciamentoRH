package com.esand.gerenciamentorh.controller.util;

public enum EnumView {
    FXML_PATH("/view/"),

    LOGIN("login/login.fxml"),
    STYLE("Style.css"),
    ICON("/imagens/monitor.png"),

    PRINCIPAL("principal.fxml"),

    FUNCIONARIO_VISUALIZAR("funcionario/visualizar.fxml"),
    FUNCIONARIO_EDITAR("funcionario/editar.fxml"),
    FUNCIONARIO_CADASTRO("funcionario/cadastro.fxml"),

    PAGAMENTO_CADASTRO("pagamento/cadastro.fxml"),
    PAGAMENTO_VISUALIZAR("pagamento/visualizar.fxml"),
    PAGAMENTO_AVALIACAO("pagamento/avaliacao.fxml"),

    BENEFICIO_VISUALIZAR("beneficio/visualizar.fxml"),
    BENEFICIO_CADASTRO("beneficio/cadastro.fxml"),
    BENEFICIO_EDITAR("beneficio/editar.fxml"),

    LOGIN_CADASTRO("login/cadastro.fxml");

    public final String path;

    EnumView(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
