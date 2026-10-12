import { useState, useEffect } from "react";
import "./FormularioRecoleccion.css";

import {
    guardarRecoleccion,
    editarRecoleccion,
    listarRecolecciones
} from "../services/RecoleccionService";

import ListaRecoleccion from "./ListaRecoleccion";

import { listarUsuarios } from "../services/UsuarioService";
import { useNavigate, useLocation } from "react-router-dom";


import Mapa from "./Mapa";


function FormularioRecoleccion() {

    // Datos del formulario
    const [hora, setHora] = useState("");
    const [fecha, setFecha] = useState("");
    const [idUsuario, setIdUsuario] = useState("");
    const [ubicacion, setUbicacion] = useState("");
    const [tipoResiduo, setTipoResiduo] = useState("");
    const [cantidadKg, setCantidadKg] = useState("");
    const [descripcion, setDescripcion] = useState("");

    // Listas
    const [usuarios, setUsuarios] = useState([]);

    const [recolecciones, setRecolecciones] = useState([]);

    // Recolección seleccionada para editar
    const [recoleccionEditar, setRecoleccionEditar] = useState(null);

    const [posicion, setPosicion] = useState(null);

    const navigate = useNavigate();

    const location = useLocation();

    const [errores, setErrores] = useState({})

    const desdeDashboard = location.state?.desdeDashboard;

    // Cargar recolecciones
    async function cargarRecolecciones() {

        try {

            const respuesta = await listarRecolecciones();
            console.log("RESPUESTA USUARIOS:", respuesta);
            console.log("DATOS USUARIOS:", respuesta.data);

            setRecolecciones(respuesta.data);


        } catch (error) {

            console.log(error);

        }
    }


    // Cargar usuarios
    async function cargarUsuarios() {

        try {

            const respuesta = await listarUsuarios();
            setUsuarios(respuesta);

        } catch (error) {

            console.log(error);

        }
    }


    // Ejecutar al iniciar el componente
    useEffect(() => {

        cargarUsuarios();
        cargarRecolecciones();

    }, []);



    // Cargar datos cuando se selecciona una recolección para editar
    useEffect(() => {

        if (recoleccionEditar) {

            setHora(recoleccionEditar.hora);
            setFecha(recoleccionEditar.fecha);
            setIdUsuario(recoleccionEditar.idUsuario);
            setUbicacion(recoleccionEditar.ubicacion);
            setTipoResiduo(recoleccionEditar.tipoResiduo);
            setCantidadKg(recoleccionEditar.cantidadKg);
            setDescripcion(recoleccionEditar.descripcion);

            // Cargar ubicación en el mapa
            if (recoleccionEditar.latitud && recoleccionEditar.longitud) {

                setPosicion({
                    lat: recoleccionEditar.latitud,
                    lng: recoleccionEditar.longitud
                });

            }



        }

    }, [recoleccionEditar]);



    async function guardar(e) {
        e.preventDefault();

        const nuevosErrores = {};

        const rol = localStorage.getItem("rol");
        console.log("ROL EN LOCALSTORAGE:", rol);



        // Bloquear al JEFE
        if (rol === "JEFE") {
            alert("No tienes permisos para registrar una recolección.");

            return;

        }

        if (!hora.trim()) {
            nuevosErrores.hora = "Seleccione la hora";

        }

        if (!fecha.trim()) {
            nuevosErrores.fecha = "Seleccione una fecha";

        }

        if (!idUsuario || idUsuario === "Seleccione") {
            nuevosErrores.idUsuario = "Debes seleccionar un usuario correspondiente";

        }

        if (!tipoResiduo || tipoResiduo.trim() === "" || tipoResiduo === "Seleccione") {
            nuevosErrores.tipoResiduo = "Debes seleccionar un residuo";

        }

        if (!cantidadKg) {
            nuevosErrores.cantidadKg = "Agrega la cantidad del residuo";

        }

        if (!descripcion.trim()) {
            nuevosErrores.descripcion = "Agrega al menos una novedad de su recolección";

        }

        if (!ubicacion.trim()) {
            nuevosErrores.ubicacion = "Agrega una ubicación";

        }

        setErrores(nuevosErrores);

        if (Object.keys(nuevosErrores).length > 0) {

            return;

        }

        const recoleccion = {
            idRecoleccion: recoleccionEditar?.idRecoleccion,
            hora,
            fecha,
            idUsuario,
            ubicacion,
            tipoResiduo,
            cantidadKg,
            descripcion,
            latitud: posicion?.lat,
            longitud: posicion?.lng,
        };

        try {

            if (recoleccionEditar) {

                await editarRecoleccion(recoleccion);
                alert("Recolección actualizada correctamente");

            } else {

                await guardarRecoleccion(recoleccion);
                alert("Recolección guardada correctamente");
            }

            cargarRecolecciones();
            limpiarFormulario();

        } catch (error) {

            console.error("Error real:", error);

            if (error.response?.status === 403) {
                alert("No tienes permisos para registrar una recolección.");
                return;
            }

            alert("Error al guardar la recolección");
        }
    }



    // Limpiar campos
    function limpiarFormulario() {

        setRecoleccionEditar(null);
        setHora("");
        setFecha("");
        setIdUsuario("");
        setUbicacion("");
        setTipoResiduo("");
        setCantidadKg("");
        setDescripcion("");
        setPosicion(null);

    }



    return (
        <div className="recoleccion-pagina">

            <div className="recoleccion-contenedor-principal">

                {/* =====================================================
                ENCABEZADO DEL MÓDULO
            ====================================================== */}

                <div className="recoleccion-card-modulo">

                    <span className="recoleccion-etiqueta">
                        MÓDULO RECOLECCIONES
                    </span>

                    <h1>
                        Gestión de Recolecciones
                    </h1>

                    <p>
                        Registra, consulta y administra la información
                        de las recolecciones.
                    </p>

                    {desdeDashboard && (

                        <button className="recoleccion-btn-regresar"
                            onClick={() => navigate("/Dashboard")}>
                            Volver
                        </button>
                    )}


                </div>


                {/* =====================================================
                TARJETA PRINCIPAL DEL FORMULARIO
            ====================================================== */}

                <div className="recoleccion-card-formulario">

                    <div className="recoleccion-form-header">

                        <div>

                            <span className="recoleccion-form-icono">
                                ♻
                            </span>

                            <div>

                                <h2>
                                    Registro de Recolección
                                </h2>

                                <p>
                                    Ingresa la información correspondiente
                                    a la recolección de residuos.
                                </p>

                            </div>

                        </div>

                    </div>


                    {/* =================================================
                    FORMULARIO
                ================================================== */}

                    <div className="recoleccion-form-grid">


                        {/* =================================================
                        HORA
                    ================================================== */}

                        <div className="recoleccion-form-group">

                            <label>
                                Hora
                            </label>

                            <input
                                type="time"
                                value={hora}
                                onChange={(e) => setHora(e.target.value)}
                            />

                            {errores.hora && (
                                <div className="recoleccion-mensaje-error">
                                    ❌ {errores.hora}
                                </div>

                            )}

                        </div>


                        {/* =================================================
                        FECHA
                    ================================================== */}

                        <div className="recoleccion-form-group">

                            <label>
                                Fecha
                            </label>

                            <input
                                type="date"
                                value={fecha}
                                onChange={(e) => setFecha(e.target.value)}
                            />
                            {errores.fecha && (
                                <div className="recoleccion-mensaje-error">
                                    ❌ {errores.fecha}
                                </div>

                            )}

                        </div>


                        {/* =================================================
                        USUARIO
                    ================================================== */}

                        <div className="recoleccion-form-group">

                            <label>
                                Usuario
                            </label>

                            <select
                                value={idUsuario}
                                onChange={(e) => setIdUsuario(e.target.value)}
                            >

                                <option value="">
                                    Seleccione un usuario
                                </option>

                                {usuarios?.map((usuario) => (

                                    <option
                                        key={usuario.idUsuario}
                                        value={usuario.idUsuario}
                                    >

                                        {usuario.nombre} {usuario.apellido}

                                    </option>

                                ))}

                            </select>

                            {errores.idUsuario && (
                                <div className="recoleccion-mensaje-error">
                                    ❌ {errores.idUsuario}
                                </div>

                            )}

                        </div>


                        {/* =================================================
                        TIPO DE RESIDUO
                    ================================================== */}

                        <div className="recoleccion-form-group">

                            <label>
                                Tipo de Residuo
                            </label>

                            <select
                                value={tipoResiduo}
                                onChange={(e) => setTipoResiduo(e.target.value)}
                            >

                                <option value="">
                                    Seleccione
                                </option>

                                <option value="Plastico">
                                    Plástico
                                </option>

                                <option value="Papel">
                                    Papel
                                </option>

                                <option value="Cartón">
                                    Cartón
                                </option>

                                <option value="Vidrio">
                                    Vidrio
                                </option>

                                <option value="Metal">
                                    Metal
                                </option>

                            </select>

                            {errores.tipoResiduo && (
                                <div className="recoleccion-mensaje-error">
                                    ❌ {errores.tipoResiduo}
                                </div>

                            )}

                        </div>


                        {/* =================================================
                        CANTIDAD
                    ================================================== */}

                        <div className="recoleccion-form-group">

                            <label>
                                Cantidad (Kg)
                            </label>

                            <input
                                type="number"
                                value={cantidadKg}
                                onChange={(e) => setCantidadKg(e.target.value)}
                                placeholder="Ej. 25"
                                min="0"
                            />
                            {errores.cantidadKg && (
                                <div className="recoleccion-mensaje-error">
                                    ❌ {errores.cantidadKg}
                                </div>

                            )}

                        </div>


                        {/* =================================================
                        OBSERVACIONES
                    ================================================== */}

                        <div className="recoleccion-form-group recoleccion-full-width">

                            <label>
                                Observaciones
                            </label>

                            <textarea
                                value={descripcion}
                                onChange={(e) => setDescripcion(e.target.value)}
                                placeholder="Ingrese observaciones sobre la recolección..."
                            />

                            {errores.descripcion && (
                                <div className="recoleccion-mensaje-error">
                                    ❌ {errores.descripcion}
                                </div>

                            )}

                        </div>


                        {/* =================================================
                        UBICACIÓN
                    ================================================== */}

                        <div className="recoleccion-form-group recoleccion-full-width">

                            <label>
                                Ubicación
                            </label>

                            <div className="recoleccion-mapa-container">

                                <Mapa
                                    setUbicacion={setUbicacion}
                                    setPosicion={setPosicion}
                                />

                            </div>


                            <input
                                type="text"
                                value={ubicacion}
                                readOnly
                                placeholder="Seleccione una ubicación en el mapa"
                                className="recoleccion-ubicacion-input"
                            />

                            {errores.ubicacion && (
                                <div className="recoleccion-mensaje-error">
                                    ❌ {errores.ubicacion}
                                </div>

                            )}

                        </div>

                    </div>


                    {/* =====================================================
                    BOTONES
                ====================================================== */}

                    <div className="recoleccion-form-buttons">

                        <button
                            type="button"
                            className="recoleccion-btn-guardar"
                            onClick={guardar}
                        >
                            Guardar Recolección
                        </button>


                        
                        <button
                            type="button"
                            className="recoleccion-btn-limpiar"
                            onClick={() => {
                                setFecha("");
                                setHora("");
                                setIdUsuario("");
                                setTipoResiduo("");
                                setCantidadKg("");
                                setUbicacion("");
                                setDescripcion("");

                            }}
                        >
                            Limpiar formulario
                        </button>

                    </div>

                   

                  


                </div>


                {/* =====================================================
                LISTA DE RECOLECCIONES
            ====================================================== */}

                <div className="recoleccion-contenedor-lista">

                    <ListaRecoleccion
                        setRecoleccionEditar={setRecoleccionEditar}
                        recolecciones={recolecciones}
                        setRecolecciones={setRecolecciones}
                        cargarRecolecciones={cargarRecolecciones}
                    />

                </div>

            </div>

        </div>
    );

}

export default FormularioRecoleccion;