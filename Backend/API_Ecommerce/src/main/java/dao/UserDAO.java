package dao;

import com.example.programacionweb_its_prac1.User;
import conexion.Conexion;

import java.util.ArrayList;

//UserDAO contiene:
//registrarUsuario -> A la base de datos y de manera definitiva
//verificarCorreo -> Para que el correo no se repita (no implementado)
//validacionUsuario -> Completar el login de ecommerce

public class UserDAO implements DAOGeneral<Integer, User, String> {
    private final Conexion c;

    public UserDAO() {
        c = new Conexion<User>();
    }

    public int agregar(User user) {
        String query = "INSERT INTO users (nombre, apellidos, correoelectronico, pass) VALUES (?, ?, ?, ?)";
        return c.ejecutarActualizacion(query, new String[]{user.getNombres(), user.getApellidos(), user.getCorreoelectronico(), user.getPassword()});
    }

    @Override
    public int agregarArticuloCarrito(Integer id_Usuario, Integer id_Producto) {
        return 0;
    }

    @Override
    public ArrayList<User> consultar() {
        return null;
    }

    @Override
    public ArrayList<User> consultar(Integer id) {
        return null;
    }

    @Override
    public int actualizar(Integer id, User elemento) {
        return 0;
    }

    @Override
    public int eliminar(Integer id, Integer id2) {
        return 0;
    }

    public User consultarCorreo(String correo) {
        String query = "SELECT id, nombre, apellidos, correoelectronico, pass FROM users WHERE correoelectronico = ?";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{correo});

        if (!registros.isEmpty()) {
            ArrayList<String> registro = registros.get(0);
            Integer id = Integer.parseInt(registro.get(0));
            String nombres = registro.get(1);
            String apellidos = registro.get(2);
            String correoelectronico = registro.get(3)  ;
            String contraseña = registro.get(4);

            // Crear el objeto User con los datos obtenidos
            User user = new User(id, nombres, apellidos, correoelectronico, contraseña);
            return user;
        }

        return null;
    }

    /*public User validacionUsuario(String usernameOrEmail) {
        // Consulta que busca un usuario por nombre o correo electrónico
        String query = "SELECT id, nombre, correoelectronico, contraseña FROM users WHERE nombre = ? OR correoelectronico = ?";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{usernameOrEmail, usernameOrEmail});

        if (!registros.isEmpty()) {
            ArrayList<String> registro = registros.get(0);
            Integer id = Integer.parseInt(registro.get(0));
            String nombres = registro.get(1);
            String apellidos = registro.get(2);
            String correoelectronico = registro.get(3)  ;
            String contraseña = registro.get(4);

            // Crear el objeto User con los datos obtenidos
            User user = new User(id, nombres, apellidos, correoelectronico, contraseña);
            return user;
        }

        return null;
    }*/

}
