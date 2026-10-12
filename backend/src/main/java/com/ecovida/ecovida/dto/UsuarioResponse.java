package com.ecovida.ecovida.dto;

import com.ecovida.ecovida.model.Rol;


// DTO utilizado para enviar la información del usuario
// al frontend sin exponer datos sensibles,
// como la contraseña.
public class UsuarioResponse {


    // Identificador único del usuario.
    private Long idUsuario;


    // Nombre del usuario.
    private String nombre;


    // Apellido del usuario.
    private String apellido;


    // Correo electrónico del usuario.
    private String correo;


    // Número de teléfono del usuario.
    private String telefono;


    // Rol asignado al usuario dentro del sistema.
    private Rol rol;


    // Constructor vacío.
    //
    // Permite crear un objeto UsuarioResponse
    // sin proporcionar información inicialmente.
    public UsuarioResponse() {
    }


    // Constructor que permite inicializar
    // todos los datos que serán enviados
    // al cliente.
    public UsuarioResponse(
            Long idUsuario,
            String nombre,
            String apellido,
            String correo,
            String telefono,
            Rol rol) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
    }


    // Obtiene el identificador del usuario.
    public Long getIdUsuario() {
        return idUsuario;
    }


    // Modifica el identificador del usuario.
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }


    // Obtiene el nombre del usuario.
    public String getNombre() {
        return nombre;
    }


    // Modifica el nombre del usuario.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    // Obtiene el apellido del usuario.
    public String getApellido() {
        return apellido;
    }


    // Modifica el apellido del usuario.
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    // Obtiene el correo electrónico del usuario.
    public String getCorreo() {
        return correo;
    }


    // Modifica el correo electrónico del usuario.
    public void setCorreo(String correo) {
        this.correo = correo;
    }


    // Obtiene el número de teléfono del usuario.
    public String getTelefono() {
        return telefono;
    }


    // Modifica el número de teléfono del usuario.
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    // Obtiene el rol asignado al usuario.
    public Rol getRol() {
        return rol;
    }


    // Modifica el rol del usuario.
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}