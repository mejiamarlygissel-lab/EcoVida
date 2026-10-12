package com.ecovida.ecovida.exception;


// Excepción personalizada utilizada cuando un recurso
// solicitado no existe dentro de la aplicación.
//
// Esta excepción hereda de RuntimeException,
// por lo que puede ser utilizada para informar
// errores relacionados con recursos no encontrados.
public class RecursoNoEncontradoException extends RuntimeException {


    // Constructor que recibe el mensaje descriptivo
    // que será mostrado cuando se produzca la excepción.
    public RecursoNoEncontradoException(String mensaje) {

        // Envía el mensaje a la clase RuntimeException.
        super(mensaje);
    }
}