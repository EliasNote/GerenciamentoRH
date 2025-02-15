package com.esand.gerenciamentorh.model.dto;

public class CalculoDto {
    private String campos;
    private String informado;
    private String proventos;
    private String descontos;

    public CalculoDto() {
    }

    public CalculoDto(String campos, String informado, String proventos, String descontos) {
        this.campos = campos;
        this.informado = informado;
        this.proventos = proventos;
        this.descontos = descontos;
    }

    public String getCampos() {
        return campos;
    }

    public void setCampos(String campos) {
        this.campos = campos;
    }

    public String getInformado() {
        return informado;
    }

    public void setInformado(String informado) {
        this.informado = informado;
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
