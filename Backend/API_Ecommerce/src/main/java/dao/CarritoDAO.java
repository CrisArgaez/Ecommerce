package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.programacionweb_its_prac1.Carrito;
import com.example.programacionweb_its_prac1.Productos;
import conexion.Conexion;

//CarritoDAO contiene:
//obtenerProducto -> Obtener los datos de un producto especifico
//eliminarProductoCarrito -> Usuario elimina producto del carrito
//actualizarCantidad -> Actualizar existencias al realizar compras

<<<<<<< HEAD
public class CarritoDAO implements DAOGeneral<Integer,Carrito,String>{
    private final Conexion c;
=======
public class CarritoDAO {
    private final Conexion<Productos> conexion;
>>>>>>> c4a152a1f5d7406dae122e6723e8e8c79324f9cd

    public CarritoDAO() {
        c = new Conexion<Carrito>();
    }

    @Override
    public int agregar(Carrito elemento) {
        return 0;
    }

    @Override
    public ArrayList<Carrito> consultar() {
        return null;
    }

    @Override
    public Carrito consultar(Integer id) {
        String query = "SELECT id_carrito, id_producto, id_usuario FROM productos WHERE id_producto = ?";
        ArrayList<String> parametros = new ArrayList<>();
        parametros.add(id.toString());
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, null);

        if (registros.isEmpty()) {

            return null;
        }

        ArrayList<String> registro = registros.get(0);
        int carritoId = Integer.parseInt(registro.get(0));
        int productoId = Integer.parseInt(registro.get(1));
        int usuarioId = Integer.parseInt(registro.get(2));

        Carrito carrito = new Carrito(carritoId, productoId, usuarioId);
        return carrito;
    }


    @Override
    public Carrito consultarCorreo(String correo) {
        return null;
    }



    @Override
    public int actualizar(Integer id, Carrito elemento) {
        return 0;
    }

<<<<<<< HEAD
    @Override
    public int eliminar(Integer id) {
        String query = "DELETE FROM carrito_de_compras WHERE id_producto = ? AND i= ";
        return c.ejecutarActualizacion(query, new String[]{String.valueOf(id)});
=======
    private Productos convertirRegistroAProducto(ArrayList<String> registro) {
        int id = Integer.parseInt(registro.get(0));
        String nombre = registro.get(1);
        String descripcion = registro.get(2);
        int precio = Integer.parseInt(registro.get(3));
        int existencia = Integer.parseInt(registro.get(4));

        return null;//new Productos(id, nombre, "", "", descripcion, precio, existencia);
>>>>>>> c4a152a1f5d7406dae122e6723e8e8c79324f9cd
    }
}

