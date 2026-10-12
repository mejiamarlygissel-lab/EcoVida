package com.ecovida.ecovida.service;

// Importa la anotación @Service para indicar que esta clase
// pertenece a la capa de lógica de negocio de Spring Boot.
import org.springframework.stereotype.Service;

// Importa la clase RestTemplate, utilizada para realizar
// peticiones HTTP hacia servicios web o API REST externas.
import org.springframework.web.client.RestTemplate;

// Indica que esta clase es un servicio administrado por Spring.
@Service
public class ApiPublicaClimaService {

    // Objeto encargado de realizar las peticiones HTTP
    // hacia una API pública externa.
    private final RestTemplate restTemplate;

    // Constructor de la clase.
    // Inicializa el objeto RestTemplate para poder consumir
    // servicios web externos.
    public ApiPublicaClimaService() {
        this.restTemplate = new RestTemplate();
    }

    // Método encargado de consultar el clima actual .
    // Retorna la información obtenida desde la API pública
    // en formato JSON.
    public String obtenerClima(double latitud, double longitud) {

        // URL de la API pública Open-Meteo.
        // Se utilizan las coordenadas 
        // para consultar las condiciones climáticas actuales.
        String url = "https://api.open-meteo.com/v1/forecast"
            + "?latitude=" + latitud
            + "&longitude=" + longitud
            + "&current=temperature_2m,relative_humidity_2m,is_day,precipitation,rain,weather_code";
        // Realiza una petición HTTP GET a la URL indicada.
        //
        // Parámetros:
        // - url: dirección de la API pública.
        // - String.class: indica que la respuesta será recibida
        //   como una cadena de texto en formato JSON.
        //
        // Retorna directamente la información obtenida
        // desde la API pública.
        return restTemplate.getForObject(url, String.class);
    }
}