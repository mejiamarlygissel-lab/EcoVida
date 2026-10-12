package com.ecovida.ecovida.service;

// Define los métodos que deberá implementar el servicio
// encargado de consultar una API pública de ubicación.
public interface ApiPublicaUbicacionService {

    // Obtiene la dirección o ubicación a partir de las
    // coordenadas de latitud y longitud.
    String obtenerUbicacion(double lat, double lon);

}