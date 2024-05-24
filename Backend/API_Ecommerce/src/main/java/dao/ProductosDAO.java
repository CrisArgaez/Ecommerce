package dao;

import java.util.ArrayList;
import com.example.programacionweb_its_prac1.Productos;
import conexion.Conexion;

public class ProductosDAO implements DAOGeneral<Integer,Productos,String>{
    private final Conexion c;

    public ProductosDAO() {
        c = new Conexion<Productos>();
    }

    @Override
    public int agregar(Productos elemento) {
        return 0;
    }

    public ArrayList<Productos> consultar() {
        String query = "SELECT id_producto, nombre, url_imagen, especificacion, descripcion, precio, existencia, galeriaFotos FROM productos";
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
            String galeria = registro.get(7);

            Productos producto = new Productos(id, nombre, urlImagen, especificacion, descripcion, precio, existencia,galeria);
            productos.add(producto);
        }
        return productos;
    }

    @Override
    public Productos consultar(Integer id) {
        return null;
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

