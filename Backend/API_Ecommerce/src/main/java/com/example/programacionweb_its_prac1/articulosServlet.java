package com.example.programacionweb_its_prac1;

import com.google.gson.Gson;
import dao.ProductosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/articulos/*")
public class articulosServlet extends HttpServlet{

    private final JsonResponse jResp = new JsonResponse();

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600"); // Tiempo de caché para preflight requests
    }

    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");
        obtenerArticulos(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");
        jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");

        Gson gson = new Gson();

        Productos productoRequest = gson.fromJson(req.getReader(), Productos.class);

        Integer idUsuario = productoRequest.getId();//Me devolvera el id que pase en el cuerpo de la peticion PUT

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            // Elimina la barra diagonal inicial
            String idArticulo = pathInfo.substring(1);
            agregarProducto(req, resp, idUsuario, Integer.valueOf(idArticulo));
        }
    }

    private void obtenerArticulos(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductosDAO articulosDAO = new ProductosDAO();
        List<Productos> productos = articulosDAO.consultar();
        jResp.success(req, resp, "Listado de productos: ", productos);
    }
<<<<<<< HEAD
=======

    private void obtenerArticuloEspecifico(HttpServletRequest req, HttpServletResponse resp, Integer id) throws IOException {
        ProductosDAO articulosDAO = new ProductosDAO();
        List<Productos> producto = articulosDAO.consultar(id);
        if(!producto.isEmpty()){
            jResp.success(req, resp, "Producto: ", producto);
        }
        else{
            jResp.failed(req, resp, "El producto que buscas no existe", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void agregarProducto(HttpServletRequest req, HttpServletResponse resp, Integer id_Usuario, Integer id_Producto) throws IOException {
        ProductosDAO articulosDAO = new ProductosDAO();
        int respuesta = articulosDAO.agregarArticuloCarrito(id_Usuario, id_Producto);
        if(respuesta != -1){
            jResp.success(req, resp, "El producto ha sido guardado exitosamente", null);
        }else{
            jResp.failed(req, resp, "No se ha podido guardar el producto", HttpServletResponse.SC_NOT_FOUND);
        }
    }
>>>>>>> c4a152a1f5d7406dae122e6723e8e8c79324f9cd
}
