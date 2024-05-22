package dao;

import com.example.programacionweb_its_prac1.User;
import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    @Override
    public ArrayList<User> consultar() {
        String query = "SELECT fullName, email, username FROM users";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{});

        ArrayList<User> users = new ArrayList<>();
        for (ArrayList<String> registro : registros) {
            String fullName = registro.get(0);
            String email = registro.get(1);
            String username = registro.get(2);

            User user = new User(fullName, email, username, null, null);
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean verificarCorreo(String email) {
        String query = "SELECT email FROM users WHERE email = ?";
        return c.verificacionConsulta(query, email);
    }

    @Override
    public User consultarUsuario(Integer id) {
        String query = "SELECT fullName, email, username FROM users WHERE id = ?";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{id.toString()});

        if (!registros.isEmpty()) {
            ArrayList<String> registro = registros.get(0);
            String fullName = registro.get(0);
            String email = registro.get(1);
            String username = registro.get(2);

            // Como no estamos seleccionando la contraseña, la establecemos como null
            User user = new User(fullName, email, username, null, null);
            return user;
        }

        // Si llegamos a este punto, significa que no se encontró ningún usuario con el id proporcionado
        return null;
    }

    public User validacionUsuario(String usernameOrEmail) {
        // Consulta que busca un usuario por nombre o correo electrónico
        String query = "SELECT id, nombre, correoelectronico, contraseña FROM users WHERE nombre = ? OR correoelectronico = ?";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{usernameOrEmail, usernameOrEmail});

        if (!registros.isEmpty()) {
            ArrayList<String> registro = registros.get(0);
            Integer id = Integer.parseInt(registro.get(0));
            String nombre = registro.get(1);
            String correoelectronico = registro.get(2)  ;
            String contraseña = registro.get(3);

            // Crear el objeto User con los datos obtenidos
            User user = new User(id, nombre, correoelectronico, contraseña);
            return user;
        }

        return null;
    }

}
