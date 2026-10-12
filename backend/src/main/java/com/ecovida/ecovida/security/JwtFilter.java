package com.ecovida.ecovida.security;


// Indica que esta clase es un componente administrado por Spring.
import org.springframework.stereotype.Component;


// Permite ejecutar el filtro una sola vez
// por cada solicitud HTTP.
import org.springframework.web.filter.OncePerRequestFilter;


// Permite crear la autenticación del usuario
// dentro del contexto de seguridad de Spring.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


// Permite almacenar la autenticación del usuario
// durante la petición actual.
import org.springframework.security.core.context.SecurityContextHolder;


// Representa la autoridad o rol que tendrá
// el usuario autenticado.
import org.springframework.security.core.authority.SimpleGrantedAuthority;


// Registra esta clase como un componente de Spring.
@Component
public class JwtFilter extends OncePerRequestFilter {


    // Utilidad encargada de generar, validar
    // y extraer información de los tokens JWT.
    private final JwtUtil jwtUtil;


    // Constructor utilizado para inyectar
    // la utilidad JWT.
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    // Intercepta cada petición HTTP para comprobar
    // si contiene un token JWT válido.
    @Override
    protected void doFilterInternal(

            // Recibe la solicitud HTTP enviada
            // desde el frontend.
            jakarta.servlet.http.HttpServletRequest request,

            // Permite enviar la respuesta HTTP
            // al cliente.
            jakarta.servlet.http.HttpServletResponse response,

            // Permite continuar con la cadena
            // de filtros de Spring Security.
            jakarta.servlet.FilterChain filterChain)

            throws jakarta.servlet.ServletException, java.io.IOException {


        // Mensaje de prueba utilizado para comprobar
        // que el filtro se está ejecutando.
        System.out.println("Entró al JwtFilter");


        // Obtiene el encabezado Authorization
        // enviado en la petición HTTP.
        String authHeader = request.getHeader("Authorization");


        // Verifica si el encabezado existe
        // y si contiene el formato esperado:
        //
        // Authorization: Bearer <token>
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {


            // Informa que la petición no contiene
            // un token Bearer válido.
            System.out.println("No llegó un Bearer Token");


            // Continúa con la petición sin establecer
            // una autenticación.
            filterChain.doFilter(request, response);

            return;
        }


        // Extrae únicamente el token JWT,
        // eliminando el texto inicial "Bearer ".
        String token = authHeader.substring(7);


        // Muestra el token recibido para realizar
        // pruebas y depuración durante el desarrollo.
        System.out.println("Token recibido: " + token);


        // Comprueba si el token recibido
        // es válido.
        System.out.println(
                "¿Token válido?: "
                        + jwtUtil.validarToken(token)
        );


        // Verifica nuevamente si el token JWT
        // es válido antes de autenticar al usuario.
        if (jwtUtil.validarToken(token)) {


            // Extrae el correo electrónico
            // almacenado dentro del token.
            String correo = jwtUtil.extraerCorreo(token);


            // Extrae el rol almacenado
            // dentro del token JWT.
            String rol = jwtUtil.extraerRol(token);


            // Muestra el correo del usuario autenticado
            // para facilitar la depuración.
            System.out.println(
                    "JWT válido para: " + correo
            );


            // Muestra el rol obtenido desde el JWT.
            System.out.println(
                    "rol del usuario: " + rol
            );


            // Crea la autoridad de Spring Security
            // correspondiente al rol del usuario.
            //
            // Spring Security utiliza el prefijo
            // "ROLE_" para identificar los roles.
            SimpleGrantedAuthority autoridad =
                    new SimpleGrantedAuthority(
                            "ROLE_" + rol
                    );


            // Crea el objeto de autenticación
            // utilizando el correo como identificador
            // del usuario.
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            correo,
                            null,
                            java.util.List.of(autoridad)
                    );


            // Guarda la autenticación en el contexto
            // de seguridad de Spring.
            //
            // A partir de este momento Spring Security
            // reconoce al usuario como autenticado
            // durante esta petición.
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
        }


        // Continúa con el siguiente filtro de la cadena
        // para que la solicitud pueda llegar al controlador.
        filterChain.doFilter(request, response);
    }
}