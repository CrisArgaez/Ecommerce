package dao;

import java.util.List;

public abstract class DAOGeneral<K, T> {
    public abstract int registrarUsuario(T objeto);
    public abstract T validacionUsuario(K clavePrimaria);
    public abstract List<T> mostrarInformacion();
    public abstract T obtenerPorId(int id);
    public abstract boolean insertar(T objeto);
    public  abstract boolean actualizarCantidad(T objeto);

    public abstract boolean eliminarProductoCarrito(int id);

}
