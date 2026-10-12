package com.ecovida.ecovida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecovida.ecovida.model.Recoleccion;


// Indica que esta interfaz funciona como repositorio de Spring.
@Repository
public interface RecoleccionRepository
        extends JpaRepository<Recoleccion, Long> {


    // Busca todas las recolecciones asociadas a un usuario.
    //
    // Spring Data JPA utiliza el nombre del método para generar
    // automáticamente la consulta utilizando el campo idUsuario.
    List<Recoleccion> findByIdUsuario(Long idUsuario);

}