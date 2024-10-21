package com.backace.ace.model;

public class LoginDto {

    public LoginDto() {
    }

    private String nombre;

    public LoginDto(String nombre, String correo, String cedula) {
        this.nombre = nombre;
        this.correo = correo;
        this.cedula = cedula;
    }

    private String correo;
    private String cedula;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
