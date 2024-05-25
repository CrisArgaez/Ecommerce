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
        String query = "INSERT INTO carrito_de_compras (id_usuario, id_producto, cantidadCompra) VALUES (?, ?, 1)";
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
    public int eliminar(Integer id, Integer id2) {
        return 0;
    }

    @Override
    public int eliminarCarritoUsuario(Integer id_Usuario) {
        return 0;
    }

    @Override
    public int verificarRepetido(Integer id_Usuario, Integer id_Producto) {
        String query = "SELECT id_producto FROM carrito_de_compras WHERE id_usuario = ? AND id_Producto = ?";
        ArrayList<ArrayList<String>> registros =  c.ejecutarConsulta(query, new String[]{String.valueOf(id_Usuario), String.valueOf(id_Producto)});

        if(registros.isEmpty()) {
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int consultarExistencia(Integer id) {
        String query = "SELECT existencia FROM productos WHERE id_producto = ?";
        ArrayList<String> parametros = new ArrayList<>();
        parametros.add(String.valueOf(id));

        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, parametros.toArray(new String[0]));

        if (registros.size() == 0) {
            return -1;
        }
        String existenciaStr = registros.get(0).get(0);
        return Integer.parseInt(existenciaStr);
    }

    @Override
    public int actualizarExistencia(Integer id, int nuevaCantidad) {
        String query = "UPDATE productos SET existencia = ? WHERE id_producto = ?";
        return c.ejecutarActualizacion(query, new String[]{String.valueOf(nuevaCantidad), String.valueOf(id)});
    }

    @Override
    public int actualizarCantidadCompra(Integer cantidadCompra,Integer id_Usuario, Integer id_Producto) {
        return 0;
    }
}

