package dao;

import java.util.ArrayList;
import com.example.programacionweb_its_prac1.Productos;
import conexion.Conexion;

public class ProductosDAO implements DAOGeneral<Integer, Productos, String> {
    private final Conexion c;

    public ProductosDAO() {
        c = new Conexion<Productos>();
    }

    @Override
    public int agregar(Productos elemento) {
        return 0;
    }

    @Override
    public int agregarArticuloCarrito(Integer id_Usuario, Integer id_Producto) {
        String query = "INSERT INTO carrito_de_compras (id_usuario, id_producto) VALUES (?, ?)";
        return c.ejecutarActualizacion(query, new String[]{String.valueOf(id_Usuario), String.valueOf(id_Producto)});
    }

    public ArrayList<Productos> consultar() {
        String query = "SELECT * FROM productos";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, null);
        ArrayList<Productos> productos = new ArrayList<>();

        for (ArrayList<String> registro : registros) {
            int id = Integer.parseInt(registro.get(0));
            String nombre = registro.get(1);
            String urlImagen = registro.get(2);
            String especificacion = registro.get(3);
            String descripcion = registro.get(4);
            int precio = Integer.parseInt(registro.get(5));
            int existencia = Integer.parseInt(registro.get(6));
            String galeriaFotos = registro.get(7);

            Productos producto = new Productos(id, nombre, urlImagen, especificacion, descripcion, precio, existencia, galeriaFotos);
            productos.add(producto);
        }
        return productos;
    }


    @Override
    public ArrayList<Productos> consultar(Integer id) {
        String query = "SELECT * FROM productos WHERE id_producto = ?";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{id.toString()});
        ArrayList<Productos> productos = new ArrayList<>();

        for (ArrayList<String> registro : registros) {
            int idUsuario = Integer.parseInt(registro.get(0));
            String nombre = registro.get(1);
            String urlImagen = registro.get(2);
            String especificacion = registro.get(3);
            String descripcion = registro.get(4);
            int precio = Integer.parseInt(registro.get(5));
            int existencia = Integer.parseInt(registro.get(6));
            String galeriaFotos = registro.get(7);

            Productos producto = new Productos(idUsuario, nombre, urlImagen, especificacion, descripcion, precio, existencia, galeriaFotos);
            productos.add(producto);
        }
        return productos;
    }

    @Override
    public Productos consultarCorreo(String correo) {
        return null;
    }

    @Override
    public int actualizar(Integer id, Productos elemento) {
        return 0;
    }

    @Override
    public int eliminar(Integer id) {
        return 0;
    }
}

