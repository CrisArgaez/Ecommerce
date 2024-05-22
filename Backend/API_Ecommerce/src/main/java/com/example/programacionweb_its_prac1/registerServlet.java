package com.example.programacionweb_its_prac1;

import com.google.gson.Gson;
import dao.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/register/*")
public class registerServlet extends HttpServlet {
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
        jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setContentType("application/json");
        registrarUsuario(req, resp);
    }

    private void registrarUsuario(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Gson gson = new Gson();

        User userRequest = gson.fromJson(req.getReader(), User.class);

        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        String fullName = userRequest.getFullName();
        String email = userRequest.getEmail();
        Integer id = null;

        //Revisa que los parametros existan en la URL
        if (username == null || password == null || fullName == null || email == null) {
            jResp.failed(req, resp, "Es necesario que rellenes todos los parámetros", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }//Revisa que los parametros no esten vacios
        else if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            jResp.failed(req, resp, "Es necesario que rellenes todos los datos", HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String encryptedPassword = encryptPassword(password);

        UserDAO userDao = new UserDAO();

        if(!userDao.verificarCorreo(email)){
            User user = new User(fullName, email, username, encryptedPassword, null);

            int estadoOperacion = userDao.registrarUsuario(user);

            if(estadoOperacion != -1){
                id = estadoOperacion;
                jResp.success(req, resp, "Usuario con id: " + id + " creado con éxito", user);//Respuesta de la solicitud
            } else{
                jResp.failed(req, resp, "No se pudo registrar al usuario", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        else{
            jResp.failed(req, resp, "Ya existe un usuario con este correo electrónico", 422);
        }
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
