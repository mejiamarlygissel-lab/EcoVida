package com.ecovida.ecovida.controller;

// Importa la anotación que permite recibir peticiones
// desde el frontend desarrollado en React.
import org.springframework.web.bind.annotation.CrossOrigin;

// Importa la anotación utilizada para crear servicios HTTP GET.
import org.springframework.web.bind.annotation.GetMapping;

// Importa la anotación que define la ruta base del controlador.
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// Importa la anotación que indica que esta clase es
// un controlador REST de Spring Boot.
import org.springframework.web.bind.annotation.RestController;

// Importa el servicio encargado de consumir
// la API pública externa de clima.
import com.ecovida.ecovida.service.ApiPublicaClimaService;

// Indica que esta clase es un controlador REST.
@RestController

// Define la ruta base de los servicios de la API pública.
//
// Todas las rutas de este controlador comenzarán con:
// /api/publica
@RequestMapping("/api/publica")

// Permite que el frontend desarrollado en React
// pueda consumir este servicio desde el puerto 5173.
@CrossOrigin(origins = "http://localhost:5173")
public class ApiPublicaController {

    // Servicio encargado de realizar la comunicación
    // con la API pública externa.
    private final ApiPublicaClimaService apiPublicaService;

    // Constructor utilizado para inyectar el servicio
    // de la API pública mediante Spring Boot.
    public ApiPublicaController(
            ApiPublicaClimaService apiPublicaService) {

        this.apiPublicaService = apiPublicaService;
    }

  
    // utilizando una API pública externa.
    //
    // Endpoint:
    // GET /api/publica/clima
    //
    // Este servicio funciona como intermediario entre
    // el frontend y la API pública externa.
    @GetMapping("/clima")
    public String obtenerClima(

            @RequestParam double lat,
            @RequestParam double lon) {
        return apiPublicaService.obtenerClima(lat, lon);
    }
}