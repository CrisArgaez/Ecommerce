package com.example.programacionweb_its_prac1;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import dao.UserDAO;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import io.jsonwebtoken.*;
import org.mindrot.jbcrypt.BCrypt;
import javax.crypto.SecretKey;

@WebServlet("/autenticacion-servlet/*")

/**
 * Clase que contiene los siguientes endpoints:
 * - login
 * - logout
 */
public class AutenticacionServlet extends HttpServlet {
    private static final String SECRET_KEY = "mWQKjKflpJSqyj0nDdSG9ZHE6x4tNaXGb35J6d7G5mo=";
    private static final Map<String, User> users = new HashMap<>();
    private final JsonResponse jResp = new JsonResponse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        // Configura las cabeceras para permitir cualquier origen
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        // Configura las cabeceras para permitir cualquier origen
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if (req.getPathInfo() == null) {
            jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String[] path = req.getPathInfo().split("/");

        if (req.getPathInfo().equals("/")) {
            jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
        }

        String action = path[1];

        switch (action) {
            case "login":
                login(req, resp);
                break;
            case "logout":
                logout(req, resp);
                break;
            default:
                jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Metodo que se utiliza para el endpoint /autenticacion-servlet/login de tipo POST
     * Se encarga de autenticar un usuario en el sistema, recibe los siguientes parametros:
     * - username
     * - password
     * 
     * Si el usuario no existe o la contraseña es incorrecta, se responde con un mensaje de error,
     * en caso contrario se genera un token JWT y se responde con un mensaje de éxito.
     * @param req
     * @param resp
     * @throws IOException
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");//Obtener el valor que se nos pasa de la URL
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDAO userDao = new UserDAO();
        User InformacionUsuario = userDao.validacionUsuario(email);

        if (InformacionUsuario != null) {
            if (verifyPassword(password, InformacionUsuario.getPassword())) {
                long nowMillis = System.currentTimeMillis();
                long expMillis = nowMillis + 300000; // 5 minutos en milisegundos
                Date exp = new Date(expMillis);

                String token = Jwts.builder()
                        .setHeaderParam("kid", SECRET_KEY)
                        .setExpiration(exp)
                        .signWith(generalKey())
                        .compact();

                jResp.success(req, resp, "Usuario encontrado y autenticado", token);
                return;
            }
        }
        jResp.failed(req, resp, "Nombre de usuario o contraseña inválidos", HttpServletResponse.SC_UNAUTHORIZED);
    }

    /**
     * Metodo que se utiliza para el endpoint /autenticacion-servlet/logout de tipo POST
     * Se encarga de cerrar la sesión de un usuario en el sistema.
     * @param req
     * @param resp
     * @throws IOException
     */
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Logged out successfully");
    }

    /**
     * Metodo que se encarga de verificar si una contraseña es correcta
     * @param inputPassword Contraseña ingresada por el usuario
     * @param storedPassword Contraseña almacenada en la base de datos (HasMap)
     * @return true si la contraseña es correcta, false en caso contrario
     */
    private boolean verifyPassword(String inputPassword, String storedPassword) {
        return BCrypt.checkpw(inputPassword, storedPassword);
    }

    /**
     * Metodo que se encarga de generar una clave secreta
     * @return SecretKey con la clave secreta generada
     */
    public static SecretKey generalKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
