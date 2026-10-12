package com.ecovida.ecovida.model;


// Enum que define los tipos de procesos que pueden
// aplicarse a los residuos dentro del sistema EcoVida.
public enum TipoProceso {


    // Proceso mediante el cual se identifica y
    // separa el residuo según sus características.
    Clasificacion,


    // Proceso mediante el cual los residuos son
    // transformados para obtener nuevos materiales
    // o productos.
    Reciclaje,


    // Proceso mediante el cual un material puede
    // volver a utilizarse sin necesidad de transformarlo.
    Reutilizacion,


    // Proceso utilizado principalmente para residuos
    // orgánicos que pueden convertirse en compost.
    Compostaje,


    // Proceso utilizado para gestionar residuos
    // que no pueden ser aprovechados mediante
    // reciclaje, reutilización o compostaje.
    Disposiciónfinal
}