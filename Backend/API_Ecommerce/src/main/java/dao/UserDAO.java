package dao;

import com.example.programacionweb_its_prac1.User;
import conexion.Conexion;
import java.util.ArrayList;

public class UserDAO {
    private final Conexion c;

    public UserDAO() {
        c = new Conexion<User>();
    }

    public int registrarUsuario(User user) {
        // Asegúrate de que los campos coincidan con los de la tabla users en la base de datos
        String query = "INSERT INTO users (nombre, correoelectronico, contraseña) VALUES (?, ?, ?)";
        return c.ejecutarActualizacion(query, new String[]{user.getNombre(), user.getCorreoelectronico(), user.getContraseña()});
    }

    public User validacionUsuario(String usernameOrEmail) {
        // Consulta que busca un usuario por nombre o correo electrónico
        String query = "SELECT id, nombre, correoelectronico, contraseña FROM users WHERE nombre = ? OR correoelectronico = ?";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{usernameOrEmail, usernameOrEmail});

        if (!registros.isEmpty()) {
            ArrayList<String> registro = registros.get(0);
            Integer id = Integer.parseInt(registro.get(0));
            String nombre = registro.get(1);
            String correoelectronico = registro.get(2);
            String contraseña = registro.get(3);

            // Crear el objeto User con los datos obtenidos
            User user = new User(id, nombre, correoelectronico, contraseña);
            return user;
        }

        return null;
    }
}
