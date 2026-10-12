package com.ecovida.ecovida;

// Importamos el modelo de Recolección.
import com.ecovida.ecovida.model.Recoleccion;

// Importamos el tipo de residuo utilizado en la prueba.
import com.ecovida.ecovida.model.TipoResiduo;

// Importamos el repositorio de recolecciones.
import com.ecovida.ecovida.repository.RecoleccionRepository;

// Importamos el modelo de Usuario.
import com.ecovida.ecovida.model.Usuario;

// Importamos el repositorio de usuarios.
import com.ecovida.ecovida.repository.UsuarioRepository;

// Importamos las herramientas necesarias para realizar las pruebas.
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Importamos las clases necesarias para simular la autenticación.
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

// Importamos el servicio que se va a probar.
import com.ecovida.ecovida.service.RecoleccionServiceImpl;

// Importamos métodos para verificar los resultados de la prueba.
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Importamos las clases para manejar fecha y hora.
import java.time.LocalDate;
import java.time.LocalTime;


// Indica que la prueba utilizará Mockito.
@ExtendWith(MockitoExtension.class)
public class RecoleccionServiceTests {

    // Simulamos el repositorio de recolecciones.
    @Mock
    private RecoleccionRepository recoleccionRepository;

    // Simulamos el repositorio de usuarios.
    @Mock
    private UsuarioRepository usuarioRepository;

    // Inyectamos los repositorios simulados en el servicio real que se va a probar.
    @InjectMocks
    private RecoleccionServiceImpl recoleccionService;


    // Prueba el funcionamiento del método guardarRecoleccion().
    @Test
    void testGuardarRecolecion() {

        // Simulamos el usuario que se encuentra autenticado.
        Authentication authentication = mock(Authentication.class);

        // Definimos el correo del usuario autenticado.
        when(authentication.getName()).thenReturn("marly56@gmail.com");

        // Simulamos el contexto de seguridad.
        SecurityContext securityContext = mock(SecurityContext.class);

        // Indicamos que el contexto de seguridad contiene el usuario simulado.
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Establecemos el contexto de seguridad simulado para la prueba.
        SecurityContextHolder.setContext(securityContext);


        // Creamos un usuario simulado para la prueba.
        Usuario usuarioMock = new Usuario();

        // Asignamos un ID al usuario simulado.
        usuarioMock.setIdUsuario(1L);

        // Asignamos el correo del usuario simulado.
        usuarioMock.setCorreo("marly56@gmail.com");

        // Simulamos la búsqueda del usuario por correo.
        when(usuarioRepository.findByCorreo("marly56@gmail.com"))
                .thenReturn(usuarioMock);


        // 1. Arrange: Preparamos los datos de prueba.
        Recoleccion recoleccionAProbar = new Recoleccion();

        // Establecemos la fecha de la recolección.
        recoleccionAProbar.setFecha(
                LocalDate.of(2026, 8, 31)
        );

        // Establecemos la hora de la recolección.
        recoleccionAProbar.setHora(
                LocalTime.of(10, 30)
        );

        // Asignamos el ID del usuario relacionado con la recolección.
        recoleccionAProbar.setIdUsuario(1L);

        // Establecemos la cantidad de residuos recolectados en kilogramos.
        recoleccionAProbar.setCantidadKg(10.0);

        // Establecemos el tipo de residuo.
        recoleccionAProbar.setTipoResiduo(TipoResiduo.Papel);

        // Establecemos la ubicación de la recolección.
        recoleccionAProbar.setUbicacion("Yopal, Casanare");


        // Simulamos el guardado de la recolección.
        // Cuando el repositorio reciba una recolección,
        // devolverá la misma recolección utilizada en la prueba.
        when(recoleccionRepository.save(any(Recoleccion.class)))
                .thenReturn(recoleccionAProbar);


        // 2. Act: Ejecutamos el método del servicio que queremos probar.
        Recoleccion resultado =
                recoleccionService.guardarRecoleccion(recoleccionAProbar);


        // 3. Assert: Verificamos que el resultado sea correcto.

        // Verificamos que se haya obtenido un resultado.
        assertNotNull(resultado);

        // Verificamos que la fecha sea la esperada.
        assertEquals(
                LocalDate.of(2026, 8, 31),
                resultado.getFecha()
        );

        // Verificamos que la hora sea la esperada.
        assertEquals(
                LocalTime.of(10, 30),
                resultado.getHora()
        );


        // Verificamos que el método save del repositorio
        // haya sido llamado exactamente una vez.
        verify(
                recoleccionRepository,
                times(1)
        ).save(any(Recoleccion.class));
    }
}