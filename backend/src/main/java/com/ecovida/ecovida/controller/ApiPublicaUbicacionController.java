package com.ecovida.ecovida.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecovida.ecovida.service.ApiPublicaUbicacionService;


// Indica que esta clase es un controlador REST.
@RestController


// Define la ruta base para los servicios
// relacionados con las API públicas.
@RequestMapping("/api/publica")
public class ApiPublicaUbicacionController {


    // Servicio encargado de consumir la API pública
    // de ubicación.
    private final ApiPublicaUbicacionService apiPublicaUbicacionService;


    // Constructor utilizado para inyectar el servicio
    // de ubicación mediante Spring Boot.
    public ApiPublicaUbicacionController(
            ApiPublicaUbicacionService apiPublicaUbicacionService) {

        this.apiPublicaUbicacionService =
                apiPublicaUbicacionService;
    }


    // Endpoint utilizado para obtener una ubicación
    // a partir de las coordenadas geográficas.
    //
    // Recibe:
    // lat = latitud
    // lon = longitud
    //
    // Endpoint:
    // GET /api/publica/ubicacion?lat=...&lon=...
    @GetMapping("/ubicacion")
    public String obtenerUbicacion(
            @RequestParam double lat,
            @RequestParam double lon) {


        // Envía las coordenadas al servicio.
        //
        // El servicio se encarga de consultar
        // la API pública externa y obtener
        // la información de ubicación.
        return apiPublicaUbicacionService.obtenerUbicacion(
                lat,
                lon
        );
    }
}