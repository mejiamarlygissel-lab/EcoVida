package com.ecovida.ecovida.service;

import java.util.List;
import java.util.Optional;

import com.ecovida.ecovida.model.Recoleccion;

// Define los métodos que deberá implementar el servicio
// encargado de gestionar las recolecciones.
public interface RecoleccionService {

    // Obtiene la lista de todas las recolecciones registradas
    List<Recoleccion> listarRecolecciones();

    // Busca una recolección por su identificador
    Optional<Recoleccion> buscarRecoleccionPorId(Long idRecoleccion);

    // Obtiene las recolecciones asociadas a un usuario específico
    List<Recoleccion> buscarPorIdUsuario(Long idUsuario);

    // Guarda una nueva recolección o actualiza una existente
    Recoleccion guardarRecoleccion(Recoleccion recoleccion);

    // Elimina una recolección por su identificador
    boolean eliminarRecoleccion(Long idRecoleccion);
}