package com.esand.gerenciamentorh.dto;

public class FolhaPagamentoDto {
    private String campos;
    private String proventos;
    private String descontos;

    public FolhaPagamentoDto(String campos, String proventos, String descontos) {
        this.campos = campos;
        this.proventos = proventos;
        this.descontos = descontos;
    }

    public String getCampos() {
        return campos;
    }

    public void setCampos(String campos) {
        this.campos = campos;
    }

    public String getProventos() {
        return proventos;
    }

    public void setProventos(String proventos) {
        this.proventos = proventos;
    }

    public String getDescontos() {
        return descontos;
    }

    public void setDescontos(String descontos) {
        this.descontos = descontos;
    }
}
