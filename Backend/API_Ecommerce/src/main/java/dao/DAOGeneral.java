package dao;

import java.util.ArrayList;

public interface DAOGeneral<K, T, S> {
    public int agregar(T elemento);

    public int agregarArticuloCarrito(K id_Usuario, K id_Producto);

    public ArrayList<T> consultar();

    public T consultar(K id);

    public T consultarCorreo(S correo);

    public int actualizar(K id, T elemento);

    public int eliminar(K id);

    public int consultarExistencia(K id);

    public int actualizarExistencia(K id, int nuevaCantidad);
}

