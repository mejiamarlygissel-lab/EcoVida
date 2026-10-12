package com.ecovida.ecovida.service;

import java.util.List;
import java.util.Optional;

import com.ecovida.ecovida.model.Clasificacion;

// Define los métodos que deberá implementar el servicio de Clasificación
public interface ClasificacionService {

    // Obtiene la lista de todas las clasificaciones registradas
    List<Clasificacion> listarClasificaciones();

    // Busca una clasificación por su identificador
    Optional<Clasificacion> buscarClasificacionPorId(Long idClasificacion);

    // Guarda una nueva clasificación o actualiza una existente
    Clasificacion guardarClasificacion(Clasificacion clasificacion);

    // Elimina una clasificación por su identificador
    boolean eliminarClasificacion(Long idClasificacion);

}