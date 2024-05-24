package com.example.programacionweb_its_prac1;

public class User {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String correoelectronico;
    private String password;

    public User(Integer id, String nombres, String apellidos, String correoelectronico, String password) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correoelectronico = correoelectronico;
        this.password = password;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }

    public String getPassword() {
        return password;
    }
}
