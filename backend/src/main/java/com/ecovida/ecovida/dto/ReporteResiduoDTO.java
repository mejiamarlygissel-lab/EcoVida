package com.ecovida.ecovida.dto;


// DTO utilizado para construir y enviar
// la información completa de un reporte de residuos.
//
// Este objeto reúne información relacionada con:
// Usuario → Recolección → Clasificación → Seguimiento.
//
// Se utiliza para enviar al frontend únicamente
// la información necesaria para mostrar el reporte.
public class ReporteResiduoDTO {


    // Identificador del usuario.
    private Long idUsuario;


    // Nombre del usuario.
    private String nombre;


    // Apellido del usuario.
    private String apellido;


    // Correo electrónico del usuario.
    private String correo;


    // Identificador de la recolección.
    private Long idRecoleccion;


    // Fecha en la que se realizó la recolección.
    private String fecha;


    // Tipo de residuo recolectado.
    private String tipoResiduo;


    // Cantidad de residuo recolectado en kilogramos.
    private Double cantidadKg;


    // Ubicación donde se realizó la recolección.
    private String ubicacion;


    // Categoría asignada durante la clasificación.
    private String categoria;


    // Descripción de la clasificación.
    private String descripcion;


    // Fecha en la que se realizó la clasificación.
    private String fechaClasificacion;


    // Estado actual del residuo dentro del seguimiento.
    private String estado;


    // Tipo de proceso aplicado al residuo.
    private String tipoProceso;


    // Resultado obtenido durante el proceso.
    private String resultado;


    // Fecha en la que se realizó el seguimiento.
    private String fechaSeguimiento;


    // Constructor vacío.
    //
    // Permite crear un objeto ReporteResiduoDTO
    // sin proporcionar información inicialmente.
    public ReporteResiduoDTO() {
    }


    // Constructor utilizado para crear un reporte
    // con toda la información del proceso.
    public ReporteResiduoDTO(
            Long idUsuario,
            String nombre,
            String apellido,
            String correo,
            Long idRecoleccion,
            String fecha,
            String tipoResiduo,
            Double cantidadKg,
            String ubicacion,
            String categoria,
            String descripcion,
            String fechaClasificacion,
            String estado,
            String tipoProceso,
            String resultado,
            String fechaSeguimiento) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.idRecoleccion = idRecoleccion;
        this.fecha = fecha;
        this.tipoResiduo = tipoResiduo;
        this.cantidadKg = cantidadKg;
        this.ubicacion = ubicacion;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fechaClasificacion = fechaClasificacion;
        this.estado = estado;
        this.tipoProceso = tipoProceso;
        this.resultado = resultado;
        this.fechaSeguimiento = fechaSeguimiento;
    }


    // Obtiene el identificador del usuario.
    public Long getIdUsuario() {
        return idUsuario;
    }


    // Obtiene el nombre del usuario.
    public String getNombre() {
        return nombre;
    }


    // Obtiene la categoría del residuo.
    public String getCategoria() {
        return categoria;
    }


    // Modifica la categoría del residuo.
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    // Obtiene la descripción de la clasificación.
    public String getDescripcion() {
        return descripcion;
    }


    // Modifica la descripción de la clasificación.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    // Obtiene la fecha de clasificación.
    public String getFechaClasificacion() {
        return fechaClasificacion;
    }


    // Modifica la fecha de clasificación.
    public void setFechaClasificacion(String fechaClasificacion) {
        this.fechaClasificacion = fechaClasificacion;
    }


    // Obtiene el estado actual del residuo.
    public String getEstado() {
        return estado;
    }


    // Modifica el estado del residuo.
    public void setEstado(String estado) {
        this.estado = estado;
    }


    // Obtiene el tipo de proceso realizado.
    public String getTipoProceso() {
        return tipoProceso;
    }


    // Modifica el tipo de proceso realizado.
    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }


    // Obtiene el resultado del proceso.
    public String getResultado() {
        return resultado;
    }


    // Modifica el resultado del proceso.
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }


    // Obtiene la fecha del seguimiento.
    public String getFechaSeguimiento() {
        return fechaSeguimiento;
    }


    // Modifica la fecha del seguimiento.
    public void setFechaSeguimiento(String fechaSeguimiento) {
        this.fechaSeguimiento = fechaSeguimiento;
    }


    // Obtiene el apellido del usuario.
    public String getApellido() {
        return apellido;
    }


    // Obtiene el correo electrónico del usuario.
    public String getCorreo() {
        return correo;
    }


    // Obtiene el identificador de la recolección.
    public Long getIdRecoleccion() {
        return idRecoleccion;
    }


    // Obtiene la fecha de la recolección.
    public String getFecha() {
        return fecha;
    }


    // Obtiene el tipo de residuo.
    public String getTipoResiduo() {
        return tipoResiduo;
    }


    // Obtiene la cantidad de residuo en kilogramos.
    public Double getCantidadKg() {
        return cantidadKg;
    }


    // Obtiene la ubicación de la recolección.
    public String getUbicacion() {
        return ubicacion;
    }
}