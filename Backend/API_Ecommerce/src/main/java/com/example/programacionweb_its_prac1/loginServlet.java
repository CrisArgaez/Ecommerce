package com.example.programacionweb_its_prac1;

import com.google.gson.Gson;
import dao.UserDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;


@WebServlet("/login/*")
public class loginServlet extends HttpServlet {

    private static final String SECRET_KEY = "mWQKjKflpJSqyj0nDdSG9ZHE6x4tNaXGb35J6d7G5mo=";
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
        validarCredenciales(req, resp);
    }

    private void validarCredenciales(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();

        User userRequest = gson.fromJson(req.getReader(), User.class);

        String password = userRequest.getPassword();
        String email = userRequest.getCorreoelectronico();

        System.out.println(email);

        UserDAO userDao = new UserDAO();
        User InformacionUsuario = userDao.consultarCorreo(email);

        if (InformacionUsuario != null) {
            System.out.println("Hola si llegue aqui");
            if (verifyPassword(password, InformacionUsuario.getPassword())) {
                long nowMillis = System.currentTimeMillis();
                long expMillis = nowMillis + 300000; // 5 minutos en milisegundos
                Date exp = new Date(expMillis);

                String token = Jwts.builder()
                        .setHeaderParam("kid", SECRET_KEY)
                        .setExpiration(exp)
                        .signWith(generalKey())
                        .compact();



                jResp.success(req, resp, ""+InformacionUsuario.getId(), token);
                return;

            }
        }
        jResp.failed(req, resp, "Nombre de usuario o contraseña inválidos", HttpServletResponse.SC_UNAUTHORIZED);
    }

    private boolean verifyPassword(String inputPassword, String storedPassword) {
        return BCrypt.checkpw(inputPassword, storedPassword);
    }

    public static SecretKey generalKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
