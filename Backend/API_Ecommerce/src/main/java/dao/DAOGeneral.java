package dao;

import com.example.programacionweb_its_prac1.User;
import java.util.List;
import java.util.ArrayList;

public abstract class DAOGeneral<K, T> {
    public abstract int registrarUsuario(T objeto);
    public abstract T validacionUsuario(K clavePrimaria);
    public abstract List<T> mostrarInformacion();
}
