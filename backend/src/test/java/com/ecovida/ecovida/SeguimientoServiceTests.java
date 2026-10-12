package com.ecovida.ecovida;

// Importamos el modelo de Clasificación
import com.ecovida.ecovida.model.Clasificacion;

// Importamos el modelo de Seguimiento del residuo
import com.ecovida.ecovida.model.SeguimientoResiduo;

// Importamos el repositorio de Clasificación
import com.ecovida.ecovida.repository.ClasificacionRepository;

// Importamos el repositorio de Seguimiento
import com.ecovida.ecovida.repository.SeguimientoResiduoRepository;

import java.util.Optional;

// Importamos el modelo de Usuario
import com.ecovida.ecovida.model.Usuario;

// Importamos el repositorio de Usuario
import com.ecovida.ecovida.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;

// Importamos las anotaciones de Mockito para crear objetos simulados
import org.mockito.InjectMocks;
import org.mockito.Mock;

// Importamos las clases necesarias para simular la autenticación
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

// Importamos el servicio que se va a probar
import com.ecovida.ecovida.service.SeguimientoResiduoServiceImpl;

// Importamos métodos para realizar las comprobaciones de las pruebas
import static org.junit.jupiter.api.Assertions.*;

// Importamos métodos de Mockito para crear y verificar simulaciones
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.quality.Strictness;

// Importamos LocalDate para trabajar con fechas
import java.time.LocalDate;


// Indicamos que la clase utilizará Mockito para realizar las pruebas
@ExtendWith(MockitoExtension.class)

// Permitimos que Mockito sea menos estricto con las simulaciones que no se utilizan
@MockitoSettings(strictness = Strictness.LENIENT)
public class SeguimientoServiceTests {

    // Simulamos el repositorio de seguimiento para no utilizar la base de datos real
    @Mock
    private SeguimientoResiduoRepository seguimientoResiduoRepository;

    // Simulamos el repositorio de usuarios
    @Mock
    private UsuarioRepository usuarioRepository;

    // Simulamos el repositorio de clasificaciones
    @Mock
    private ClasificacionRepository clasificacionRepository;

    // Inyectamos los objetos simulados en el servicio real que se va a probar
    @InjectMocks
    private SeguimientoResiduoServiceImpl seguimientoResiduoService;


    // Prueba el método encargado de guardar un seguimiento
    @Test
    void testGuardarSeguimiento() {

        // Simulamos la autenticación del usuario que está realizando la operación
        Authentication authentication = mock(Authentication.class);

        // Indicamos el correo del usuario que se encuentra autenticado
        when(authentication.getName()).thenReturn("marly56@gmail.com");

        // Creamos un contexto de seguridad simulado
        SecurityContext securityContext = mock(SecurityContext.class);

        // Indicamos que el contexto devolverá la autenticación simulada
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Establecemos el contexto de seguridad simulado
        SecurityContextHolder.setContext(securityContext);


        // Creamos un usuario simulado para la prueba
        Usuario usuarioMock = new Usuario();

        // Asignamos un ID al usuario simulado
        usuarioMock.setIdUsuario(1L);

        // Asignamos el correo utilizado en la autenticación
        usuarioMock.setCorreo("marly56@gmail.com");

        // Simulamos la búsqueda del usuario por correo
        when(usuarioRepository.findByCorreo("marly56@gmail.com")).thenReturn(usuarioMock);


        // 1. Arrange (Preparar los datos de prueba)

        // Creamos el objeto de seguimiento que será utilizado en la prueba
        SeguimientoResiduo seguimientoAProbar = new SeguimientoResiduo();

        // Establecemos la fecha del seguimiento
        seguimientoAProbar.setFechaSeguimiento(LocalDate.of(2026, 8, 31));

        // Establecemos el tipo de proceso realizado al residuo
        seguimientoAProbar.setTipoProceso("Reutilizacion");

        // Establecemos el estado actual del seguimiento
        seguimientoAProbar.setEstado("Pendiente");

        // Establecemos el resultado del proceso
        seguimientoAProbar.setResultado("El material esta en reutilización");


        // Creamos una clasificación simulada
        Clasificacion clasificacionMock = new Clasificacion();

        // Asignamos un ID a la clasificación
        clasificacionMock.setIdClasificacion(1L);


        // Asignamos la clasificación al seguimiento
        seguimientoAProbar.setClasificacion(clasificacionMock);

        // Simulamos que la clasificación con ID 1 existe
        when(clasificacionRepository.findById(1L))
                .thenReturn(Optional.of(clasificacionMock));

        // Simulamos el guardado del seguimiento.
        // Cuando se intente guardar cualquier objeto SeguimientoResiduo,
        // se devolverá el seguimiento preparado anteriormente
        when(seguimientoResiduoRepository.save(any(SeguimientoResiduo.class)))
                .thenReturn(seguimientoAProbar);


        // 2. Act (Ejecutar el método que se está probando)

        // Ejecutamos el método guardarSeguimiento del servicio
        SeguimientoResiduo resultado =
                seguimientoResiduoService.guardarSeguimiento(seguimientoAProbar);


        // 3. Assert (Verificar los resultados)

        // Verificamos que el resultado no sea nulo
        assertNotNull(resultado);

        // Verificamos que la fecha guardada sea la esperada
        assertEquals(
                LocalDate.of(2026, 8, 31),
                resultado.getFechaSeguimiento()
        );

        // Verificamos que el tipo de proceso sea el esperado
        assertEquals(
                "Reutilizacion",
                resultado.getTipoProceso()
        );


        // Verificamos que el método save del repositorio
        // haya sido ejecutado exactamente una vez
        verify(
                seguimientoResiduoRepository,
                times(1)
        ).save(any(SeguimientoResiduo.class));
    }
}