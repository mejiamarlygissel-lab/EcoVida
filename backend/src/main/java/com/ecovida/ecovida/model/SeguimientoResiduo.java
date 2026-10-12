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
@Table(name = "SeguimientoResiduo")
public class SeguimientoResiduo {


    // Identificador único del seguimiento del residuo.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento_residuo")
    private Long idSeguimientoResiduo;


    // Fecha en la que se realizó el seguimiento del residuo.
    @Column(name = "fecha_seguimiento")
    private LocalDate fechaSeguimiento;


    // Tipo de proceso realizado al residuo.
    @Column(name = "tipo_proceso")
    private String tipoProceso;


    // Resultado obtenido después del proceso realizado.
    @Column(name = "resultado")
    private String resultado;


    // Estado actual del residuo dentro del proceso
    // de seguimiento.
    @Column(name = "estado")
    private String estado;


    // Clasificación asociada al seguimiento del residuo.
    //
    // Un seguimiento pertenece a una clasificación.
    @ManyToOne
    @JoinColumn(name = "id_clasificacion")
    private Clasificacion clasificacion;


    // Constructor vacío requerido por JPA.
    public SeguimientoResiduo() {
    }


    // Retorna el identificador del seguimiento.
    public Long getIdSeguimientoResiduo() {
        return idSeguimientoResiduo;
    }


    // Asigna el identificador del seguimiento.
    public void setIdSeguimientoResiduo(
            Long idSeguimientoResiduo) {

        this.idSeguimientoResiduo = idSeguimientoResiduo;
    }


    // Retorna la fecha en la que se realizó el seguimiento.
    public LocalDate getFechaSeguimiento() {
        return fechaSeguimiento;
    }


    // Asigna la fecha del seguimiento.
    public void setFechaSeguimiento(
            LocalDate fechaSeguimiento) {

        this.fechaSeguimiento = fechaSeguimiento;
    }


    // Retorna el tipo de proceso realizado.
    public String getTipoProceso() {
        return tipoProceso;
    }


    // Asigna el tipo de proceso realizado.
    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }


    // Retorna el resultado obtenido durante el proceso.
    public String getResultado() {
        return resultado;
    }


    // Asigna el resultado obtenido durante el proceso.
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }


    // Retorna el estado actual del residuo.
    public String getEstado() {
        return estado;
    }


    // Asigna el estado actual del residuo.
    public void setEstado(String estado) {
        this.estado = estado;
    }


    // Retorna la clasificación asociada al seguimiento.
    public Clasificacion getClasificacion() {
        return clasificacion;
    }


    // Asigna la clasificación asociada al seguimiento.
    public void setClasificacion(
            Clasificacion clasificacion) {

        this.clasificacion = clasificacion;
    }
}