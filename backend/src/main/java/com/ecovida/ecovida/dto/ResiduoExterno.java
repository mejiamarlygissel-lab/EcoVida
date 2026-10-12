package com.ecovida.ecovida.dto;


// DTO utilizado para representar la información
// de un residuo obtenido o enviado mediante
// un servicio externo.
public class ResiduoExterno {


    // Material del residuo.
    private String material;


    // Categoría a la que pertenece el residuo.
    private String categoria;


    // Descripción del residuo.
    private String descripcion;


    // Obtiene el material del residuo.
    public String getMaterial() {
        return material;
    }


    // Modifica el material del residuo.
    public void setMaterial(String material) {
        this.material = material;
    }


    // Obtiene la categoría del residuo.
    public String getCategoria() {
        return categoria;
    }


    // Modifica la categoría del residuo.
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    // Obtiene la descripción del residuo.
    public String getDescripcion() {
        return descripcion;
    }


    // Modifica la descripción del residuo.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}