package com.ecovida.ecovida.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


// Indica que esta clase representa una entidad de la base de datos.
@Entity

// Especifica el nombre de la tabla asociada a esta entidad.
@Table(name = "Clasificacion")
public class Clasificacion {


    // Identificador único de la clasificación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClasificacion;


    // Fecha en la que se realizó la clasificación.
    @Column(name = "fecha_clasificacion")
    private LocalDate fecha_clasificacion;


    // Categoría asignada al residuo durante la clasificación.
    @Column(name = "categoria")
    private String categoria;


    // Descripción adicional de la clasificación realizada.
    @Column(name = "descripcion")
    private String descripcion;


    // Recolección asociada a esta clasificación.
    //
    // Una recolección puede estar relacionada con una
    // clasificación mediante la relación muchos a uno.
    @ManyToOne
    @JoinColumn(name = "id_recoleccion")
    private Recoleccion recoleccion;


    // Constructor vacío requerido por JPA.
    public Clasificacion() {
    }


    // Retorna el identificador de la clasificación.
    public Long getIdClasificacion() {
        return idClasificacion;
    }


    // Asigna el identificador de la clasificación.
    public void setIdClasificacion(Long idClasificacion) {
        this.idClasificacion = idClasificacion;
    }


    // Retorna la fecha en la que se realizó la clasificación.
    public LocalDate getFecha() {
        return fecha_clasificacion;
    }


    // Asigna la fecha de la clasificación.
    public void setFecha(LocalDate fecha) {
        this.fecha_clasificacion = fecha;
    }


    // Retorna la categoría asignada al residuo.
    public String getCategoria() {
        return categoria;
    }


    // Asigna la categoría del residuo.
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    // Retorna la descripción de la clasificación.
    public String getDescripcion() {
        return descripcion;
    }


    // Asigna la descripción de la clasificación.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    // Retorna la recolección asociada a la clasificación.
    public Recoleccion getRecoleccion() {
        return recoleccion;
    }


    // Asigna la recolección asociada a la clasificación.
    public void setRecoleccion(Recoleccion recoleccion) {
        this.recoleccion = recoleccion;
    }
}