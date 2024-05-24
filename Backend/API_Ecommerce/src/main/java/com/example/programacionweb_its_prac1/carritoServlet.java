package com.example.programacionweb_its_prac1;

import dao.CarritoDAO;
import dao.ProductosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


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
        // Se repite el mismo metodo del realizar compra, para evitar errores

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            int id = Integer.parseInt(pathInfo.substring(1));
            ProductosDAO productosDAO = new ProductosDAO();
            int existenciaProducto = productosDAO.consultarExistencia(id);
            if (existenciaProducto > 0) {
                actualizarCantidadArticulo(req, resp, id);

            }
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
        String pathInfo = req.getPathInfo();
        int id = Integer.parseInt(pathInfo.substring(1));
        ProductosDAO productosDAO = new ProductosDAO();
        int existenciaProducto = productosDAO.consultarExistencia(id);

        if (existenciaProducto > 0) {
            // Si hay existencia del producto, realiza la compra
            jResp.success(req, resp, "Compra realizada con éxito.", "");
            // Llama al método actualizarCantidadArticulo para actualizar la cantidad de productos
          actualizarCantidadArticulo(req, resp, id);
        } else {
            // Si no hay existencia del producto, devuelve un mensaje de error
            jResp.failed(req, resp, "No hay existencia del producto.", 404);
        }
    }


    private void actualizarCantidadArticulo(HttpServletRequest req, HttpServletResponse resp, int idProducto) throws IOException {
        ProductosDAO productosDAO = new ProductosDAO();
        int cantidadComprada = Integer.parseInt(req.getParameter("existencia"));
        int existenciaActual = productosDAO.consultarExistencia(idProducto);
        int nuevaExistencia = existenciaActual - cantidadComprada;

        int filasActualizadas = productosDAO.actualizarExistencia(idProducto, nuevaExistencia);
        if (filasActualizadas > 0) {
            jResp.success(req, resp, "Cantidad actualizada con éxito.", nuevaExistencia);
        } else {
            jResp.failed(req, resp, "Error al actualizar la cantidad del producto.", 500);
        }
    }



    private void eliminarArticuloCarrito(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        int idProducto = Integer.parseInt(pathInfo.substring(1));
        int filasEliminadas = carritoDAO.eliminar(idProducto);
        if (filasEliminadas > 0) {
            jResp.success(req, resp, "Producto eliminado con éxito.", "");

        }
    }
}

