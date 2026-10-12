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

import com.ecovida.ecovida.model.Recoleccion;
import com.ecovida.ecovida.service.RecoleccionService;


// Permite que el frontend desarrollado en React pueda consumir
// los servicios de este controlador desde el puerto 5173.
@CrossOrigin(origins = "http://localhost:5173")


// Indica que esta clase es un controlador REST.
@RestController


// Define la ruta base para todos los servicios
// relacionados con las recolecciones.
@RequestMapping("/api/recoleccion")
public class RecoleccionController {


    // Servicio encargado de manejar la lógica de negocio
    // relacionada con las recolecciones.
    private final RecoleccionService recoleccionService;


    // Constructor que permite inyectar el servicio
    // de recolecciones mediante Spring.
    public RecoleccionController(RecoleccionService recoleccionService) {

        this.recoleccionService = recoleccionService;
    }


    // Lista todas las recolecciones registradas
    // en la base de datos.
    @GetMapping
    public List<Recoleccion> listarRecolecciones() {

        return recoleccionService.listarRecolecciones();
    }


    // Busca una recolección utilizando su identificador.
    //
    // Endpoint:
    // GET /api/recoleccion/{idRecoleccion}
    @GetMapping("/{idRecoleccion}")
    public ResponseEntity<Recoleccion> buscarRecoleccionPorId(
            @PathVariable Long idRecoleccion) {


        // Busca la recolección mediante el servicio.
        Optional<Recoleccion> recoleccion =
                recoleccionService.buscarRecoleccionPorId(idRecoleccion);


        // Verifica si la recolección existe.
        if (recoleccion.isPresent()) {

            // Retorna la recolección encontrada.
            return ResponseEntity.ok(recoleccion.get());
        }


        // Retorna código HTTP 404 cuando no existe.
        return ResponseEntity.notFound().build();
    }


    // Obtiene todas las recolecciones asociadas
    // a un usuario específico.
    //
    // Endpoint:
    // GET /api/recoleccion/usuario/{idUsuario}
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Recoleccion>> buscarPorIdUsuario(
            @PathVariable Long idUsuario) {


        // Consulta las recolecciones relacionadas
        // con el usuario indicado.
        List<Recoleccion> recolecciones =
                recoleccionService.buscarPorIdUsuario(idUsuario);


        // Retorna la lista de recolecciones encontrada.
        return ResponseEntity.ok(recolecciones);
    }


    // Registra una nueva recolección.
    //
    // Endpoint:
    // POST /api/recoleccion
    @PostMapping
    public Recoleccion guardarRecoleccion(
            @RequestBody Recoleccion recoleccion) {


        // Envía la información al servicio para
        // almacenarla en la base de datos.
        return recoleccionService.guardarRecoleccion(recoleccion);
    }


    // Actualiza una recolección existente.
    //
    // Endpoint:
    // PUT /api/recoleccion/{idRecoleccion}
    @PutMapping("/{idRecoleccion}")
    public ResponseEntity<Recoleccion> actualizarRecoleccion(
            @PathVariable Long idRecoleccion,
            @RequestBody Recoleccion recoleccion) {


        // Busca primero la recolección existente.
        Optional<Recoleccion> recoleccionExistente =
                recoleccionService.buscarRecoleccionPorId(idRecoleccion);


        // Verifica que la recolección exista antes
        // de realizar la actualización.
        if (recoleccionExistente.isEmpty()) {

            return ResponseEntity.notFound().build();
        }


        // Asigna el identificador de la recolección
        // que se desea actualizar.
        recoleccion.setIdRecoleccion(idRecoleccion);


        // Guarda los cambios realizados.
        Recoleccion recoleccionActualizado =
                recoleccionService.guardarRecoleccion(recoleccion);


        // Retorna la recolección actualizada.
        return ResponseEntity.ok(recoleccionActualizado);
    }


    // Elimina una recolección utilizando su identificador.
    //
    // Endpoint:
    // DELETE /api/recoleccion/{idRecoleccion}
    @DeleteMapping("/{idRecoleccion}")
    public ResponseEntity<Void> eliminarRecoleccion(
            @PathVariable Long idRecoleccion) {


        // Solicita al servicio eliminar la recolección.
        boolean eliminado =
                recoleccionService.eliminarRecoleccion(idRecoleccion);


        // Verifica si la eliminación fue realizada correctamente.
        if (eliminado) {

            // Código 204 indica que la operación fue exitosa
            // y no se devuelve contenido.
            return ResponseEntity.noContent().build();
        }


        // Código 404 indica que la recolección no existe.
        return ResponseEntity.notFound().build();
    }
}