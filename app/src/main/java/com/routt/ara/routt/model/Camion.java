package com.routt.ara.routt.model;

public class Camion {
    private String idPersona;
    private String fotoCamion;
    private String marca;
    private int modelo;
    private Boolean noEconomico;
    private String placa;

    public Camion(String idPersona, String fotoCamion, String marca, int modelo, Boolean noEconomico, String placa) {
        this.idPersona = idPersona;
        this.fotoCamion = fotoCamion;
        this.marca = marca;
        this.modelo = modelo;
        this.noEconomico = noEconomico;
        this.placa = placa;
    }

    public Camion() {
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getFotoCamion() {
        return fotoCamion;
    }

    public void setFotoCamion(String fotoCamion) {
        this.fotoCamion = fotoCamion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public Boolean getNoEconomico() {
        return noEconomico;
    }

    public void setNoEconomico(Boolean noEconomico) {
        this.noEconomico = noEconomico;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}