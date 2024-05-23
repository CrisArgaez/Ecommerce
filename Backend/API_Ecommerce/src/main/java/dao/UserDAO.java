package dao;

import com.example.programacionweb_its_prac1.User;
import conexion.Conexion;
import java.util.ArrayList;

public class UserDAO implements DAOGeneral<Integer, User>{
    private final Conexion c;

    public UserDAO() {
        c = new Conexion<User>();
    }

    public int registrarUsuario(User user) {
        // Asegúrate de que los campos coincidan con los de la tabla users en la base de datos
        String query = "INSERT INTO users (nombre, correoelectronico, pass) VALUES (?, ?, ?)";
        return c.ejecutarActualizacion(query, new String[]{user.getNombre(), user.getCorreoelectronico(), user.getPass()});
    }

    public User validacionUsuario(String email) {
        // Consulta que busca un usuario por correo electrónico
        String query = "SELECT id, nombre, correoelectronico, pass FROM users WHERE correoelectronico = ?";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{email});

        if (!registros.isEmpty()) {
            ArrayList<String> registro = registros.get(0);
            Integer id = Integer.parseInt(registro.get(0));
            String nombre = registro.get(1);
            String apellidos = registro.get(2);
            String correoelectronico = registro.get(3);
            String contrasenia = registro.get(4);

            // Crear el objeto User con los datos obtenidos
            User user = new User(id, nombre,apellidos, correoelectronico, contrasenia);
            return user;
        }

        return null;
    }

}
