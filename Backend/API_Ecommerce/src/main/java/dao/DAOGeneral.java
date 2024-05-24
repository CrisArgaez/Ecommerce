package dao;

import java.util.ArrayList;

public interface DAOGeneral<K, T, S> {
    public int agregar(T elemento);

    public ArrayList<T> consultar();

    public T consultar(K id);

    public T consultarCorreo(S correo);

    public int actualizar(K id, T elemento);

    public int eliminar(K id);
}
