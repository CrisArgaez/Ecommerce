package dao;

import java.util.ArrayList;

import com.example.programacionweb_its_prac1.Carrito;
import com.example.programacionweb_its_prac1.Productos;
import conexion.Conexion;

//CarritoDAO contiene:
//obtenerProducto -> Obtener los datos de un producto especifico
//eliminarProductoCarrito -> Usuario elimina producto del carrito
//actualizarCantidad -> Actualizar existencias al realizar compras


public class CarritoDAO implements DAOGeneral<Integer,Carrito,String>{
    private final Conexion c;


    public CarritoDAO() {
        c = new Conexion<Carrito>();
    }

    @Override
    public int agregar(Carrito elemento) {
        return 0;
    }

    @Override
    public int agregarArticuloCarrito(Integer id_Usuario, Integer id_Producto) {
        return 0;
    }

    @Override
    public ArrayList<Carrito> consultar() {
        return null;
    }

    @Override
    public ArrayList<Carrito> consultar(Integer id) {
        String query = "SELECT id_producto FROM carrito_de_compras WHERE id_usuario = ?";
        ArrayList<ArrayList<String>> registros = c.ejecutarConsulta(query, new String[]{id.toString()});
        ArrayList<Carrito> productos = new ArrayList<>();

        if (registros.isEmpty()) {
            return null;
        }else{
            for (ArrayList<String> registro : registros) {
                int productoId = Integer.parseInt(registro.get(0));

                Carrito carrito = new Carrito(0, 0, productoId);
                productos.add(carrito);
            }
            return productos;
        }
    }


    @Override
    public Carrito consultarCorreo(String correo) {
        return null;
    }



    @Override
    public int actualizar(Integer id, Carrito elemento) {
        return 0;
    }


    @Override
    public int eliminar(Integer idUsuario, Integer idProducto) {
        String query = "DELETE FROM carrito_de_compras WHERE id_usuario = ? AND id_producto = ?";
        return c.ejecutarActualizacion(query, new String[]{String.valueOf(idUsuario), String.valueOf(idProducto)});
    }
}


