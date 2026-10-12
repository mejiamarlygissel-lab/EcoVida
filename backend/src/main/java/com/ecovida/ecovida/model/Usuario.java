package com.ecovida.ecovida.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


// Representa la entidad Usuario dentro de la base de datos.
@Entity

// Indica que esta entidad se relaciona con la tabla Usuario.
@Table(name = "Usuario")
public class Usuario {

    // Identificador único del usuario.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;


    // Nombre del usuario. Este campo es obligatorio.
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;


    // Apellido del usuario.
    @Column(name = "apellido")
    private String apellido;


    // Correo electrónico utilizado para identificar al usuario.
    @Email(message = "El correo no tiene un formato válido")
    @Column(name = "correo")
    private String correo;


    // Número de teléfono del usuario. Debe contener exactamente 10 dígitos.
    @Pattern(
        regexp = "\\d{10}",
        message = "El teléfono debe tener 10 dígitos"
    )
    @Column(name = "telefono")
    private String telefono;


    // Contraseña utilizada para la autenticación del usuario.
    @Column(name = "password")
    private String password;


    // Rol que determina los permisos del usuario dentro del sistema.
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;


    // Constructor vacío requerido por JPA.
    public Usuario() {
    }
    
    // costructor para iniciar el administrador
    public Usuario(
        String nombre,
        String apellido,
        String correo,
        String telefono,
        String password,
        Rol rol) {

    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.telefono = telefono;
    this.password = password;
    this.rol = rol;
}


    // Retorna el identificador del usuario.
    public Long getIdUsuario() {
        return idUsuario;
    }


    // Asigna el identificador del usuario.
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }


    // Retorna el nombre del usuario.
    public String getNombre() {
        return nombre;
    }


    // Asigna el nombre del usuario.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    // Retorna el apellido del usuario.
    public String getApellido() {
        return apellido;
    }


    // Asigna el apellido del usuario.
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    // Retorna el correo electrónico del usuario.
    public String getCorreo() {
        return correo;
    }


    // Asigna el correo electrónico del usuario.
    public void setCorreo(String correo) {
        this.correo = correo;
    }


    // Retorna el número de teléfono del usuario.
    public String getTelefono() {
        return telefono;
    }


    // Asigna el número de teléfono del usuario.
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    // Retorna la contraseña del usuario.
    public String getPassword() {
        return password;
    }


    // Asigna la contraseña del usuario.
    public void setPassword(String password) {
        this.password = password;
    }


    // Retorna el rol asignado al usuario.
    public Rol getRol() {
        return rol;
    }


    // Asigna el rol del usuario.
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}