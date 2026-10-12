package com.ecovida.ecovida.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecovida.ecovida.dto.ReporteResiduoDTO;
import com.ecovida.ecovida.service.ReporteService;


// Indica que esta clase es un controlador REST.
// Permite exponer servicios web que pueden ser
// consumidos por el frontend desarrollado en React.
@RestController


// Define la ruta base para los servicios
// relacionados con los reportes.
//
// Todos los endpoints de este controlador
// comienzan con:
// /api/reportes
@RequestMapping("/api/reportes")
public class ReporteController {


    // Servicio encargado de generar
    // la información de los reportes.
    private final ReporteService reporteService;


    // Constructor utilizado para inyectar
    // el servicio de reportes mediante Spring.
    public ReporteController(
            ReporteService reporteService) {

        this.reporteService = reporteService;
    }


    // Genera un reporte completo asociado
    // a un usuario específico.
    //
    // Endpoint:
    // GET /api/reportes/usuario/{idUsuario}
    //
    // Recibe el identificador del usuario
    // y retorna la información del reporte
    // utilizando el DTO ReporteResiduoDTO.
    @GetMapping("/usuario/{idUsuario}")
    public List<ReporteResiduoDTO> generarReportePorUsuario(
            @PathVariable Long idUsuario) {


        // Envía el identificador del usuario
        // al servicio encargado de construir
        // el reporte.
        return reporteService.generaReportePorUsuario(
                idUsuario
        );
    }
}