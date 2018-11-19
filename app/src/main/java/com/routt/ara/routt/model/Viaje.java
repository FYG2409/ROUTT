package com.routt.ara.routt.model;

public class Viaje {
    private String idViaje;
    private String correoOfertante;
    private String nombreLugarSalida;
    private String nombreLugarLlegada;
    private int pago;
    private String imagenFondo;
    private String tipoCaja;
    private String carga;
    private int cantidadCarga;
    private Boolean rControl;
    private Boolean dobleRemolque;
    private Boolean disponible;
    private String fechaApoxSalida;
    private String ubicacionLugarSalida;
    private String ubicacionLugarLlegada;
    private String correoTrailero;

    public Viaje(String idViaje, String correoOfertante, String nombreLugarSalida, String nombreLugarLlegada, int pago, String imagenFondo, String tipoCaja, String carga, int cantidadCarga, Boolean rControl, Boolean dobleRemolque, Boolean disponible, String fechaApoxSalida, String ubicacionLugarSalida, String ubicacionLugarLlegada, String correoTrailero) {
        this.idViaje = idViaje;
        this.correoOfertante = correoOfertante;
        this.nombreLugarSalida = nombreLugarSalida;
        this.nombreLugarLlegada = nombreLugarLlegada;
        this.pago = pago;
        this.imagenFondo = imagenFondo;
        this.tipoCaja = tipoCaja;
        this.carga = carga;
        this.cantidadCarga = cantidadCarga;
        this.rControl = rControl;
        this.dobleRemolque = dobleRemolque;
        this.disponible = disponible;
        this.fechaApoxSalida = fechaApoxSalida;
        this.ubicacionLugarSalida = ubicacionLugarSalida;
        this.ubicacionLugarLlegada = ubicacionLugarLlegada;
        this.correoTrailero = correoTrailero;
    }

    public Viaje() {
    }

    public String getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }

    public String getCorreoOfertante() {
        return correoOfertante;
    }

    public void setCorreoOfertante(String correoOfertante) {
        this.correoOfertante = correoOfertante;
    }

    public String getNombreLugarSalida() {
        return nombreLugarSalida;
    }

    public void setNombreLugarSalida(String nombreLugarSalida) {
        this.nombreLugarSalida = nombreLugarSalida;
    }

    public String getNombreLugarLlegada() {
        return nombreLugarLlegada;
    }

    public void setNombreLugarLlegada(String nombreLugarLlegada) {
        this.nombreLugarLlegada = nombreLugarLlegada;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public String getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(String imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    public String getTipoCaja() {
        return tipoCaja;
    }

    public void setTipoCaja(String tipoCaja) {
        this.tipoCaja = tipoCaja;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public int getCantidadCarga() {
        return cantidadCarga;
    }

    public void setCantidadCarga(int cantidadCarga) {
        this.cantidadCarga = cantidadCarga;
    }

    public Boolean getrControl() {
        return rControl;
    }

    public void setrControl(Boolean rControl) {
        this.rControl = rControl;
    }

    public Boolean getDobleRemolque() {
        return dobleRemolque;
    }

    public void setDobleRemolque(Boolean dobleRemolque) {
        this.dobleRemolque = dobleRemolque;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getFechaApoxSalida() {
        return fechaApoxSalida;
    }

    public void setFechaApoxSalida(String fechaApoxSalida) {
        this.fechaApoxSalida = fechaApoxSalida;
    }

    public String getUbicacionLugarSalida() {
        return ubicacionLugarSalida;
    }

    public void setUbicacionLugarSalida(String ubicacionLugarSalida) {
        this.ubicacionLugarSalida = ubicacionLugarSalida;
    }

    public String getUbicacionLugarLlegada() {
        return ubicacionLugarLlegada;
    }

    public void setUbicacionLugarLlegada(String ubicacionLugarLlegada) {
        this.ubicacionLugarLlegada = ubicacionLugarLlegada;
    }

    public String getCorreoTrailero() {
        return correoTrailero;
    }

    public void setCorreoTrailero(String correoTrailero) {
        this.correoTrailero = correoTrailero;
    }
}