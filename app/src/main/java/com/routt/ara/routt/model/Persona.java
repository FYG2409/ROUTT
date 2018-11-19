package com.routt.ara.routt.model;

public class Persona {
    private String nombre;
    private String apePaat;
    private String apeMat;
    private int edad;
    private String ubicacion;
    private int noHappyFace;
    private int noAngryFace;
    private int noSosoFace;
    private String contraseña;
    private String correo;
    private String fotoPerfil;
    private Boolean rControl;
    private int tipo;

    public Persona(String nombre, String apePaat, String apeMat, int edad, String ubicacion, int noHappyFace, int noAngryFace, int noSosoFace, String contraseña, String correo, String fotoPerfil, Boolean rControl, int tipo) {
        this.nombre = nombre;
        this.apePaat = apePaat;
        this.apeMat = apeMat;
        this.edad = edad;
        this.ubicacion = ubicacion;
        this.noHappyFace = noHappyFace;
        this.noAngryFace = noAngryFace;
        this.noSosoFace = noSosoFace;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fotoPerfil = fotoPerfil;
        this.rControl = rControl;
        this.tipo = tipo;
    }

    public Persona(String nombre, String apePaat, String apeMat, int edad, String ubicacion, int noHappyFace, int noAngryFace, int noSosoFace, String contraseña, String correo, String fotoPerfil, int tipo) {
        this.nombre = nombre;
        this.apePaat = apePaat;
        this.apeMat = apeMat;
        this.edad = edad;
        this.ubicacion = ubicacion;
        this.noHappyFace = noHappyFace;
        this.noAngryFace = noAngryFace;
        this.noSosoFace = noSosoFace;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fotoPerfil = fotoPerfil;
        this.tipo = tipo;
    }

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePaat() {
        return apePaat;
    }

    public void setApePaat(String apePaat) {
        this.apePaat = apePaat;
    }

    public String getApeMat() {
        return apeMat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getNoHappyFace() {
        return noHappyFace;
    }

    public void setNoHappyFace(int noHappyFace) {
        this.noHappyFace = noHappyFace;
    }

    public int getNoAngryFace() {
        return noAngryFace;
    }

    public void setNoAngryFace(int noAngryFace) {
        this.noAngryFace = noAngryFace;
    }

    public int getNoSosoFace() {
        return noSosoFace;
    }

    public void setNoSosoFace(int noSosoFace) {
        this.noSosoFace = noSosoFace;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Boolean getrControl() {
        return rControl;
    }

    public void setrControl(Boolean rControl) {
        this.rControl = rControl;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}