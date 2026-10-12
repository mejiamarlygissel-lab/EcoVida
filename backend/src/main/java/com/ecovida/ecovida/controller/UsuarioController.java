
package com.ecovida.ecovida.controller;

// Importa las clases necesarias para trabajar con listas y datos opcionales.
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Importa las clases utilizadas para manejar códigos de respuesta HTTP.
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Permite obtener la información del usuario autenticado.
import org.springframework.security.core.context.SecurityContextHolder;

// Importa las anotaciones utilizadas para crear los endpoints REST.
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Permite obtener la información de autenticación y roles.
import org.springframework.security.core.Authentication;

// Importa el DTO utilizado para enviar información segura del usuario.
import com.ecovida.ecovida.dto.UsuarioResponse;

// Importa la entidad Usuario.
import com.ecovida.ecovida.model.Usuario;

// Importa el servicio encargado de la lógica de usuarios.
import com.ecovida.ecovida.service.UsuarioService;

import com.ecovida.ecovida.repository.UsuarioRepository;

// Permite realizar validaciones sobre los datos recibidos.
import jakarta.validation.Valid;

// Permite que React pueda consumir este controlador desde el puerto 5173.
@CrossOrigin(origins = "http://localhost:5173")

// Indica que esta clase es un controlador REST.
@RestController

// Define la ruta principal de los endpoints de usuarios.
@RequestMapping("/api/usuarios")
public class UsuarioController {

        private final UsuarioRepository usuarioRepository;
        // Servicio encargado de realizar las operaciones de usuarios.
        private final UsuarioService usuarioService;

        

        // Constructor utilizado para inyectar UsuarioService.
        public UsuarioController(UsuarioService usuarioService, 
                UsuarioRepository usuarioRepository) {

                this.usuarioService = usuarioService;
                this.usuarioRepository = usuarioRepository;

        }

        // Lista todos los usuarios registrados.
        @GetMapping
        public List<UsuarioResponse> listarUsuarios() {

                // Consulta los usuarios mediante el servicio.
                List<Usuario> usuarios = usuarioService.listarUsuarios();

                // Lista donde se almacenará la información que se enviará.
                List<UsuarioResponse> respuesta = new ArrayList<>();

                // Recorre todos los usuarios encontrados.
                for (Usuario usuario : usuarios) {

                        // Convierte la entidad Usuario en un UsuarioResponse.
                        UsuarioResponse dto = new UsuarioResponse(

                                        usuario.getIdUsuario(),
                                        usuario.getNombre(),
                                        usuario.getApellido(),
                                        usuario.getCorreo(),
                                        usuario.getTelefono(),
                                        usuario.getRol()

                        );

                        // Agrega el usuario convertido a la respuesta.
                        respuesta.add(dto);

                }

                // Devuelve la lista de usuarios al frontend.
                return respuesta;
        }

        // Busca un usuario utilizando su identificador.
        @GetMapping("/{idUsuario}")
        public ResponseEntity<Usuario> buscarUsuarioPorId(
                        @PathVariable Long idUsuario) {

                // Busca el usuario mediante su ID.
                Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(idUsuario);

                // Comprueba si el usuario fue encontrado.
                if (usuario.isPresent()) {

                        // Devuelve el usuario con código HTTP 200.
                        return ResponseEntity.ok(usuario.get());

                }

                // Devuelve HTTP 404 cuando el usuario no existe.
                return ResponseEntity.notFound().build();
        }

        // Busca un usuario utilizando su correo electrónico.
        @GetMapping("/correo/{correo}")
        public ResponseEntity<UsuarioResponse> buscarUsuarioPorCorreo(
                        @PathVariable String correo) {

                // Busca el usuario mediante su correo.
                Optional<Usuario> usuario = usuarioService.buscarUsuarioPorCorreo(correo);

                // Comprueba si el usuario existe.
                if (usuario.isEmpty()) {

                        // Devuelve HTTP 404 si no se encuentra.
                        return ResponseEntity.notFound().build();

                }

                // Obtiene el usuario encontrado.
                Usuario u = usuario.get();

                // Convierte el usuario en un DTO para proteger información sensible.
                UsuarioResponse dto = new UsuarioResponse(

                                u.getIdUsuario(),
                                u.getNombre(),
                                u.getApellido(),
                                u.getCorreo(),
                                u.getTelefono(),
                                u.getRol()

                );

                // Devuelve la información del usuario.
                return ResponseEntity.ok(dto);
        }

