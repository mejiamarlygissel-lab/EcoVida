package com.ecovida.ecovida;

// Importamos los modelos que se utilizan en la prueba.
import com.ecovida.ecovida.model.Clasificacion;
import com.ecovida.ecovida.model.Recoleccion;
import com.ecovida.ecovida.model.Usuario;

// Importamos los repositorios que serán simulados durante la prueba.
import com.ecovida.ecovida.repository.ClasificacionRepository;
import com.ecovida.ecovida.repository.RecoleccionRepository;
import com.ecovida.ecovida.repository.UsuarioRepository;

import java.util.Optional;

// Importamos las herramientas necesarias para realizar las pruebas.
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

// Importamos las clases necesarias para simular la autenticación.
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

// Configuración de Mockito para las pruebas.
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

// Servicio que se va a probar.
import com.ecovida.ecovida.service.ClasificacionServiceImpl;

// Métodos utilizados para verificar los resultados de las pruebas.
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Importamos LocalDate para manejar la fecha de clasificación.
import java.time.LocalDate;


// Indica que la clase utilizará Mockito para realizar las pruebas.
@ExtendWith(MockitoExtension.class)

// Permite utilizar una configuración flexible para los objetos simulados.
@MockitoSettings(strictness = Strictness.LENIENT)
public class ClasificacionServiceTests {

    // Simulamos el repositorio de clasificaciones.
    @Mock
    private ClasificacionRepository clasificacionRepository;

    // Simulamos el repositorio de usuarios.
    @Mock
    private UsuarioRepository usuarioRepository;

    // Simulamos el repositorio de recolecciones.
    @Mock
    private RecoleccionRepository recoleccionRepository;

    // Inyectamos los repositorios simulados en el servicio que se va a probar.
    @InjectMocks
    private ClasificacionServiceImpl clasificacionService;


    // Prueba el funcionamiento del método guardarClasificacion().
    @Test
    void testGuardarClasificación() {

        // Simulamos el usuario que está autenticado.
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
        when(usuarioRepository.findByCorreo("marly56@gmail.com")).thenReturn(usuarioMock);


        // 1. Arrange: Preparamos los datos que se utilizarán en la prueba.
        Clasificacion clasificacionAProbar = new Clasificacion();

        // Establecemos la fecha de la clasificación.
        clasificacionAProbar.setFecha(LocalDate.of(2026, 8, 31));

        // Establecemos la categoría del residuo.
        clasificacionAProbar.setCategoria("No_Aprovechable");

        // Establecemos la descripción de la clasificación.
        clasificacionAProbar
                .setDescripcion("Material plástico clasificado y separado para su posterior aprovechamiento.");


        // Creamos una recolección simulada.
        Recoleccion recoleccionMock = new Recoleccion();

        // Asignamos un ID a la recolección.
        recoleccionMock.setIdRecoleccion(1L);


        // Asociamos la recolección con la clasificación.
        clasificacionAProbar.setRecoleccion(recoleccionMock);


        // Simulamos que la recolección con ID 1 existe en la base de datos.
        when(recoleccionRepository.findById(1L))
                .thenReturn(Optional.of(recoleccionMock));

        // Simulamos el guardado de la clasificación.
        when(clasificacionRepository.save(any(Clasificacion.class)))
                .thenReturn(clasificacionAProbar);


        // 2. Act: Ejecutamos el método del servicio que queremos probar.
        Clasificacion resultado =
                clasificacionService.guardarClasificacion(clasificacionAProbar);


        // 3. Assert: Verificamos que el resultado sea correcto.

        // Verificamos que se haya obtenido un resultado.
        assertNotNull(resultado);

        // Verificamos que la fecha sea la esperada.
        assertEquals(
                LocalDate.of(2026, 8, 31),
                resultado.getFecha()
        );

        // Verificamos que la categoría sea la esperada.
        assertEquals(
                "No_Aprovechable",
                resultado.getCategoria()
        );

        // Verificamos que el método save del repositorio
        // haya sido llamado exactamente una vez.
        verify(
                clasificacionRepository,
                times(1)
        ).save(any(Clasificacion.class));
    }
}