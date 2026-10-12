package com.ecovida.ecovida.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecovida.ecovida.model.Clasificacion;
import com.ecovida.ecovida.repository.ClasificacionRepository;
import com.ecovida.ecovida.service.ClasificacionService;


// Permite que el frontend desarrollado en React pueda consumir
// los servicios de este controlador desde el puerto 5173.
@CrossOrigin(origins = "http://localhost:5173")


// Indica que esta clase es un controlador REST.
@RestController


// Define la ruta base para todos los servicios
// relacionados con las clasificaciones.
@RequestMapping("/api/clasificacion")
public class ClasificacionController {


    
  


    // Servicio encargado de manejar la lógica de negocio
    // relacionada con las clasificaciones.
    private final ClasificacionService clasificacionService;


    // Constructor utilizado para inyectar las dependencias
    // necesarias para trabajar con clasificaciones.
    public ClasificacionController(
            ClasificacionService clasificacionService,
            ClasificacionRepository clasificacionRepository) {

        this.clasificacionService = clasificacionService;
        
    }


    // Obtiene la lista de todas las clasificaciones registradas.
    //
    // Endpoint:
    // GET /api/clasificacion
    @GetMapping
    public List<Clasificacion> listarClasificaciones() {

        return clasificacionService.listarClasificaciones();
    }


    // Busca una clasificación utilizando su identificador.
    //
    // Endpoint:
    // GET /api/clasificacion/{idClasificacion}
    @GetMapping("/{idClasificacion}")
    public ResponseEntity<Clasificacion> buscarClasificacionPorId(
            @PathVariable Long idClasificacion) {


        // Busca la clasificación mediante el servicio.
        Optional<Clasificacion> clasificacion =
                clasificacionService.buscarClasificacionPorId(
                        idClasificacion
                );


        // Verifica si la clasificación existe.
        if (clasificacion.isPresent()) {

            // Retorna la clasificación encontrada.
            return ResponseEntity.ok(clasificacion.get());
        }


        // Retorna código HTTP 404 cuando no existe.
        return ResponseEntity.notFound().build();
    }


    // Registra una nueva clasificación.
    //
    // Endpoint:
    // POST /api/clasificacion
    @PostMapping
    public Clasificacion guardarClasificacion(
            @RequestBody Clasificacion clasificacion) {


        // Envía la información al servicio para guardarla
        // en la base de datos.
        return clasificacionService.guardarClasificacion(
                clasificacion
        );
    }


    // Actualiza una clasificación existente.
    //
    // Endpoint:
    // PUT /api/clasificacion/{idClasificacion}
    @PutMapping("/{idClasificacion}")
    public ResponseEntity<Clasificacion> actualizarClasificacion(
            @PathVariable Long idClasificacion,
            @RequestBody Clasificacion clasificacion) {


        // Busca primero la clasificación existente.
        Optional<Clasificacion> clasificacionExistente =
                clasificacionService.buscarClasificacionPorId(
                        idClasificacion
                );


        // Verifica que la clasificación exista antes
        // de realizar la actualización.
        if (clasificacionExistente.isEmpty()) {

            return ResponseEntity.notFound().build();
        }


        // Asigna el identificador de la clasificación
        // que se desea actualizar.
        clasificacion.setIdClasificacion(idClasificacion);


        // Guarda los cambios realizados.
        Clasificacion clasificacionActualizada =
                clasificacionService.guardarClasificacion(
                        clasificacion
                );


        // Retorna la clasificación actualizada.
        return ResponseEntity.ok(clasificacionActualizada);
    }


    // Elimina una clasificación utilizando su identificador.
    //
    // Endpoint:
    // DELETE /api/clasificacion/{idClasificacion}
    @DeleteMapping("/{idClasificacion}")
    public ResponseEntity<Void> eliminarClasificacion(
            @PathVariable Long idClasificacion) {


        // Solicita al servicio eliminar la clasificación.
        boolean eliminado =
                clasificacionService.eliminarClasificacion(
                        idClasificacion
                );


        // Verifica si la eliminación fue realizada correctamente.
        if (eliminado) {

            // Código 204 indica que la operación fue exitosa
            // y no se devuelve contenido.
            return ResponseEntity.noContent().build();
        }


        // Código 404 indica que la clasificación no existe.
        return ResponseEntity.notFound().build();
    }
}