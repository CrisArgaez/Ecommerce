package dao;

import com.example.programacionweb_its_prac1.User;
import java.util.ArrayList;

public interface DAOGeneral<K, E> {
    public int registrarUsuario(E elemento);

    public ArrayList<E> consultar();

    boolean verificarCorreo(String email);

    public User consultarUsuario(K id);
}
