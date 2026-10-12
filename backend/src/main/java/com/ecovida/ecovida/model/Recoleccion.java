package com.ecovida.ecovida.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;


// Indica que esta clase representa una entidad de la base de datos.
@Entity

// Especifica el nombre de la tabla asociada a esta entidad.
@Table(name = "Recoleccion")
public class Recoleccion {


    // Identificador único de la recolección.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecoleccion;


    // Fecha en la que se realizó la recolección.
    @Column(name = "fecha")
    private LocalDate fecha;


    // Hora en la que se realizó la recolección.
    @Column(name = "hora")
    private LocalTime hora;


    // Tipo de residuo recolectado.
    //
    // EnumType.STRING permite almacenar en la base de datos
    // el nombre del tipo de residuo en forma de texto.
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_residuo")
    private TipoResiduo tipoResiduo;


    // Ubicación donde se realizó la recolección.
    @Column(name = "ubicacion")
    private String ubicacion;


    // Cantidad de residuo recolectado expresada en kilogramos.
    //
    // El valor debe ser mayor que cero.
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(name = "cantidad_kg")
    private Double cantidadkg;


    // Descripción adicional relacionada con la recolección.
    @Column(name = "descripcion")
    private String descripcion;


    // Identificador del usuario asociado a la recolección.
    @Column(name = "id_usuario")
    private Long idUsuario;


    // Constructor vacío requerido por JPA.
    public Recoleccion() {
    }


    // Retorna el identificador de la recolección.
    public Long getIdRecoleccion() {
        return idRecoleccion;
    }


    // Asigna el identificador de la recolección.
    public void setIdRecoleccion(Long idRecoleccion) {
        this.idRecoleccion = idRecoleccion;
    }


    // Retorna la fecha en la que se realizó la recolección.
    public LocalDate getFecha() {
        return fecha;
    }


    // Asigna la fecha de la recolección.
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


    // Retorna la hora en la que se realizó la recolección.
    public LocalTime getHora() {
        return hora;
    }


    // Asigna la hora de la recolección.
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }


    // Retorna la ubicación donde se realizó la recolección.
    public String getUbicacion() {
        return ubicacion;
    }


    // Asigna la ubicación de la recolección.
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    // Retorna el tipo de residuo recolectado.
    public TipoResiduo getTipoResiduo() {
        return tipoResiduo;
    }


    // Asigna el tipo de residuo recolectado.
    public void setTipoResiduo(TipoResiduo tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }


    // Retorna la cantidad de residuo recolectada en kilogramos.
    public Double getCantidadKg() {
        return cantidadkg;
    }


    // Asigna la cantidad de residuo recolectada en kilogramos.
    public void setCantidadKg(Double cantidaKg) {
        this.cantidadkg = cantidaKg;
    }


    // Retorna la descripción de la recolección.
    public String getDescripcion() {
        return descripcion;
    }


    // Asigna la descripción de la recolección.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    // Retorna el identificador del usuario asociado.
    public Long getIdUsuario() {
        return idUsuario;
    }


    // Asigna el identificador del usuario asociado.
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}