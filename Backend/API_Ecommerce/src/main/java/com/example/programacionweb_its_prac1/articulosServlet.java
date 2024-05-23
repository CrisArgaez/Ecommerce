package com.example.programacionweb_its_prac1;

import dao.ProductosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

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
        /*String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            obtenerArticulos(req, resp);
        }*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");
    }

    private void obtenerArticulos(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductosDAO articulosDAO = new ProductosDAO();
        List<Productos> productos = articulosDAO.consultar();
        jResp.success(req, resp, "Listado de productos: ", productos);
    }
}
