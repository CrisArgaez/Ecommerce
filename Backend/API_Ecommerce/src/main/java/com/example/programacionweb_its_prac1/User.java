package com.example.programacionweb_its_prac1;

public class User {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String correoelectronico;
    private String pass;

    public User(Integer id, String nombre,String apellidos, String correoelectronico, String pass) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoelectronico = correoelectronico;
        this.pass = pass;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
