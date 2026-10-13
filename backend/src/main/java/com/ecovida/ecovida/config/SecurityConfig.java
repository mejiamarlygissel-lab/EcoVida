package com.ecovida.ecovida.config;

import java.util.List;

// Importa la anotación para definir componentes Bean de Spring
import org.springframework.context.annotation.Bean;

// Indica que esta clase contiene configuraciones de Spring
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

// Permite configurar la seguridad de las solicitudes HTTP
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// Permite configurar la política de creación de sesiones
import org.springframework.security.config.http.SessionCreationPolicy;

// Representa la cadena de filtros de seguridad de Spring Security
import org.springframework.security.web.SecurityFilterChain;

// Permite agregar filtros personalizados dentro de la cadena de seguridad
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Importa el filtro personalizado encargado de validar los tokens JWT
import com.ecovida.ecovida.security.JwtFilter;

// Importa BCrypt para encriptar las contraseñas
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Interfaz utilizada para manejar el cifrado y validación de contraseñas
import org.springframework.security.crypto.password.PasswordEncoder;


// Indica que esta clase contiene configuraciones de Spring
@Configuration
public class SecurityConfig {

        // Filtro personalizado encargado de validar los tokens JWT
        private final JwtFilter jwtFilter;

        // Constructor que recibe e inyecta el filtro JWT
        public SecurityConfig(JwtFilter jwtFilter) {
                this.jwtFilter = jwtFilter;
        }

        // Define la configuración principal de seguridad de Spring Security
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http

                                // Activa la configuración CORS para permitir la comunicación
                                // entre el frontend React y el backend Spring Boot
                                .cors(cors -> {
                                })

                                // Deshabilita CSRF porque la aplicación utiliza
                                // autenticación mediante tokens JWT
                                .csrf(csrf -> csrf.disable())

                                // Configura la aplicación como Stateless,
                                // es decir, no guarda sesiones en el servidor
                                // Cada petición debe enviar su propio token JWT
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                // Define las reglas de acceso para los diferentes endpoints
                                .authorizeHttpRequests(auth -> auth

                                                // Permite acceder al login sin autenticación
                                                // porque aquí se genera el token JWT
                                                .requestMatchers("/api/auth/**").permitAll()

                                                // Permite el acceso a las APIs públicas
                                                // sin necesidad de autenticación
                                                .requestMatchers("/api/publica/**").permitAll()

                                                // Permite las solicitudes OPTIONS utilizadas
                                                // durante la validación CORS del navegador
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                                // Permite registrar nuevos usuarios sin token
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/usuarios/registro")
                                                .permitAll()

                                                // Permite actualizar usuarios autenticados
                                                // con cualquiera de los tres roles
                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/usuarios/**")
                                                .hasAnyRole(
                                                                "ADMINISTRADOR",
                                                                "JEFE",
                                                                "OPERARIO")

                                                // Permite eliminar usuarios autenticados
                                                // con cualquiera de los tres roles
                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/usuarios/**")
                                                .hasAnyRole(
                                                                "ADMINISTRADOR",
                                                                "OPERARIO",
                                                                "JEFE")

                                                // Permite consultar usuarios autenticados
                                                // con cualquiera de los tres roles
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/usuarios/**")
                                                .hasAnyRole(
                                                                "JEFE",
                                                                "ADMINISTRADOR",
                                                                "OPERARIO")

                                                // Define permisos generales para las rutas
                                                // relacionadas con usuarios
                                                .requestMatchers(
                                                                "/api/usuarios/**")
                                                .hasAnyRole(
                                                                "ADMINISTRADOR",
                                                                "OPERARIO")

                                                // Permite consultar recolecciones,
                                                // clasificaciones y seguimientos
                                                // a los tres roles
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/recoleccion/**",
                                                                "/api/clasificacion/**",
                                                                "/api/seguimiento/**")
                                                .hasAnyRole(
                                                                "JEFE",
                                                                "ADMINISTRADOR",
                                                                "OPERARIO")

                                                // Permite realizar operaciones sobre
                                                // recolecciones a Administradores y Operarios
                                                .requestMatchers(
                                                                "/api/recoleccion/**")
                                                .hasAnyRole(
                                                                "ADMINISTRADOR",
                                                                "OPERARIO")

                                                // Permite realizar operaciones sobre
                                                // clasificaciones a Administradores y Operarios
                                                .requestMatchers(
                                                                "/api/clasificacion/**")
                                                .hasAnyRole(
                                                                "ADMINISTRADOR",
                                                                "OPERARIO")

                                                // Permite realizar operaciones sobre
                                                // seguimientos a Administradores y Operarios
                                                .requestMatchers(
                                                                "/api/seguimiento/**")
                                                .hasAnyRole(
                                                                "ADMINISTRADOR",
                                                                "OPERARIO")

                                                // Todas las demás rutas necesitan
                                                // un usuario autenticado con un JWT válido
                                                .anyRequest().authenticated()

                                )

                                // Agrega el filtro JWT antes del filtro de autenticación
                                // para validar el token enviado en cada petición
                                .addFilterBefore(
                                                jwtFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                // Construye y devuelve la configuración de seguridad
                return http.build();
        }


        // Define el Bean encargado de manejar las contraseñas
        @Bean
        public PasswordEncoder passwordEncoder() {

                // Utiliza BCrypt para generar y validar contraseñas de forma segura
                return new BCryptPasswordEncoder();
        }


        // Configuración global de CORS para permitir
        // la comunicación entre frontend y backend
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                // Crea una configuración con las reglas CORS
                CorsConfiguration configuration = new CorsConfiguration();

                // Permite solicitudes provenientes del frontend React
                // que funciona en localhost:5173
                configuration.setAllowedOrigins(
                                List.of("http://localhost:5173","https://eco-vida-cyan.vercel.app"));


                // Define los métodos HTTP permitidos desde React
                // GET: consultar información
                // POST: registrar información
                // PUT: actualizar información
                // DELETE: eliminar información
                // OPTIONS: validar solicitudes CORS
                configuration.setAllowedMethods(
                                List.of(
                                                "GET",
                                                "POST",
                                                "PUT",
                                                "DELETE",
                                                "OPTIONS"
                                ));


                // Permite cualquier encabezado enviado por el frontend,
                // incluyendo Authorization, donde se envía el token JWT
                configuration.setAllowedHeaders(
                                List.of("*"));


                // Permite enviar información de autenticación
                // como cookies o credenciales si fueran necesarias
                configuration.setAllowCredentials(true);


                // Crea el origen de configuración CORS
                UrlBasedCorsConfigurationSource source =
                                new UrlBasedCorsConfigurationSource();


                // Aplica la configuración CORS a todos los endpoints
                source.registerCorsConfiguration(
                                "/**",
                                configuration);


                // Devuelve la configuración CORS creada
                return source;
        }
}