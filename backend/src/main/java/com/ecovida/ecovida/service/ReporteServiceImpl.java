package com.ecovida.ecovida.service;

// Importa ArrayList para crear listas dinámicas de reportes
import java.util.ArrayList;
// Importa List para manejar colecciones de datos
import java.util.List;

// Indica que esta clase pertenece a la capa de servicios de Spring
import org.springframework.stereotype.Service;

// Importa el DTO utilizado para construir la información del reporte
import com.ecovida.ecovida.dto.ReporteResiduoDTO;
// Importa los modelos utilizados para obtener la información del proceso
import com.ecovida.ecovida.model.Clasificacion;
import com.ecovida.ecovida.model.Recoleccion;
import com.ecovida.ecovida.model.SeguimientoResiduo;
import com.ecovida.ecovida.model.Usuario;
// Importa los repositorios necesarios para consultar la base de datos
import com.ecovida.ecovida.repository.ClasificacionRepository;
import com.ecovida.ecovida.repository.RecoleccionRepository;
import com.ecovida.ecovida.repository.SeguimientoResiduoRepository;
import com.ecovida.ecovida.repository.UsuarioRepository;

// Registra esta clase como un servicio administrado por Spring
@Service
public class ReporteServiceImpl implements ReporteService {

    // Repositorio utilizado para consultar la información de los usuarios
    private final UsuarioRepository usuarioRepository;

    // Repositorio utilizado para consultar las recolecciones
    private final RecoleccionRepository recoleccionRepository;

    // Repositorio utilizado para consultar las clasificaciones
    private final ClasificacionRepository clasificacionRepository;

    // Repositorio utilizado para consultar los seguimientos de residuos
    private final SeguimientoResiduoRepository seguimientoResiduoRepository;

    // Constructor que recibe los repositorios mediante inyección de dependencias
    public ReporteServiceImpl(
            UsuarioRepository usuarioRepository,
            RecoleccionRepository recoleccionRepository,
            ClasificacionRepository clasificacionRepository,
            SeguimientoResiduoRepository seguimientoResiduoRepository) {

        this.usuarioRepository = usuarioRepository;
        this.recoleccionRepository = recoleccionRepository;
        this.clasificacionRepository = clasificacionRepository;
        this.seguimientoResiduoRepository = seguimientoResiduoRepository;
    }

    // Genera un reporte completo del proceso de gestión de residuos
    // asociado a un usuario específico
    @Override
    public List<ReporteResiduoDTO> generaReportePorUsuario(Long idUsuario) {

        // Busca el usuario por su identificador
        // y genera una excepción si no existe
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con el ID: " + idUsuario));

        // Busca todas las recolecciones asociadas al usuario
        List<Recoleccion> recolecciones =
                recoleccionRepository.findByIdUsuario(idUsuario);

        // Muestra información del usuario en la consola
        // para facilitar la revisión durante las pruebas
        System.out.println("USUARIO: " + usuario.getIdUsuario());

        System.out.println("NOMBRE: "
                + usuario.getNombre() + " "
                + usuario.getApellido());

        // Muestra la cantidad de recolecciones encontradas
        System.out.println(
                "RECOLECCIONES ENCONTRADAS: "
                        + recolecciones.size());

        // Lista donde se almacenarán los reportes generados
        List<ReporteResiduoDTO> reportes = new ArrayList<>();

        // Recorre todas las recolecciones asociadas al usuario
        for (Recoleccion recoleccion : recolecciones) {

            // Muestra información de la recolección actual
            // para facilitar la depuración del proceso
            System.out.println(
                    "ID RECOLECCION: "
                            + recoleccion.getIdRecoleccion()
                            + " | TIPO: "
                            + recoleccion.getTipoResiduo()
                            + " | FECHA: "
                            + recoleccion.getFecha());

            // Busca las clasificaciones asociadas a la recolección actual
            List<Clasificacion> clasificaciones =
                    clasificacionRepository.findByRecoleccion_IdRecoleccion(
                            recoleccion.getIdRecoleccion());

            // Verifica si la recolección todavía no tiene clasificación
            if (clasificaciones == null || clasificaciones.isEmpty()) {

                // Crea un reporte únicamente con la información
                // disponible de la recolección
                ReporteResiduoDTO reporte =
                        crearReporteSinClasificacion(
                                usuario,
                                recoleccion);

                // Agrega el reporte a la lista de resultados
                reportes.add(reporte);

                // Continúa con la siguiente recolección
                continue;
            }

            // Recorre las clasificaciones asociadas a la recolección
            for (Clasificacion clasificacion : clasificaciones) {

                // Busca los seguimientos relacionados
                // con la clasificación actual
                List<SeguimientoResiduo> seguimientos =
                        seguimientoResiduoRepository
                                .findByClasificacion(clasificacion);

                // Verifica si la clasificación todavía no tiene seguimiento
                if (seguimientos == null || seguimientos.isEmpty()) {

                    // Crea un reporte con la información de la recolección
                    // y la clasificación, pero sin datos de seguimiento
                    ReporteResiduoDTO reporte =
                            crearReporteSinSeguimiento(
                                    usuario,
                                    recoleccion,
                                    clasificacion);

                    // Agrega el reporte a la lista de resultados
                    reportes.add(reporte);

                    // Continúa con la siguiente clasificación
                    continue;
                }

                // Recorre todos los seguimientos asociados
                // a la clasificación actual
                for (SeguimientoResiduo seguimiento : seguimientos) {

                    // Crea un reporte con toda la información disponible
                    // del usuario, recolección, clasificación y seguimiento
                    ReporteResiduoDTO reporte =
                            crearReporteCompleto(
                                    usuario,
                                    recoleccion,
                                    clasificacion,
                                    seguimiento);

                    // Agrega el reporte completo a la lista de resultados
                    reportes.add(reporte);
                }
            }
        }

        // Muestra en consola la cantidad total de reportes generados
        System.out.println(
                "TOTAL REPORTES GENERADOS: "
                        + reportes.size());

        // Retorna la lista de reportes generados
        return reportes;
    }

