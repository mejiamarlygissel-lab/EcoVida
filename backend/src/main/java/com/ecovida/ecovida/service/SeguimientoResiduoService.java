package com.ecovida.ecovida.service;

import java.util.List;
import java.util.Optional;

import com.ecovida.ecovida.model.SeguimientoResiduo;


// Define las operaciones disponibles para el servicio
// de seguimiento de residuos.
public interface SeguimientoResiduoService {


    // Obtiene la lista de todos los seguimientos registrados.
    List<SeguimientoResiduo> listarSeguimiento();


    // Busca un seguimiento utilizando su identificador.
    Optional<SeguimientoResiduo> buscarSeguimientoPorId(
            Long idSeguimientoResiduo
    );


    // Guarda un nuevo seguimiento o actualiza
    // uno existente.
    SeguimientoResiduo guardarSeguimiento(
            SeguimientoResiduo seguimientoResiduo
    );


    // Elimina un seguimiento utilizando su identificador.
    boolean eliminarSeguimiento(
            Long idSeguimientoResiduo
    );
}