        // Registra un nuevo usuario.
        @PostMapping("/registro")
        public ResponseEntity<Usuario> registrarUsuario(
                        @RequestBody Usuario usuario) {

                // Envía el usuario al servicio para realizar el registro.
                Usuario nuevoUsuario = usuarioService.registarUsuario(usuario);

                // Devuelve el usuario creado con HTTP 201.
                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(nuevoUsuario);
        }

        // Actualiza la información de un usuario.
        @PutMapping("/{idUsuario}")
        public ResponseEntity<Usuario> actualizarUsuario(

                        @PathVariable Long idUsuario,

                        @Valid @RequestBody Usuario usuarioRecibido) {

                // Obtiene la información del usuario autenticado.
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                // Obtiene el correo del usuario autenticado.
                String correoActual = authentication.getName();

                // Busca en la base de datos al usuario autenticado.
                Optional<Usuario> usuarioActualOpt = usuarioService.buscarUsuarioPorCorreo(correoActual);

                // Verifica que el usuario autenticado exista.
                if (usuarioActualOpt.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                // Obtiene el usuario autenticado.
                Usuario usuarioActual = usuarioActualOpt.get();

                // Obtiene el rol del usuario autenticado.
                String rolActual = authentication.getAuthorities()
                                .iterator()
                                .next()
                                .getAuthority();

                // El JEFE solamente puede actualizar su propio usuario.
                if (rolActual.equals("ROLE_JEFE") &&
                                !usuarioActual.getIdUsuario().equals(idUsuario)) {

                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

                }

                // Busca el usuario que se desea actualizar.
                Optional<Usuario> usuarioExistenteOpt = usuarioService.buscarUsuarioPorId(idUsuario);

                // Verifica que el usuario exista.
                if (usuarioExistenteOpt.isEmpty()) {

                        // Devuelve HTTP 404 si no existe.
                        return ResponseEntity.notFound().build();

                }

                // Obtiene el usuario existente.
                Usuario usuarioExistente = usuarioExistenteOpt.get();

                // Actualiza el nombre del usuario.
                usuarioExistente.setNombre(
                                usuarioRecibido.getNombre());

                // Actualiza el apellido del usuario.
                usuarioExistente.setApellido(
                                usuarioRecibido.getApellido());

                // Actualiza el teléfono del usuario.
                usuarioExistente.setTelefono(
                                usuarioRecibido.getTelefono());

                // Guarda los cambios realizados en el usuario.
                Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
                // Devuelve el usuario actualizado con HTTP 200.
                return ResponseEntity.ok(usuarioActualizado);
        }

        // Elimina un usuario mediante su identificador.
        @DeleteMapping("/{idUsuario}")
        public ResponseEntity<?> eliminarUsuario(
                        @PathVariable Long idUsuario) {

                // Obtiene la información del usuario autenticado.
                Authentication authentication = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                // Obtiene el rol del usuario autenticado.
                String rol = authentication.getAuthorities()
                                .iterator()
                                .next()
                                .getAuthority();

                // Muestra en consola el rol que intenta eliminar.
                System.out.println("Rol que intenta eliminar: " + rol);

                // El JEFE no tiene permiso para eliminar usuarios.
                if (rol.equals("ROLE_JEFE")) {
                        return ResponseEntity
                                        .status(HttpStatus.FORBIDDEN)
                                        .build();
                }

                // El OPERARIO tampoco tiene permiso para eliminar usuarios.
                if (rol.equals("ROLE_OPERARIO")) {
                        return ResponseEntity
                                        .status(HttpStatus.FORBIDDEN)
                                        .build();
                }

                try {

                        // Si el usuario es ADMINISTRADOR, realiza la eliminación.
                        boolean eliminado = usuarioService.eliminarUsuario(idUsuario);

                        // Comprueba si el usuario fue eliminado correctamente.
                        if (eliminado) {
                                return ResponseEntity.noContent().build();
                        }

                } catch (IllegalStateException e) {

                        return ResponseEntity
                                        .status(HttpStatus.CONFLICT)
                                        .body(e.getMessage());

                }

                // Devuelve HTTP 404 si el usuario no existe.
                return ResponseEntity.notFound().build();
        }

}
