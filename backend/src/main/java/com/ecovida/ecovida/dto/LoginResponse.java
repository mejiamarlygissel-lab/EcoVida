package com.ecovida.ecovida.dto;


// Representa la información que el backend
// devuelve al frontend después de un intento
// de inicio de sesión.
public class LoginResponse {


    // Mensaje que indica el resultado
    // de la autenticación.
    private String mensaje;


    // Correo electrónico del usuario autenticado.
    private String correo;


    // Token JWT generado para el usuario.
    //
    // Este token será utilizado posteriormente
    // para autenticar las peticiones realizadas
    // desde el frontend.
    private String token;



     private String rol;


    // Constructor vacío.
    //
    // Permite crear un objeto LoginResponse
    // sin proporcionar valores inicialmente.
    public LoginResponse() {
    }


    // Constructor que permite inicializar
    // todos los atributos de la respuesta.
    public LoginResponse(
            String mensaje,
            String correo,
            String token,
            String rol
        ) {

        this.mensaje = mensaje;
        this.correo = correo;
        this.token = token;
        this.rol = rol;
    }


    // Retorna el mensaje de la respuesta.
    public String getMensaje() {
        return mensaje;
    }


    // Modifica el mensaje de la respuesta.
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    // Retorna el correo del usuario autenticado.
    public String getCorreo() {
        return correo;
    }


    // Modifica el correo del usuario.
    public void setCorreo(String correo) {
        this.correo = correo;
    }


    // Retorna el token JWT generado.
    public String getToken() {
        return token;
    }


    // Modifica el token JWT.
    public void setToken(String token) {
        this.token = token;
    }


     // Retorna el rol generado.
    public String getRol() {
        return rol;
    }

     // Modifica el rol JWT.
    public void setRol(String rol) {
        this.rol = rol;
    }
}