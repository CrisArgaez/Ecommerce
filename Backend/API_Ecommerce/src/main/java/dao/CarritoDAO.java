package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.programacionweb_its_prac1.Productos;
import conexion.Conexion;

//CarritoDAO contiene:
//obtenerProducto -> Obtener los datos de un producto especifico
//eliminarProductoCarrito -> Usuario elimina producto del carrito
//actualizarCantidad -> Actualizar existencias al realizar compras

public class CarritoDAO{
    private final Conexion<Productos> conexion;

    public CarritoDAO() {
        conexion = new Conexion<Productos>();
    }

    public List<Productos> consultarPorIds(List<Integer> idProductos) {
        if (idProductos == null || idProductos.isEmpty()) {
            return new ArrayList<>();
        }

        String query = "SELECT id_producto, nombre, descripcion, precio, existencia FROM carrito_de_compras WHERE id_producto IN (" +
                idProductos.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
        ArrayList<ArrayList<String>> registros = conexion.ejecutarConsulta(query, new String[]{});

        List<Productos> productos = new ArrayList<>();
        for (ArrayList<String> registro : registros) {
            productos.add(convertirRegistroAProducto(registro));
        }

        return productos;
    }

    public int eliminarProductoCarrito(int idProducto) {
        String query = "DELETE FROM carrito_de_compras WHERE id_producto = ?";
        return conexion.ejecutarActualizacion(query, new String[]{String.valueOf(idProducto)});
    }

    public int actualizarCantidad(int idProducto, int nuevaCantidad) {
        String query = "UPDATE carrito_de_compras SET existencia = ? WHERE id_producto = ?";
        return conexion.ejecutarActualizacion(query, new String[]{String.valueOf(nuevaCantidad), String.valueOf(idProducto)});
    }

    private Productos convertirRegistroAProducto(ArrayList<String> registro) {
        int id = Integer.parseInt(registro.get(0));
        String nombre = registro.get(1);
        String descripcion = registro.get(2);
        int precio = Integer.parseInt(registro.get(3));
        int existencia = Integer.parseInt(registro.get(4));

        return new Productos(id, nombre, "", "", descripcion, precio, existencia);
    }
}

