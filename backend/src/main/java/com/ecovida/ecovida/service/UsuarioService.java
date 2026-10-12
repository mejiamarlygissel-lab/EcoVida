package com.ecovida.ecovida.service;

import java.util.List;
import java.util.Optional;

import com.ecovida.ecovida.model.Usuario;

// Interfaz que define los métodos que debe implementar el servicio de usuarios
public interface UsuarioService {

    // Obtiene la lista de todos los usuarios registrados
    List<Usuario> listarUsuarios();

    // Busca un usuario mediante su identificador
    Optional<Usuario> buscarUsuarioPorId(Long idUsuario);

      Optional<Usuario> buscarUsuarioPorCorreo(String correo);

    // Guarda un nuevo usuario o actualiza uno existente
    Usuario registarUsuario(Usuario usuario);

    // Elimina un usuario por su identificador
    boolean eliminarUsuario(Long idUsuario);

    // Valida las credenciales del usuario para el inicio de sesión
    public Usuario login(Usuario usuario);

}