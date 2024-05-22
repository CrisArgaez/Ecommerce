package com.example.programacionweb_its_prac1;


public class Carrito {
    private int idCarrito;
    private int idUsuario;
    private int idProducto;
    private String nombre;
    private String descripcion;
    private int precio;
    private int existencia;

    public Carrito() {
        // Constructor vacío
    }

    public Carrito(int idCarrito, int idUsuario, int idProducto, String nombre, String descripcion, int precio, int existencia) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencia = existencia;
    }

    // Getters y Setters
    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}

