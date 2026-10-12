package com.ecovida.ecovida.model;


// Enum que define los roles disponibles
// dentro del sistema EcoVida.
//
// Cada rol determina los permisos y el nivel
// de acceso que puede tener un usuario.
public enum Rol {


    // Rol con permisos administrativos.
    // Puede acceder y gestionar la información
    // general del sistema.
    ADMINISTRADOR,


    // Rol asignado al personal encargado
    // de realizar las operaciones del sistema.
    OPERARIO,


    // Rol con permisos de supervisión y gestión.
    // Puede consultar información general
    // y realizar funciones propias de supervisión.
    JEFE
}