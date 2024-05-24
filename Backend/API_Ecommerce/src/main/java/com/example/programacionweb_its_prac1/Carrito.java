package com.example.programacionweb_its_prac1;


public class Carrito {
    private int idCarrito;
    private int idUsuario;
    private int idProducto;

    private int cantidadCompra;

    public Carrito(int idCarrito, int idUsuario, int idProducto, int cantidadCompra) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.cantidadCompra = cantidadCompra;
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

    public int getCantidadCompra(){return cantidadCompra;}

    public void setCantidadCompra(int cantidadCompra){
        this.cantidadCompra = cantidadCompra;
    }
}

