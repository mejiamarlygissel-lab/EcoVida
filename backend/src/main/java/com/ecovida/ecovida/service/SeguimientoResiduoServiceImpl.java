package com.ecovida.ecovida.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecovida.ecovida.model.Clasificacion;
import com.ecovida.ecovida.model.Recoleccion;
import com.ecovida.ecovida.model.SeguimientoResiduo;
import com.ecovida.ecovida.model.Usuario;
import com.ecovida.ecovida.repository.ClasificacionRepository;
import com.ecovida.ecovida.repository.RecoleccionRepository;
import com.ecovida.ecovida.repository.SeguimientoResiduoRepository;
import com.ecovida.ecovida.repository.UsuarioRepository;


// Indica que esta clase pertenece a la capa de servicios de Spring.
@Service
public class SeguimientoResiduoServiceImpl
        implements SeguimientoResiduoService {


    // Repositorio utilizado para realizar operaciones
    // sobre los seguimientos de residuos.
    private final SeguimientoResiduoRepository seguimientoResiduoRepository;


    // Repositorio utilizado para consultar las recolecciones
    // registradas en la base de datos.
    private final RecoleccionRepository recoleccionRepository;


    // Repositorio utilizado para consultar los usuarios
    // registrados en el sistema.
    private final UsuarioRepository usuarioRepository;


    // Repositorio utilizado para consultar las clasificaciones
    // asociadas a las recolecciones.
    private final ClasificacionRepository clasificacionRepository;


    // Constructor utilizado para inyectar los repositorios
    // necesarios para el funcionamiento del servicio.
    public SeguimientoResiduoServiceImpl(
            SeguimientoResiduoRepository seguimientoResiduoRepository,
            RecoleccionRepository recoleccionRepository,
            ClasificacionRepository clasificacionRepository,
            UsuarioRepository usuarioRepository) {

        this.seguimientoResiduoRepository =
                seguimientoResiduoRepository;

        this.recoleccionRepository =
                recoleccionRepository;

        this.clasificacionRepository =
                clasificacionRepository;

        this.usuarioRepository =
                usuarioRepository;
    }


    // Obtiene los seguimientos disponibles para el usuario
    // actualmente autenticado.
    //
    // Los administradores y jefes pueden consultar todos
    // los seguimientos registrados.
    //
    // Los demás usuarios solamente pueden consultar los
    // seguimientos relacionados con sus propias recolecciones
    // y clasificaciones.
    @Override
    public List<SeguimientoResiduo> listarSeguimiento() {


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
                "Rol detectado en Seguimiento: " + rol
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
                "ID usuario en Seguimiento: " + idUsuario
        );


        // Los administradores y jefes pueden consultar
        // todos los seguimientos registrados.
        if (rol.equals("ROLE_ADMINISTRADOR")
                || rol.equals("ROLE_JEFE")) {

            return seguimientoResiduoRepository.findAll();
        }


        // Obtiene las recolecciones pertenecientes
        // al usuario autenticado.
        List<Recoleccion> recolecciones =
                recoleccionRepository.findByIdUsuario(idUsuario);


        // Crea una lista donde se almacenarán los
        // seguimientos correspondientes al usuario.
        List<SeguimientoResiduo> seguimientos =
                new ArrayList<>();


        // Recorre las recolecciones del usuario.
        for (Recoleccion recoleccion : recolecciones) {


            // Busca las clasificaciones relacionadas
            // con la recolección actual.
            List<Clasificacion> clasificaciones =
                    clasificacionRepository
                            .findByRecoleccion_IdRecoleccion(
                                    recoleccion.getIdRecoleccion()
                            );


            // Recorre las clasificaciones encontradas.
            for (Clasificacion clasificacion : clasificaciones) {


                // Busca los seguimientos asociados
                // a la clasificación actual.
                List<SeguimientoResiduo> seguimientoClasificacion =
                        seguimientoResiduoRepository
                                .findByClasificacion(
                                        clasificacion
                                );


                // Agrega los seguimientos encontrados
                // a la lista general.
                seguimientos.addAll(
                        seguimientoClasificacion
                );
            }
        }


        // Retorna los seguimientos relacionados
        // con las recolecciones del usuario.
        return seguimientos;
    }


    // Busca un seguimiento utilizando su identificador.
    @Override
    public Optional<SeguimientoResiduo> buscarSeguimientoPorId(
            Long idSeguimientoResiduo) {

        return seguimientoResiduoRepository.findById(
                idSeguimientoResiduo
        );
    }


    // Guarda un nuevo seguimiento o actualiza
    // uno existente.
    @Override
    public SeguimientoResiduo guardarSeguimiento(
            SeguimientoResiduo seguimientoResiduo) {

        // Guarda el seguimiento en la base de datos.
        return seguimientoResiduoRepository.save(
                seguimientoResiduo
        );
    }


    // Elimina un seguimiento utilizando su identificador.
    @Override
    public boolean eliminarSeguimiento(
            Long idSeguimientoResiduo) {


        // Verifica si el seguimiento existe antes
        // de realizar la eliminación.
        if (seguimientoResiduoRepository.existsById(
                idSeguimientoResiduo)) {


            // Elimina el seguimiento de la base de datos.
            seguimientoResiduoRepository.deleteById(
                    idSeguimientoResiduo
            );


            // Indica que la eliminación fue exitosa.
            return true;
        }


        // Indica que el seguimiento no existe.
        return false;
    }
}