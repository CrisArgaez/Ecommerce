package dao;



import java.util.ArrayList;

import com.example.programacionweb_its_prac1.Carrito;
import com.example.programacionweb_its_prac1.Productos;
import conexion.Conexion;

public class CarritoDAO implements DAOGeneral<Integer, Carrito> {
    private final Conexion<Productos> conexion;

    public CarritoDAO() {
        conexion = new Conexion<>();
    }
    public Productos obtenerProducto(int idProducto) {
        String query = "SELECT id_producto, nombre, descripcion, precio, existencia FROM carrito_de_compras WHERE id_producto = ?";
        ArrayList<ArrayList<String>> registros = conexion.ejecutarConsulta(query, new String[]{String.valueOf(idProducto)});

        if (!registros.isEmpty()) {
            ArrayList<String> registro = registros.get(0);
            int id = Integer.parseInt(registro.get(0));
            String nombre = registro.get(1);
            String url_imagen = registro.get(2);
            String descripcion = registro.get(3);
            String especificaciones = registro.get(4);
            int precio = Integer.parseInt(registro.get(5));
            int existencia = Integer.parseInt(registro.get(6));

            return new Productos(id,nombre,url_imagen,especificaciones,descripcion,precio,existencia);
        }

        return null;
    }

    public int eliminarProductoCarrito(int idProducto) {
        String query = "DELETE FROM carrito_de_compras WHERE id_producto = ?";
        return conexion.ejecutarActualizacion(query, new String[]{String.valueOf(idProducto)});
    }

    public int actualizarCantidad(int idProducto, int nuevaCantidad) {
        String query = "UPDATE carrito_de_compras SET existencia = ? WHERE id_producto = ?";
        return conexion.ejecutarActualizacion(query, new String[]{String.valueOf(nuevaCantidad), String.valueOf(idProducto)});
    }
}

