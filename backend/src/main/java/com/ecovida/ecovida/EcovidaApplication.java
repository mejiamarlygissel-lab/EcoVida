package com.ecovida.ecovida;

// Importa la clase que permite iniciar una aplicación Spring Boot
import org.springframework.boot.SpringApplication;
// Indica que esta es la clase principal de la aplicación
// y habilita la configuración automática de Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marca esta clase como el punto de inicio de la aplicación
@SpringBootApplication
public class EcovidaApplication {

    // Método principal que inicia la ejecución de la aplicación
    public static void main(String[] args) {

        // Ejecuta la aplicación Spring Boot
        SpringApplication.run(EcovidaApplication.class, args);
    }

}