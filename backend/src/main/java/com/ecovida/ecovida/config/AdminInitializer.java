package com.ecovida.ecovida.config;

import com.ecovida.ecovida.model.Rol;
import com.ecovida.ecovida.model.Usuario;
import com.ecovida.ecovida.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${BOOTSTRAP_ADMINISTRADOR_NOMBRE:}")
    private String nombre;

    @Value("${BOOTSTRAP_ADMINISTRADOR_APELLIDO:}")
    private String apellido;

    @Value("${BOOTSTRAP_ADMINISTRADOR_EMAIL:}")
    private String correo;

    @Value("${BOOTSTRAP_ADMINISTRADOR_TELEFONO:}")
    private String telefono;

    @Value("${BOOTSTRAP_ADMINISTRADOR_PASSWORD:}")
    private String password;

    @Value("${BOOTSTRAP_ADMINISTRADOR_ROL:}")
    private String rol;

    public AdminInitializer(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (nombre.isBlank()
                || apellido.isBlank()
                || correo.isBlank()
                || password.isBlank()
                || telefono.isBlank()
                || rol.isBlank()

        ) {

            System.out.println(
                    "Administrador inicial no configurado.");
            return;
        }

        if (usuarioRepository.existsByCorreo(correo)) {
            System.out.println(
                    "El administrador inicial ya existe.");
            return;
        }

        Usuario administrador = new Usuario(
                nombre,
                apellido,
                correo,
                telefono,
                passwordEncoder.encode(password),
                Rol.ADMINISTRADOR);

        usuarioRepository.save(administrador);

        System.out.println(
                "Administrador inicial creado correctamente.");
    }
}
