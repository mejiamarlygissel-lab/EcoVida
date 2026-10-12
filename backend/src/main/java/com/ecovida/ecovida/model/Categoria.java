package com.ecovida.ecovida.model;


// Enum que define las categorías disponibles
// para clasificar los residuos dentro del sistema.
public enum Categoria {


    // Residuo que puede ser aprovechado,
    // reutilizado o reciclado.
    Aprovechable,


    // Residuo que no puede ser aprovechado
    // mediante los procesos de reciclaje establecidos.
    No_Aprovechable,


    // Residuo de origen orgánico que puede
    // ser tratado mediante procesos orgánicos.
    Orgánico
}