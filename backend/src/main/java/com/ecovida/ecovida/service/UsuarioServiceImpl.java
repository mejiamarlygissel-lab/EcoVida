package com.ecovida.ecovida.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecovida.ecovida.model.Usuario;
import com.ecovida.ecovida.repository.UsuarioRepository;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Indica que esta clase pertenece a la capa de servicios de Spring.
@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Repositorio utilizado para acceder a los datos de los usuarios.
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    // Constructor que recibe el repositorio mediante inyección de dependencias.
    public UsuarioServiceImpl(

            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder)

    {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Obtiene todos los usuarios registrados en la base de datos.
    @Override
    public List<Usuario> listarUsuarios() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String correo = authentication.getName();

        String rol = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        System.out.println("Usuario autenticado: " + correo);
        System.out.println("Rol detectado: " + rol);

        if (rol.equals("ROLE_ADMINISTRADOR")
                || rol.equals("ROLE_JEFE")) {

            return usuarioRepository.findAll();

        }

        Usuario usuario = usuarioRepository.findByCorreo(correo);

        List<Usuario> resultado = new ArrayList<>();

        if (usuario != null) {
            resultado.add(usuario);

        }

        return resultado;

    }

    // Busca un usuario utilizando su identificador.
    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long idUsuario) {

        return usuarioRepository.findById(idUsuario);
    }

    // Busca un usuario utilizando su correo electrónico.
    @Override
    public Optional<Usuario> buscarUsuarioPorCorreo(String correo) {

        // Consulta el usuario mediante su correo.
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        // Verifica si se encontró un usuario.
        if (usuario != null) {

            return Optional.of(usuario);
        }

        // Retorna un Optional vacío cuando el usuario no existe.
        return Optional.empty();
    }

    // Guarda un usuario nuevo o actualiza uno existente.
    @Override
    public Usuario registarUsuario(Usuario usuario) {

        // Encripta la contraseña antes de guardarla en la base de datos.
        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword()));

        // Guarda el usuario en la base de datos.
        return usuarioRepository.save(usuario);
    }

    @Override
    public boolean eliminarUsuario(Long idUsuario) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String rol = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        System.out.println("Rol que intenta eliminar: " + rol);

        // Solo el ADMINISTRADOR puede eliminar usuarios
        if (!rol.equals("ROLE_ADMINISTRADOR")) {
            return false;
        }

        // Verifica si el usuario existe
        if (!usuarioRepository.existsById(idUsuario)) {
            return false;
        }

        try {

            usuarioRepository.deleteById(idUsuario);
            usuarioRepository.flush();

            return true;

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "No se puede eliminar este usuario porque tiene registros asociados.");

        }

    }

    // Valida las credenciales proporcionadas durante el inicio de sesión.
    @Override
    public Usuario login(Usuario usuario) {

        // Busca el usuario registrado utilizando su correo electrónico.
        Usuario usuarioEncontrado =

                usuarioRepository.findByCorreo(usuario.getCorreo());

        // Verifica que el usuario exista y que la contraseña coincida
        // con la contraseña almacenada en la base de datos.
        if (usuarioEncontrado != null &&
                passwordEncoder.matches(
                        usuario.getPassword(),
                        usuarioEncontrado.getPassword())) {

            // Retorna el usuario autenticado.
            return usuarioEncontrado;
        }

        // Retorna null cuando las credenciales no son correctas.
        return null;
    }
}
