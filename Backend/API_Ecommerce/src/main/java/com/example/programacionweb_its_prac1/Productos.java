package com.example.programacionweb_its_prac1;

public class Productos {
    private int id;
    private String nombre;
    private String urlImagen;
    private String especificacion;
    private String descripcion;
    private int precio;
    private int existencia;

    private String galeriaFotos;

<<<<<<< HEAD
=======
    public Productos() {
        // Constructor vacío
    }

>>>>>>> 1a4311bef9e3bbe28d0a4fbc553ae8c706e9e244
    public Productos(int id, String nombre, String urlImagen, String especificacion, String descripcion, int precio, int existencia, String galeriaFotos) {
        this.id = id;
        this.nombre = nombre;
        this.urlImagen = urlImagen;
        this.especificacion = especificacion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencia = existencia;
        this.galeriaFotos = galeriaFotos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getGaleriaFotos() {
        return galeriaFotos;
    }

    public void setGaleriaFotos(String galeriaFotos) {
        this.galeriaFotos = galeriaFotos;
    }
}