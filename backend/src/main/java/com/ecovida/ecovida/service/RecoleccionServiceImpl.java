package com.ecovida.ecovida.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecovida.ecovida.model.Recoleccion;
import com.ecovida.ecovida.model.Usuario;
import com.ecovida.ecovida.repository.RecoleccionRepository;
import com.ecovida.ecovida.repository.UsuarioRepository;


// Indica que esta clase pertenece a la capa de servicios de Spring.
@Service
public class RecoleccionServiceImpl implements RecoleccionService {


    // Repositorio utilizado para consultar la información
    // de los usuarios registrados.
    private UsuarioRepository usuarioRepository;


    // Repositorio utilizado para realizar las operaciones
    // relacionadas con las recolecciones.
    private final RecoleccionRepository recoleccionRepository;


    // Constructor utilizado para inyectar los repositorios
    // necesarios para trabajar con usuarios y recolecciones.
    public RecoleccionServiceImpl(
            RecoleccionRepository recoleccionRepository,
            UsuarioRepository usuarioRepository) {

        this.recoleccionRepository = recoleccionRepository;
        this.usuarioRepository = usuarioRepository;
    }


    // Obtiene las recolecciones disponibles para el usuario
    // que actualmente se encuentra autenticado.
    //
    // Los administradores y jefes pueden consultar todas
    // las recolecciones registradas.
    //
    // Los demás usuarios solamente pueden consultar las
    // recolecciones asociadas a su propio usuario.
    @Override
    public List<Recoleccion> listarRecolecciones() {


        // Obtiene la información del usuario autenticado
        // desde el contexto de seguridad de Spring.
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        // Obtiene el correo del usuario autenticado.
        String correo = authentication.getName();


        // Obtiene el rol del usuario autenticado.
        String rol = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();


        // Muestra en consola el rol detectado para facilitar
        // la revisión durante las pruebas del sistema.
        System.out.println("Rol detectado: " + rol);


        // Busca en la base de datos al usuario que corresponde
        // al correo autenticado.
        Usuario usuarioAutenticado =
                usuarioRepository.findByCorreo(correo);


        // Obtiene el identificador del usuario autenticado.
        Long idUsuario =
                usuarioAutenticado.getIdUsuario();


        // Los usuarios con rol de administrador o jefe pueden
        // consultar todas las recolecciones del sistema.
        if (rol.equals("ROLE_ADMINISTRADOR")
                || rol.equals("ROLE_JEFE")) {

            return recoleccionRepository.findAll();
        }


        // Los demás usuarios solamente reciben las recolecciones
        // asociadas a su propio identificador.
        return recoleccionRepository.findByIdUsuario(idUsuario);
    }


    // Busca una recolección utilizando su identificador.
    @Override
    public Optional<Recoleccion> buscarRecoleccionPorId(
            Long idRecoleccion) {

        return recoleccionRepository.findById(idRecoleccion);
    }


    // Obtiene todas las recolecciones asociadas
    // a un usuario específico.
    @Override
    public List<Recoleccion> buscarPorIdUsuario(Long idUsuario) {

        return recoleccionRepository.findByIdUsuario(idUsuario);
    }


    // Guarda una nueva recolección.
    //
    // El usuario asociado se obtiene directamente desde
    // la sesión autenticada para evitar que el frontend
    // pueda asignar una recolección a otro usuario.
    @Override
    @SuppressWarnings("unused")
    public Recoleccion guardarRecoleccion(
            Recoleccion recoleccion) {


        // Obtiene la información del usuario autenticado
        // desde el contexto de seguridad de Spring.
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        // Obtiene el correo del usuario autenticado.
        String correo = authentication.getName();


        // Muestra en consola el usuario autenticado durante
        // las pruebas del sistema.
        System.out.println(
                "Usuario autenticado en Spring: " + correo
        );


        // Busca el usuario correspondiente al correo obtenido.
        Usuario usuarioAutenticado =
                usuarioRepository.findByCorreo(correo);


        // Verifica que el usuario autenticado exista.
        if (usuarioAutenticado == null) {

            throw new RuntimeException(
                    "Error: No se encontró el usuario autenticado."
            );
        }


        // Obtiene el identificador del usuario autenticado.
        Long idUsuario =
                usuarioAutenticado.getIdUsuario();


        // Muestra el identificador del usuario durante
        // las pruebas del sistema.
        System.out.println(
                "ID del usuario autenticado: " + idUsuario
        );


        // Asigna automáticamente el usuario autenticado
        // a la nueva recolección.
        recoleccion.setIdUsuario(idUsuario);


        // Guarda la recolección en la base de datos.
        return recoleccionRepository.save(recoleccion);
    }


    // Elimina una recolección utilizando su identificador.
    @Override
    public boolean eliminarRecoleccion(Long idRecoleccion) {


        // Verifica si la recolección existe antes de eliminarla.
        if (recoleccionRepository.existsById(idRecoleccion)) {


            // Elimina la recolección de la base de datos.
            recoleccionRepository.deleteById(idRecoleccion);


            // Indica que la eliminación fue realizada correctamente.
            return true;
        }


        // Indica que la recolección no existe.
        return false;
    }
}