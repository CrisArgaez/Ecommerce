package com.example.programacionweb_its_prac1;

import dao.CarritoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        if (req.getPathInfo().equals("/")) {
            obtenerArticulosCarrito(req, resp);
        }
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

        // Eliminar un artículo del carrito
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            eliminarArticuloCarrito(req, resp);
        }
    }

    private void obtenerArticulosCarrito(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        int id = Integer.parseInt(pathInfo.substring(1));
        Carrito carrito = carritoDAO.consultar(id);

        if (carrito != null) {
            jResp.success(req, resp, "Listado de productos en el carrito: ", carrito);
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



    private void eliminarArticuloCarrito(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        int idProducto = Integer.parseInt(pathInfo.substring(1));
        int filasEliminadas = carritoDAO.eliminar(idProducto);
        if (filasEliminadas > 0) {
            jResp.success(req, resp, "Producto eliminado con éxito.", "");

        }
    }
}

