package dao;

import com.example.programacionweb_its_prac1.User;
import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserDAO implements DAOGeneral<Integer, User> {
    private final Conexion c;
    public UserDAO() {
        c = new Conexion<User>();
    }

    @Override
    public int registrarUsuario(User user) {
        String query = "INSERT INTO users(fullName, email, username, password) VALUES (?, ?, ?, ?)";
        return c.ejecutarActualizacion(query, user.getAll());
    }

    public User validacionUsuario(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        System.out.println("El email que usare es " + email);
        ArrayList<ArrayList<String>> resp = c.ejecutarConsulta(query, new String[]{email});
        //System.out.println(rs);

        if(!resp.isEmpty()){
            ArrayList<String> rs = resp.get(0);//Obtener la primera linea de respuesta
        }

        // Si llegamos a este punto, significa que no se encontró ningún usuario con el email proporcionado
        return null;
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

}

