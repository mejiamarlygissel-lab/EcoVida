package com.ecovida.ecovida.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecovida.ecovida.model.Clasificacion;
import com.ecovida.ecovida.model.Recoleccion;
import com.ecovida.ecovida.model.Usuario;
import com.ecovida.ecovida.repository.ClasificacionRepository;
import com.ecovida.ecovida.repository.RecoleccionRepository;
import com.ecovida.ecovida.repository.UsuarioRepository;


// Indica que esta clase pertenece a la capa de servicios de Spring.
@Service
public class ClasificacionServiceImpl implements ClasificacionService {


    // Repositorio utilizado para realizar operaciones
    // sobre las clasificaciones.
    private final ClasificacionRepository clasificacionRepository;


    // Repositorio utilizado para consultar las recolecciones
    // registradas en la base de datos.
    private final RecoleccionRepository recoleccionRepository;


    // Repositorio utilizado para consultar la información
    // de los usuarios registrados.
    private final UsuarioRepository usuarioRepository;


    // Constructor utilizado para inyectar los repositorios
    // necesarios para el funcionamiento del servicio.
    public ClasificacionServiceImpl(
            ClasificacionRepository clasificacionRepository,
            RecoleccionRepository recoleccionRepository,
            UsuarioRepository usuarioRepository) {

        this.clasificacionRepository = clasificacionRepository;
        this.recoleccionRepository = recoleccionRepository;
        this.usuarioRepository = usuarioRepository;
    }


    // Obtiene la lista de clasificaciones disponibles
    // para el usuario actualmente autenticado.
    //
    // Los administradores y jefes pueden consultar todas
    // las clasificaciones.
    //
    // Los demás usuarios solamente pueden consultar las
    // clasificaciones relacionadas con sus propias recolecciones.
    @Override
    public List<Clasificacion> listarClasificaciones() {


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


        // Muestra el rol detectado durante las pruebas
        // del sistema.
        System.out.println(
                "Rol detectado en Clasificación: " + rol
        );


        // Busca en la base de datos al usuario correspondiente
        // al correo autenticado.
        Usuario usuarioAutenticado =
                usuarioRepository.findByCorreo(correo);


        // Obtiene el identificador del usuario autenticado.
        Long idUsuario =
                usuarioAutenticado.getIdUsuario();


        // Muestra el identificador del usuario durante
        // las pruebas del sistema.
        System.out.println(
                "ID usuario en Clasificación: " + idUsuario
        );


        // Los administradores y jefes pueden consultar
        // todas las clasificaciones registradas.
        if (rol.equals("ROLE_ADMINISTRADOR")
                || rol.equals("ROLE_JEFE")) {

            return clasificacionRepository.findAll();
        }


        // Obtiene las recolecciones pertenecientes
        // al usuario autenticado.
        List<Recoleccion> recolecciones =
                recoleccionRepository.findByIdUsuario(idUsuario);


        // Crea una lista donde se almacenarán las
        // clasificaciones relacionadas con esas recolecciones.
        List<Clasificacion> clasificaciones =
                new ArrayList<>();


        // Recorre todas las recolecciones del usuario.
        for (Recoleccion recoleccion : recolecciones) {


            // Busca las clasificaciones asociadas
            // a la recolección actual.
            List<Clasificacion> clasificacionesRecolecion =
                    clasificacionRepository
                            .findByRecoleccion_IdRecoleccion(
                                    recoleccion.getIdRecoleccion()
                            );


            // Agrega las clasificaciones encontradas
            // a la lista general.
            clasificaciones.addAll(clasificacionesRecolecion);
        }


        // Retorna las clasificaciones pertenecientes
        // al usuario autenticado.
        return clasificaciones;
    }


    // Busca una clasificación utilizando su identificador.
    @Override
    public Optional<Clasificacion> buscarClasificacionPorId(
            Long idClasificacion) {

        return clasificacionRepository.findById(idClasificacion);
    }


    // Guarda una nueva clasificación o actualiza
    // una clasificación existente.
    @Override
    public Clasificacion guardarClasificacion(
            Clasificacion clasificacion) {


        // Obtiene la recolección asociada
        // a la clasificación recibida.
        Recoleccion recoleccion =
                clasificacion.getRecoleccion();


        // Verifica que se haya enviado una recolección.
        if (recoleccion == null) {

            throw new RuntimeException(
                    "Error: Debes proporcionar un ID de recolecion."
            );
        }


        // Obtiene el identificador de la recolección.
        Long idRecoleccion =
                recoleccion.getIdRecoleccion();


        // Verifica que la recolección exista antes
        // de guardar la clasificación.
        recoleccionRepository.findById(idRecoleccion)
                .orElseThrow(() -> new RuntimeException(
                        "La recolección con ID "
                                + idRecoleccion
                                + " no está registrada."
                ));


        // Guarda la clasificación en la base de datos.
        return clasificacionRepository.save(clasificacion);
    }


    // Elimina una clasificación utilizando
    // su identificador.
    @Override
    public boolean eliminarClasificacion(
            Long idClasificacion) {


        // Verifica si la clasificación existe
        // antes de eliminarla.
        if (clasificacionRepository.existsById(
                idClasificacion)) {


            // Elimina la clasificación de la base de datos.
            clasificacionRepository.deleteById(
                    idClasificacion
            );


            // Indica que la eliminación fue exitosa.
            return true;
        }


        // Indica que la clasificación no existe.
        return false;
    }
}