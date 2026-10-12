package com.ecovida.ecovida.model;


// Enum que define los estados que puede tener
// un residuo durante el proceso de seguimiento.
public enum Estados {


    // Indica que el proceso todavía no ha comenzado.
    Pendiente,


    // Indica que el residuo se encuentra
    // actualmente en proceso.
    Enproceso,


    // Indica que el proceso del residuo
    // ha terminado correctamente.
    Finalizado
}