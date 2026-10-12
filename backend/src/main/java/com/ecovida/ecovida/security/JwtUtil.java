package com.ecovida.ecovida.security;

// Importa las clases necesarias para trabajar con fechas y convertir la clave secreta a bytes
import java.nio.charset.StandardCharsets;
import java.util.Date;

// Permite trabajar con claves utilizadas para firmar los tokens JWT
import javax.crypto.SecretKey;

// Permite obtener valores configurados en application.properties
import org.springframework.beans.factory.annotation.Value;

// Indica que esta clase es un componente administrado por Spring
import org.springframework.stereotype.Component;

// Importa las clases necesarias para crear y validar tokens JWT
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Registra esta clase como un componente de Spring
@Component
public class JwtUtil {

    // Obtiene la clave secreta definida en application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Obtiene el tiempo de expiración del token definido en application.properties
    @Value("${jwt.expiration}")
    private long expiration;

    // Genera la clave utilizada para firmar y validar los tokens JWT
    private SecretKey obtenerClave() {

        // Convierte la clave secreta en bytes y crea una clave HMAC
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Genera un token JWT utilizando el correo y el rol del usuario autenticado
    public String generarToken(String correo, String rol) {

        // Construye el token JWT con la información del usuario
        return Jwts.builder()

                // Guarda el correo del usuario como sujeto del token
                .setSubject(correo)

                // Guarda el rol del usuario dentro del token
                .claim("rol", rol)

                // Registra la fecha y hora en la que se genera el token
                .setIssuedAt(new Date())

                // Establece la fecha y hora en la que el token dejará de ser válido
                .setExpiration(
                        new Date(System.currentTimeMillis() + expiration)
                )

                // Firma el token utilizando la clave secreta
                .signWith(obtenerClave())

                // Genera el token como una cadena de texto
                .compact();
    }

    // Extrae el correo electrónico almacenado dentro del token JWT
    public String extraerCorreo(String token) {

        // Obtiene la información almacenada dentro del token
        Claims claims = Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Retorna el correo almacenado como sujeto del token
        return claims.getSubject();
    }

    // Extrae el rol almacenado dentro del token JWT
    public String extraerRol(String token) {

        // Obtiene la información almacenada dentro del token
        Claims claims = Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Retorna el rol almacenado en el token
        return claims.get("rol", String.class);
    }

    // Verifica si el token JWT recibido es válido
    public boolean validarToken(String token) {

        try {

            // Intenta leer y validar la firma y la información del token
            Jwts.parser()
                    .verifyWith(obtenerClave())
                    .build()
                    .parseSignedClaims(token);

            // Retorna true si el token es válido
            return true;

        } catch (Exception e) {

            // Retorna false si el token es inválido, está alterado o ha expirado
            return false;
        }
    }
}