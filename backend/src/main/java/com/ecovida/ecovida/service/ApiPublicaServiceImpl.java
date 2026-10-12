package com.ecovida.ecovida.service;

// Importa HttpEntity para enviar información junto con la petición HTTP
import org.springframework.http.HttpEntity;

// Importa HttpHeaders para configurar los encabezados de la petición
import org.springframework.http.HttpHeaders;

// Importa HttpMethod para indicar el tipo de petición HTTP
import org.springframework.http.HttpMethod;

// Importa ResponseEntity para recibir la respuesta de la API externa
import org.springframework.http.ResponseEntity;

// Importa la anotación @Service para registrar esta clase como un servicio de Spring
import org.springframework.stereotype.Service;

// Importa RestTemplate para realizar peticiones HTTP a servicios externos
import org.springframework.web.client.RestTemplate;

// Indica que esta clase es un servicio administrado por Spring Boot
@Service
public class ApiPublicaServiceImpl implements ApiPublicaUbicacionService {

    // Objeto encargado de realizar las peticiones HTTP
    // hacia la API pública de ubicación.
    private final RestTemplate restTemplate;

    // Constructor de la clase.
    // Inicializa el objeto RestTemplate para realizar
    // las peticiones a servicios externos.
    public ApiPublicaServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    // Método encargado de consultar una API pública
    // para obtener la dirección correspondiente a unas coordenadas.
    @Override
    public String obtenerUbicacion(double lat, double lon) {

        // Construye la URL de la API pública Nominatim.
        // Se envían la latitud y longitud recibidas como parámetros
        // para obtener información sobre la ubicación.
        String url =
                "https://nominatim.openstreetmap.org/reverse?format=json&lat="
                + lat
                + "&lon="
                + lon
                + "&zoom=18&addressdetails=1";

        // Crea los encabezados necesarios para la petición HTTP.
        HttpHeaders headers = new HttpHeaders();

        // Identifica la aplicación ante el servicio Nominatim
        // mediante el encabezado User-Agent.
        headers.set(
                "User-Agent",
                "EcoVida-App/1.0 contacto@ecovida.com"
        );

        // Crea la entidad HTTP utilizando los encabezados configurados.
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Realiza una petición HTTP GET a la API pública.
        // La respuesta se recibe como una cadena de texto
        // que contiene la información en formato JSON.
        ResponseEntity<String> respuesta =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        String.class
                );

        // Retorna el contenido de la respuesta obtenida
        // desde la API pública de ubicación.
        return respuesta.getBody();
    }
}