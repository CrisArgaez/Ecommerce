package dao;

import java.util.ArrayList;
import java.util.List;
import com.example.programacionweb_its_prac1.Productos;
import conexion.Conexion;

public class ProductosDAO {
    private final Conexion c;

    public ProductosDAO() {
        c = new Conexion<Productos>();
    }

    public List<Productos> mostrarInformacion() {
        String query = "SELECT id_producto, nombre, url_imagen, especificacion, descripcion, precio, existencia FROM productos";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, null);
        List<Productos> productos = new ArrayList<>();

        for (ArrayList<String> registro : registros) {
            int id = Integer.parseInt(registro.get(0));
            String nombre = registro.get(1);
            String urlImagen = registro.get(2);
            String especificacion = registro.get(3);
            String descripcion = registro.get(4);
            int precio = Integer.parseInt(registro.get(5));
            int existencia = Integer.parseInt(registro.get(6));

            Productos producto = new Productos(id, nombre, urlImagen, especificacion, descripcion, precio, existencia);
            productos.add(producto);
        }

        return productos;
    }
}

