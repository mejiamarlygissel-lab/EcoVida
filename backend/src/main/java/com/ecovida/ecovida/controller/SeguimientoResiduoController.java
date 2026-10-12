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

import com.ecovida.ecovida.model.SeguimientoResiduo;
import com.ecovida.ecovida.service.SeguimientoResiduoService;


// Permite que el frontend desarrollado en React pueda consumir
// los servicios de este controlador desde el puerto 5173.
@CrossOrigin(origins = "http://localhost:5173")


// Indica que esta clase es un controlador REST.
@RestController


// Define la ruta base para todos los servicios
// relacionados con el seguimiento de residuos.
@RequestMapping("/api/seguimiento")
public class SeguimientoResiduoController {


    // Servicio encargado de manejar la lógica de negocio
    // relacionada con el seguimiento de residuos.
    private final SeguimientoResiduoService seguimientoResiduoService;


    // Constructor utilizado para inyectar el servicio
    // de seguimiento de residuos.
    public SeguimientoResiduoController(
            SeguimientoResiduoService seguimientoResiduoService) {

        this.seguimientoResiduoService = seguimientoResiduoService;
    }


    // Obtiene la lista de todos los seguimientos registrados.
    //
    // Endpoint:
    // GET /api/seguimiento
    @GetMapping
    public List<SeguimientoResiduo> listarSeguimiento() {

        return seguimientoResiduoService.listarSeguimiento();
    }


    // Busca un seguimiento utilizando su identificador.
    //
    // Endpoint:
    // GET /api/seguimiento/{idSeguimientoResiduo}
    @GetMapping("/{idSeguimientoResiduo}")
    public ResponseEntity<SeguimientoResiduo> buscarSeguimientoPorId(
            @PathVariable Long idSeguimientoResiduo) {


        // Busca el seguimiento mediante el servicio.
        Optional<SeguimientoResiduo> seguimientoResiduo =
                seguimientoResiduoService
                        .buscarSeguimientoPorId(
                                idSeguimientoResiduo
                        );


        // Verifica si el seguimiento existe.
        if (seguimientoResiduo.isPresent()) {

            // Retorna el seguimiento encontrado.
            return ResponseEntity.ok(
                    seguimientoResiduo.get()
            );
        }


        // Retorna código HTTP 404 cuando no existe.
        return ResponseEntity.notFound().build();
    }


    // Registra un nuevo seguimiento de residuo.
    //
    // Endpoint:
    // POST /api/seguimiento
    @PostMapping
    public SeguimientoResiduo guardarSeguimiento(
            @RequestBody SeguimientoResiduo seguimientoResiduo) {


        // Envía la información al servicio para
        // almacenarla en la base de datos.
        return seguimientoResiduoService
                .guardarSeguimiento(seguimientoResiduo);
    }


    // Actualiza un seguimiento existente.
    //
    // Endpoint:
    // PUT /api/seguimiento/{idSeguimientoResiduo}
    @PutMapping("/{idSeguimientoResiduo}")
    public ResponseEntity<SeguimientoResiduo> actualizarSeguimiento(
            @PathVariable Long idSeguimientoResiduo,
            @RequestBody SeguimientoResiduo seguimientoResiduo) {


        // Busca primero el seguimiento existente.
        Optional<SeguimientoResiduo> seguimientoExistente =
                seguimientoResiduoService
                        .buscarSeguimientoPorId(
                                idSeguimientoResiduo
                        );


        // Verifica que el seguimiento exista antes
        // de realizar la actualización.
        if (seguimientoExistente.isEmpty()) {

            return ResponseEntity.notFound().build();
        }


        // Asigna el identificador del seguimiento
        // que se desea actualizar.
        seguimientoResiduo.setIdSeguimientoResiduo(
                idSeguimientoResiduo
        );


        // Guarda los cambios realizados.
        SeguimientoResiduo SeguimientoActualizado =
                seguimientoResiduoService
                        .guardarSeguimiento(
                                seguimientoResiduo
                        );


        // Retorna el seguimiento actualizado.
        return ResponseEntity.ok(
                SeguimientoActualizado
        );
    }


    // Elimina un seguimiento utilizando su identificador.
    //
    // Endpoint:
    // DELETE /api/seguimiento/{idSeguimientoResiduo}
    @DeleteMapping("/{idSeguimientoResiduo}")
    public ResponseEntity<Void> eliminarSeguimiento(
            @PathVariable Long idSeguimientoResiduo) {


        // Solicita al servicio eliminar el seguimiento.
        boolean eliminado =
                seguimientoResiduoService
                        .eliminarSeguimiento(
                                idSeguimientoResiduo
                        );


        // Verifica si la eliminación fue realizada correctamente.
        if (eliminado) {

            // Código 204 indica que la operación fue exitosa
            // y no se devuelve contenido.
            return ResponseEntity.noContent().build();
        }


        // Código 404 indica que el seguimiento no existe.
        return ResponseEntity.notFound().build();
    }
}