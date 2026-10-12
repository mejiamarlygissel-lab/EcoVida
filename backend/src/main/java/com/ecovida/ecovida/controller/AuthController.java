package com.ecovida.ecovida.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Importa el objeto que contiene la respuesta
// que se devuelve después del inicio de sesión.
import com.ecovida.ecovida.dto.LoginResponse;

// Importa el modelo Usuario utilizado para recibir
// los datos de inicio de sesión.
import com.ecovida.ecovida.model.Usuario;

// Importa la clase encargada de generar
// los tokens de seguridad JWT.
import com.ecovida.ecovida.security.JwtUtil;

// Importa el servicio encargado de validar
// las credenciales del usuario.
import com.ecovida.ecovida.service.UsuarioService;


// Indica que esta clase es un controlador REST.
// Permite recibir solicitudes HTTP y responder
// información en formato JSON.
@RestController


// Define la ruta principal para los servicios
// relacionados con la autenticación.
//
// Los endpoints de esta clase comienzan con:
// /api/auth
@RequestMapping("/api/auth")


// Permite que el frontend desarrollado en React
// pueda consumir este controlador desde el puerto 5173.
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {


    // Objeto encargado de generar los tokens JWT.
    private final JwtUtil jwtUtil;


    // Servicio encargado de buscar usuarios
    // y validar sus credenciales.
    private final UsuarioService usuarioService;


    // Constructor utilizado para inyectar
    // las dependencias necesarias.
    public AuthController(
            UsuarioService usuarioService,
            JwtUtil jwtUtil) {

        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }


    // Define el endpoint utilizado para iniciar sesión.
    //
    // Método HTTP:
    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody Usuario usuario) {


        // Envía el correo y la contraseña al servicio
        // para verificar las credenciales del usuario.
        Usuario usuarioAutenticado =
                usuarioService.login(usuario);


        // Crea el objeto que contendrá la respuesta
        // que será enviada al frontend.
        LoginResponse respuesta = new LoginResponse();


        // Verifica si las credenciales proporcionadas
        // son correctas.
        if (usuarioAutenticado != null) {


            // Genera el token JWT utilizando el correo
            // y el rol del usuario autenticado.
            //
            // El rol se incluye dentro del token para
            // permitir posteriormente controlar los permisos.
            String token = jwtUtil.generarToken(
                    usuarioAutenticado.getCorreo(),
                    usuarioAutenticado.getRol().name()
            );


            // Guarda el token JWT en la respuesta.
            respuesta.setToken(token);


            // Define el mensaje de confirmación
            // para el inicio de sesión.
            respuesta.setMensaje(
                    "Autenticación satisfactoria"
            );


            // Guarda el correo del usuario autenticado
            // en la respuesta.
            respuesta.setCorreo(
                    usuarioAutenticado.getCorreo()
            );


            respuesta.setRol(
                usuarioAutenticado.getRol().name()

            );


            // Retorna HTTP 200 indicando que la autenticación
            // fue realizada correctamente.
            return ResponseEntity.ok(respuesta);


        } else {


            // Define el mensaje cuando el correo
            // o la contraseña son incorrectos.
            respuesta.setMensaje(
                    "Correo o contraseña incorrectos"
            );


            // Retorna HTTP 401 indicando que las credenciales
            // proporcionadas no son válidas.
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(respuesta);
        }
    }
}