package com.ecovida.ecovida.service;

// Importa la clase List para manejar listas de datos
import java.util.List;

// Importa el DTO utilizado para representar la información
// que será incluida en el reporte de residuos
import com.ecovida.ecovida.dto.ReporteResiduoDTO;

// Interfaz que define los servicios relacionados
// con la generación de reportes de residuos
public interface ReporteService {

    // Genera un reporte de residuos asociado a un usuario específico.
    // Recibe el identificador del usuario y retorna una lista
    // con la información necesaria para construir el reporte.
    List<ReporteResiduoDTO> generaReportePorUsuario(Long idUsuario);
}