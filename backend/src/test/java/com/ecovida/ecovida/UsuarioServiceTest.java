package com.ecovida.ecovida;

// Importamos el modelo de Usuario
import com.ecovida.ecovida.model.Usuario;

// Importamos el repositorio de usuarios
import com.ecovida.ecovida.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// Importamos las herramientas de Mockito para realizar las pruebas
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Importamos el servicio que se va a probar
import com.ecovida.ecovida.service.UsuarioServiceImpl;

// Importamos métodos para realizar las comprobaciones de la prueba
import static org.junit.jupiter.api.Assertions.*;

// Importamos herramientas de Mockito para simular comportamientos
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


// Indicamos que la clase utilizará Mockito para realizar las pruebas
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    // Simulamos el repositorio de usuarios
    // No se utiliza la base de datos real durante esta prueba
    @Mock
    private UsuarioRepository usuarioRepository;

    // Inyectamos el repositorio simulado dentro del servicio real
    // que se va a probar
    @InjectMocks
    private UsuarioServiceImpl usuarioService;


    // Prueba el registro exitoso de un usuario
    @Test
    void testRegistrarUsuarioExitoso() {

        // 1. Arrange (Preparar los datos de prueba)

        // Creamos un usuario para utilizarlo en la prueba
        Usuario usuarioAProbar = new Usuario();

        // Establecemos el nombre del usuario
        usuarioAProbar.setNombre("Marly");

        // Establecemos el apellido del usuario
        usuarioAProbar.setApellido("Mejía");

        // Establecemos el número de teléfono
        usuarioAProbar.setTelefono("3001234567");

        // Establecemos el correo electrónico
        usuarioAProbar.setCorreo("marly@ecovida.com");

        // Establecemos la contraseña que se utilizará en la prueba
        usuarioAProbar.setPassword("123456");


        // Simulamos el comportamiento del repositorio.
        // Cuando se intente guardar cualquier objeto Usuario,
        // el repositorio devolverá el usuario preparado anteriormente.
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioAProbar);


        // 2. Act (Ejecutar el método del servicio)

        // Ejecutamos el método de registro del servicio
        Usuario resultado = usuarioService.registarUsuario(usuarioAProbar);


        // 3. Assert (Verificar los resultados)

        // Verificamos que el resultado obtenido no sea nulo
        assertNotNull(resultado);

        // Verificamos que el nombre del usuario sea el esperado
        assertEquals("Marly", resultado.getNombre());

        // Verificamos que el correo del usuario sea el esperado
        assertEquals("marly@ecovida.com", resultado.getCorreo());


        // Verificamos que el método save del repositorio
        // haya sido ejecutado exactamente una vez
        verify(usuarioRepository, times(1))
                .save(any(Usuario.class));
    }
}