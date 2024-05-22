package com.example.programacionweb_its_prac1;

public class User {
    private Integer id;
    private String nombre;
    private String correoelectronico;
    private String contraseña;

    public User(Integer id, String nombre, String correoelectronico, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.correoelectronico = correoelectronico;
        this.contraseña = contraseña;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
