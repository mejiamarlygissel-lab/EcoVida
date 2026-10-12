package com.ecovida.ecovida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecovida.ecovida.model.Clasificacion;
import com.ecovida.ecovida.model.SeguimientoResiduo;


// Indica que esta interfaz funciona como repositorio de Spring.
@Repository
public interface SeguimientoResiduoRepository
        extends JpaRepository<SeguimientoResiduo, Long> {


    // Busca todos los seguimientos asociados a una
    // clasificación específica.
    //
    // Spring Data JPA utiliza la relación clasificacion
    // de la entidad SeguimientoResiduo para realizar
    // automáticamente la consulta.
    List<SeguimientoResiduo> findByClasificacion(
            Clasificacion clasificacion
    );


    // Hereda automáticamente las operaciones básicas de JPA,
    // como guardar, buscar, actualizar y eliminar seguimientos.
}