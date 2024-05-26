package dao;

import java.util.ArrayList;

public interface DAOGeneral<K, T, S> {
    public int agregar(T elemento);

    public int agregarArticuloCarrito(K id_Usuario, K id_Producto);

    public ArrayList<T> consultar();

    public ArrayList<T> consultar(K id);

    public T consultarCorreo(S correo);//Usado para verificacion del login

    public int eliminar(K id_Usuario, K id_Producto);

    public int eliminarCarritoUsuario(K id_Usuario);

    public int verificarRepetido(K id_Usuario, K id_Producto);

    public int consultarExistencia(K id);

    public int actualizarExistencia(K id, int nuevaCantidad);

    public int actualizarCantidadCompra(K cantidadCompra, K id_Usuario, K id_Producto);
}
