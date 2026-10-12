package com.ecovida.ecovida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecovida.ecovida.model.Usuario;


// Indica que esta interfaz es un repositorio de Spring.
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    // Busca un usuario utilizando su correo electrónico.
    Usuario findByCorreo(String correo);


    // Busca un usuario utilizando su contraseña.
    Usuario findByPassword(String password);

    boolean existsByCorreo(String correo);

}