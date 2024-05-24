package com.example.programacionweb_its_prac1;

import com.google.gson.Gson;
import dao.CarritoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        Integer idUsuario = productoRequest.getIdUsuario();//Me devolvera el id que pase en el cuerpo de la peticion PUT
        obtenerArticulosCarrito(req, resp, idUsuario);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");
        // Procesar la compra de los artículos en el carrito
        realizarCompra(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");

        // Actualizar la cantidad de elementos del artículo en el carrito
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
//            actualizarCantidadArticulo(req, resp);

            // metodo aun en proceso
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
            jResp.failed(req, resp, "No se encontró ningún carrito para el ID especificado.",404);
        }
    }


    private void realizarCompra(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int cantidad = Integer.parseInt(req.getParameter("existencia"));
        if(cantidad > 0) {
            jResp.success(req, resp, "Compra realizada con éxito.", "");

            // metodo aun creandose

//            actualizarCantidadArticulo(req, resp);
        }
    }

//    private void actualizarCantidadArticulo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String pathInfo = req.getPathInfo();
//        int idProducto = Integer.parseInt(pathInfo.substring(1));
//        int nuevaCantidad = Integer.parseInt(req.getParameter("existencia"));
//
//        int filasActualizadas = carritoDAO.actualizar(idProducto, nuevaCantidad);
//        if (filasActualizadas > 0) {
//            jResp.success(req, resp, "Cantidad actualizada con éxito.", nuevaCantidad);
//        }
//    }



    private void eliminarArticuloCarrito(HttpServletRequest req, HttpServletResponse resp, Integer idUsuario, Integer idProducto) throws IOException {
        CarritoDAO carritodao = new CarritoDAO();
        int filasEliminadas = carritodao.eliminar(idUsuario, idProducto);
        if (filasEliminadas > 0) {
            jResp.success(req, resp, "Producto eliminado con éxito.", "");
        }else{
            jResp.failed(req, resp, "No se encontró ningún carrito con el ID del artículo que desea eliminar.",404);
        }
    }
}