    // Crea un reporte cuando la recolección,
    // clasificación y seguimiento están disponibles
    private ReporteResiduoDTO crearReporteCompleto(
            Usuario usuario,
            Recoleccion recoleccion,
            Clasificacion clasificacion,
            SeguimientoResiduo seguimiento) {

        // Construye y retorna el DTO con toda la información
        // correspondiente al proceso completo
        return new ReporteResiduoDTO(

                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),

                recoleccion.getIdRecoleccion(),
                recoleccion.getFecha().toString(),
                recoleccion.getTipoResiduo().toString(),
                recoleccion.getCantidadKg(),
                recoleccion.getUbicacion(),

                clasificacion.getCategoria(),
                clasificacion.getDescripcion(),
                clasificacion.getFecha().toString(),

                seguimiento.getEstado().toString(),
                seguimiento.getTipoProceso().toString(),
                seguimiento.getResultado(),
                seguimiento.getFechaSeguimiento().toString()
        );
    }

    // Crea un reporte cuando existe una recolección y una clasificación,
    // pero todavía no existe un seguimiento
    private ReporteResiduoDTO crearReporteSinSeguimiento(
            Usuario usuario,
            Recoleccion recoleccion,
            Clasificacion clasificacion) {

        // Construye el DTO utilizando la información disponible
        // y deja los datos de seguimiento en null
        return new ReporteResiduoDTO(

                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),

                recoleccion.getIdRecoleccion(),
                recoleccion.getFecha().toString(),
                recoleccion.getTipoResiduo().toString(),
                recoleccion.getCantidadKg(),
                recoleccion.getUbicacion(),

                clasificacion.getCategoria(),
                clasificacion.getDescripcion(),
                clasificacion.getFecha().toString(),

                null,
                null,
                null,
                null
        );
    }

    // Crea un reporte cuando solamente existe la recolección
    // y todavía no se ha realizado la clasificación ni el seguimiento
    private ReporteResiduoDTO crearReporteSinClasificacion(
            Usuario usuario,
            Recoleccion recoleccion) {

        // Construye el DTO con los datos disponibles
        // y deja los campos de clasificación y seguimiento en null
        return new ReporteResiduoDTO(

                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),

                recoleccion.getIdRecoleccion(),
                recoleccion.getFecha().toString(),
                recoleccion.getTipoResiduo().toString(),
                recoleccion.getCantidadKg(),
                recoleccion.getUbicacion(),

                null,
                null,
                null,

                null,
                null,
                null,
                null
        );
    }
}