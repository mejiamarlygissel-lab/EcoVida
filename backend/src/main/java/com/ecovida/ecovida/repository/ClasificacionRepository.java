package com.ecovida.ecovida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecovida.ecovida.model.Clasificacion;


// Indica que esta interfaz funciona como repositorio de Spring.
@Repository
public interface ClasificacionRepository
        extends JpaRepository<Clasificacion, Long> {


    // Busca todas las clasificaciones relacionadas con una
    // recolección utilizando el identificador de la recolección.
    //
    // Spring Data JPA utiliza el nombre del método para acceder
    // al campo idRecoleccion de la entidad Recoleccion relacionada.
    List<Clasificacion> findByRecoleccion_IdRecoleccion(
            Long idRecoleccion
    );


    // Hereda automáticamente las operaciones básicas de JPA,
    // como guardar, buscar, actualizar y eliminar clasificaciones.
}