package com.example.programacionweb_its_prac1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.CarritoDAO;
import dao.ProductosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/carrito/*")
public class carritoServlet extends HttpServlet {

    private final JsonResponse jResp = new JsonResponse();
    private final CarritoDAO carritoDAO = new CarritoDAO();

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600"); // Tiempo de caché para preflight requests
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");

        Gson gson = new Gson();

        Carrito productoRequest = gson.fromJson(req.getReader(), Carrito.class);

        // Obtener el valor del parámetro "idUsuario" de la URL
        String idUsuarioParametro = req.getParameter("idUsuario");

        if (idUsuarioParametro != null) {
            Integer idUsuario = Integer.parseInt(idUsuarioParametro);
            obtenerArticulosCarrito(req, resp, idUsuario);
        } else {
            // Manejar el caso en el que no se proporciona el parámetro "idUsuario"
            // Puedes enviar una respuesta de error o realizar otra acción apropiada.
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");
        Gson gson = new Gson();

        // Lee el JSON completo como una lista de objetos Carrito
        List<Carrito> carritos = gson.fromJson(req.getReader(), new TypeToken<List<Carrito>>() {
        }.getType());

        //Todas las compras son del mismo usuario
        Integer idUsuario = carritos.get(0).getIdUsuario();

        //Verificar la existencia de cada uno de los productos que se quieren comprar
        for (Carrito carrito : carritos) {
            Integer idProducto = carrito.getIdProducto();
            Integer idCantidadCompra = carrito.getCantidadCompra();

            int compraPosible = verificarExistencia(idProducto, idCantidadCompra);
            if (compraPosible == 1) {
                jResp.success(req, resp, "Existencia verificada", null);
            } else {
                jResp.failed(req, resp, "No se puede realizar la compra pues la cantidad de productos que deseas sobrepasa el inventario actual", 500);
                return; // Detener el procesamiento si una compra no es posible
            }
        }

        //Una vez verificados, se actualizan todos los elementos
        for (Carrito carrito : carritos) {
            Integer idProducto = carrito.getIdProducto();
            Integer idCantidadCompra = carrito.getCantidadCompra();

            int estado = actualizarExistenciaProducto(req, resp, idProducto, idCantidadCompra);
            if (estado == 1) {
                jResp.success(req, resp, "Inventario actualizado", null);
            } else {
                jResp.failed(req, resp, "No se pudo completar la compra", 500);
                return; // Detener el procesamiento si hay una falla al actualizar la base de datos
            }
        }

        // Si llegamos aquí, todas las compras fueron exitosas
        realizarCompra(req, resp, idUsuario);
        jResp.success(req, resp, "Todas las compras se realizaron correctamente", null);
    }


    //
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();
        int idProducto = Integer.parseInt(pathInfo.substring(1));

        Gson gson = new Gson();
        Carrito productoRequest = gson.fromJson(req.getReader(), Carrito.class);
        Integer idUsuario = productoRequest.getIdUsuario();//Me devolvera el id que pase en el cuerpo de la peticion DELETE
        Integer idCantidadCompra = productoRequest.getCantidadCompra();//Me devuelve la cantidad que el usuario esta seleccionando actualmente

        ProductosDAO productosDAO = new ProductosDAO();
        int existenciaProducto = productosDAO.consultarExistencia(idProducto);

        if (existenciaProducto >= idCantidadCompra && idCantidadCompra > 0) {
            actualizarCantidadCompra(req, resp, idCantidadCompra, idUsuario, idProducto);
        } else {
            jResp.failed(req, resp, "La cantidad que quieres comprar, sobrepasa el inventario actual", 422);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");

        //Obtener el id del articulo por eliminar
        String pathInfo = req.getPathInfo();
        int idProducto = Integer.parseInt(pathInfo.substring(1));

        //Determinar a quien le pertenece el id por medio del body de la peticion
        Gson gson = new Gson();
        Carrito productoRequest = gson.fromJson(req.getReader(), Carrito.class);
        Integer idUsuario = productoRequest.getIdUsuario();//Me devolvera el id que pase en el cuerpo de la peticion DELETE

        //Proceso de eliminacion
        eliminarArticuloCarrito(req, resp, idUsuario, idProducto);
    }

    private void obtenerArticulosCarrito(HttpServletRequest req, HttpServletResponse resp, Integer id) throws IOException {
        CarritoDAO carritodao = new CarritoDAO();
        ArrayList<Carrito> carrito = carritodao.consultar(id);

        if (carrito != null) {
            jResp.success(req, resp, "Listado de productos en el carrito del usuario " + id + ": ", carrito);
        } else {
            jResp.failed(req, resp, "No se encontró ningún carrito para el ID especificado.", 404);
        }
    }

    private void realizarCompra(HttpServletRequest req, HttpServletResponse resp, int idUsuario) throws IOException {
        CarritoDAO carritodao = new CarritoDAO();
        int filasAfectadas = carritodao.eliminarCarritoUsuario(idUsuario);
        if (filasAfectadas > 0) {
            jResp.success(req, resp, "Compra realizada con éxito", null);
        } else {
            jResp.failed(req, resp, "Error al completar la compra", 500);
        }
    }

    private int verificarExistencia(int idProducto, int cantidadComprada) throws IOException {
        ProductosDAO productosDAO = new ProductosDAO();
        int existenciaActual = productosDAO.consultarExistencia(idProducto);
        if (existenciaActual >= cantidadComprada) {
            return 1;//Se puede realizar la compra
        } else {
            return 0;//No se puede realizar la compra
        }
    }

    private int actualizarExistenciaProducto(HttpServletRequest req, HttpServletResponse resp, int idProducto, int cantidadComprada) throws IOException {
        ProductosDAO productosDAO = new ProductosDAO();
        int existenciaActual = productosDAO.consultarExistencia(idProducto);
        int nuevaExistencia = existenciaActual - cantidadComprada;

        if (nuevaExistencia >= 0) {//Si al restarle la cantidad que quiere comprar el usuario a la existencia actual, da mayor a 0, la compra se puede realizar
            int filasActualizadas = productosDAO.actualizarExistencia(idProducto, nuevaExistencia);
            if (filasActualizadas > 0) {
                jResp.success(req, resp, "Cantidad actualizada con éxito.", nuevaExistencia);
            } else {
                jResp.failed(req, resp, "Error al actualizar la cantidad del producto.", 422);
            }
            return 1;  //Los datos se actualizaron
        } else {
            return 0; //Algo fallo al actualizar el inventario
        }
    }

    private void eliminarArticuloCarrito(HttpServletRequest req, HttpServletResponse resp, Integer idUsuario, Integer idProducto) throws IOException {
        CarritoDAO carritodao = new CarritoDAO();
        int filasEliminadas = carritodao.eliminar(idUsuario, idProducto);
        if (filasEliminadas > 0) {
            jResp.success(req, resp, "Producto eliminado con éxito.", "");
        } else {
            jResp.failed(req, resp, "No se encontró ningún carrito con el ID del artículo que desea eliminar.", 404);
        }
    }

    private void actualizarCantidadCompra(HttpServletRequest req, HttpServletResponse resp, Integer cantidadCompra, Integer idUsuario, Integer idProducto) throws IOException {
        CarritoDAO carritodao = new CarritoDAO();
        int filasAfectadas = carritodao.actualizarCantidadCompra(cantidadCompra, idUsuario, idProducto);
        if (filasAfectadas > 0) {
            jResp.success(req, resp, "Cantidad actualizada con éxito.", "");
        } else {
            jResp.failed(req, resp, "No se encontró ningún carrito con el ID del artículo que desea actualizar.", 404);
        }
    }
}