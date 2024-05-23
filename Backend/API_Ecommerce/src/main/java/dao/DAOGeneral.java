package dao;

import com.example.programacionweb_its_prac1.User;

import java.util.List;

public abstract class DAOGeneral<K, T> {
    public abstract int registrarUsuario(T objeto);

    abstract User validacionUsuario(String email);

    public abstract List<T> mostrarInformacion();

    public abstract T obtenerPorId(int id);
    public  abstract boolean actualizarCantidad(T objeto);

    public abstract boolean eliminarProductoCarrito(int id);

}
