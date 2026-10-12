import React, { useState, useEffect } from "react";
import {
    listarClasificaciones,
    guardarClasificacion,
    actualizarClasificacion
} from "../services/ClasificacionService";
import { listarUsuarios } from "../services/UsuarioService";
import './FormularioClasificacion.css'

import ListaClasificacion from "./ListaClasificacion";
import { listarRecolecciones } from "../services/RecoleccionService";
import { useNavigate, useLocation } from "react-router-dom";





function FormularioClasificacion() {


    const [fecha, setFecha] = useState("");
    const [categoria, setCategoria] = useState("");
    const [descripcion, setDescripcion] = useState("");


    const [nombreUsuario, setNombreUsuario] = useState("");
    const [fechaRecoleccion, setFechaRecoleccion] = useState("");
    const [residuo, setResiduo] = useState("");
    const [cantidad, setCantidad] = useState("");




    const [clasificacion, setClasificacion] = useState([]);
    const [recolecciones, setRecolecciones] = useState([]);
    const [usuarios, setUsuarios] = useState([]);
    const [idRecoleccion, setIdRecoleccion] = useState("");
    const [idUsuario, setIdUsuario] = useState("");



    // Recolección seleccionada para editar
    const [clasificacionEditar, setClasificacionEditar] = useState(null);
    const [recoleccionesUsuario, setRecoleccionesUsuario] = useState([]);

    const [clasificaciones, setClasificaciones] = useState([]);

    const navigate = useNavigate();

    const location = useLocation();

    const [error, setError] = useState({});

    const desdeDashboard = location.state?.desdeDashboard;





    // Cargar usuarios
    async function cargarClasificacion() {

        try {

            const respuesta = await listarClasificaciones();
            setClasificaciones(respuesta);

        } catch (error) {

            console.log(error);

        }
    }


    // Ejecutar al iniciar el componente
    useEffect(() => {

        cargarRecolecciones();
        cargarClasificacion();
        cargarUsuarios();



    }, []);

    useEffect(() => {

        if (clasificacionEditar) {

            // Datos de la clasificación
            setFecha(clasificacionEditar.fecha);
            setCategoria(clasificacionEditar.categoria);
            setDescripcion(clasificacionEditar.descripcion);

            // Recolección relacionada
            if (clasificacionEditar.recoleccion) {

                const id = clasificacionEditar.recoleccion.idRecoleccion;

                setIdRecoleccion(id);

                // Buscar la recolección en la lista
                const recoleccion = recolecciones.find(
                    r => r.idRecoleccion == id
                );

                if (recoleccion) {

                    // Información solamente para mostrar
                    setFechaRecoleccion(recoleccion.fecha);
                    setResiduo(recoleccion.tipoResiduo);
                    setCantidad(recoleccion.cantidadKg);

                    // Buscar usuario relacionado
                    const usuario = usuarios.find(
                        u => u.idUsuario == recoleccion.idUsuario
                    );

                    if (usuario) {

                        setNombreUsuario(
                            usuario.nombre + " " + usuario.apellido
                        );

                        setIdUsuario(usuario.idUsuario);
                    }
                }
            }
        }

    }, [clasificacionEditar, recolecciones, usuarios]);


    // Guardar o actualizar
    async function guardar(e) {
        e.preventDefault();

        const nuevosError = {};

        if (!idUsuario || idUsuario === "Seleccione") {
            nuevosError.idUsuario = "Debes seleccionar un usuario";

        }


        if (!idRecoleccion || idRecoleccion === "Seleccione") {
            nuevosError.idRecoleccion = "Debes seleccionar una recolección";

        }


        if (!fecha.trim()) {
            nuevosError.fecha = "Selecione una fecha";


        }

        if (!categoria.trim()) {
            nuevosError.categoria = "Agrega una categoria ";



        }

        if (!descripcion.trim()) {
            nuevosError.descripcion = "Agrega al menos una noveda de la clasificación";



        }

        setError(nuevosError);

        if (Object.keys(nuevosError).length > 0) {

            return;

        }




        const clasificacion = {
            idClasificacion: clasificacionEditar?.idClasificacion,
            fecha,
            categoria,
            descripcion,
            recoleccion: {
                idRecoleccion: Number(idRecoleccion)
            }
        };

        console.log(clasificacion);


        try {


            if (clasificacionEditar) {

                await actualizarClasificacion(clasificacion);

                alert("Clasificacion actualizada correctamente");


            } else {

                await guardarClasificacion(clasificacion);

                alert("Clasificacion guardada correctamente");


            }




            // Actualizar tabla
            cargarClasificacion();


            // Limpiar formulario
            limpiarFormulario();

        } catch (error) {

            console.error("Error real:", error);

            if (error.response?.status === 403) {
                alert("No tienes permisos para registrar una clasificación.");
                return;
            }

            alert("Error al guardar la clasificación");
        }

    }



    // Limpiar campos
    function limpiarFormulario() {

        setClasificacionEditar(null);
        setFecha("");
        setCategoria("");
        setDescripcion("");
        setIdRecoleccion("");

        setIdRecoleccion("");
        setFechaRecoleccion("");
        setResiduo("");
        setCantidad("");
        setRecoleccionesUsuario([]);



    }



    function cambiarRecoleccion(e) {

        const id = e.target.value;

        setIdRecoleccion(id);

        const recoleccion = recoleccionesUsuario.find(
            r => r.idRecoleccion == id
        );

        if (recoleccion) {

            setFechaRecoleccion(recoleccion.fecha);
            setResiduo(recoleccion.tipoResiduo);
            setCantidad(recoleccion.cantidadKg);

            const usuario = usuarios.find(
                u => u.idUsuario == recoleccion.idUsuario
            );

            if (usuario) {
                setNombreUsuario(
                    usuario.nombre + " " + usuario.apellido
                );
            }
        }

    }

    function cambiarUsuario(e) {

        const id = Number(e.target.value);

        console.log("ID usuario seleccionado:", id);

        console.log("Todas las recolecciones:", recolecciones);


        const resultado = recolecciones.filter(
            r => r.idUsuario === id
        );


        console.log("Recolecciones encontradas:", resultado);


        setIdUsuario(id);
        setRecoleccionesUsuario(resultado);
        setIdRecoleccion("");

    }



    async function cargarUsuarios() {

        try {

            const respuesta = await listarUsuarios();
            setUsuarios(respuesta);

        } catch (error) {

            console.log(error);

        }

    }

    async function cargarRecolecciones() {

        try {

            const respuesta = await listarRecolecciones();

            console.log(respuesta.data);

            setRecolecciones(respuesta.data);

        } catch (error) {

            console.log(error);

        }

    }


    return (
        <div className="clasificacion-pagina">
            <div className="clasificacion-contenedor-principal">

                <div className="clasificacion-card-modulo">
                    <h1>
                        Gestión de Clasificación
                    </h1>
                    <p>
                        Registra, consulta, actualiza y administra la información
                        de las clasificaciones del sistema.
                    </p>

                    {desdeDashboard && (


                        <button className="clasificacion-btn-regresar"
                            onClick={() => navigate("/Dashboard")}>
                            Volver
                        </button>
                    )}

                </div>


                {/* Tarjeta del formulario */}
                <div className="clasificacion-card-formulario">

                    <form className="clasificacion-form" onSubmit={guardar}>

                        {/* ===========================
                        SECCIÓN 1
                        ============================ */}

                        <div className="clasificacion-seccion">

                            <h2>Seleccionar Recolección</h2>




                            <div className="clasificacion-form-grid">



                                <div className="clasificacion-form-group">
                                    <label>Usuario</label>
                                    <select
                                        value={idUsuario}
                                        onChange={cambiarUsuario}
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

                                     {error.idUsuario && (
                                        <div className="clasificacion-mensaje-error">
                                            ❌ {error.idUsuario}
                                        </div>
                                    )}

                                </div>

                                <div className="clasificacion-form-group">
                                    <label>Recolección</label>
                                    <select
                                        value={idRecoleccion}
                                        onChange={cambiarRecoleccion}
                                    >
                                        <option value="">
                                            Seleccione una recolección
                                        </option>
                                        {recoleccionesUsuario.map((recoleccion) => (
                                            <option
                                                key={recoleccion.idRecoleccion}
                                                value={recoleccion.idRecoleccion}
                                            >
                                                {recoleccion.fecha} - {recoleccion.tipoResiduo}
                                            </option>
                                        ))}
                                    </select>
                                     {error.idRecoleccion && (
                                        <div className="clasificacion-mensaje-error">
                                            ❌ {error.idRecoleccion}
                                        </div>
                                    )}

                                </div>

                            </div>

                        </div>

                        {/* ===========================
                        SECCIÓN 2
                        ============================ */}

                        <div className="clasificacion-seccion">

                            <h2>Información de la Recolección</h2>

                            <div className="clasificacion-form-grid">

                                <div className="clasificacion-form-group">
                                    <label>Usuario</label>
                                    <input
                                        type="text"
                                        value={nombreUsuario}
                                        readOnly
                                    />
                                </div>

                                <div className="clasificacion-form-group">
                                    <label>Fecha</label>
                                    <input
                                        type="date"
                                        value={fechaRecoleccion}
                                        readOnly
                                    />
                                </div>

                                <div className="clasificacion-form-group">
                                    <label>Residuo</label>
                                    <input
                                        type="text"
                                        value={residuo}
                                        readOnly
                                    />
                                </div>

                                <div className="clasificacion-form-group">
                                    <label>Cantidad recolectada (Kg)</label>
                                    <input
                                        type="text"
                                        value={cantidad ? `${Number(cantidad).toFixed(2)} Kg ` : ""}
                                        readOnly
                                    />
                                </div>

                            </div>

                        </div>

                        {/* ===========================
                        SECCIÓN 3
                        ============================ */}

                        <div className="clasificacion-seccion">

                            <h2>Datos de la Clasificación</h2>

                            <div className="clasificacion-form-grid">

                                <div className="clasificacion-form-group">
                                    <label>Fecha</label>
                                    <input
                                        type="date"
                                        value={fecha}
                                        onChange={(e) => setFecha(e.target.value)}
                                    />
                                     {error.fecha && (
                                        <div className="clasificacion-mensaje-error">
                                            ❌ {error.fecha}
                                        </div>
                                    )}
                                </div>

                                <div className="clasificacion-form-group">
                                    <label>Categoría</label>
                                    <select
                                        value={categoria}
                                        onChange={(e) => setCategoria(e.target.value)}
                                    >
                                        <option value="">Seleccione</option>
                                        <option value="Aprovechable">Aprovechable</option>
                                        <option value="No Aprovechable">No Aprovechable</option>
                                        <option value="Orgánico">Orgánico</option>
                                    </select>
                                     {error.categoria && (
                                        <div className="clasificacion-mensaje-error">
                                            ❌ {error.categoria}
                                        </div>
                                    )}
                                </div>

                                <div className="clasificacion-form-group clasificacion-full-width">
                                    <label>Observaciones</label>
                                    <textarea
                                        value={descripcion}
                                        onChange={(e) => setDescripcion(e.target.value)}
                                    />
                                     {error.descripcion && (
                                        <div className="clasificacion-mensaje-error">
                                            ❌ {error.descripcion}
                                        </div>
                                    )}
                                </div>

                            </div>

                        </div>

                        {/* Botones */}

                        <div className="clasificacion-form-buttons">
                            <button type="submit" className="clasificacion-btn-guardar">
                                Guardar
                            </button>

                            <button
                                type="button"
                                className="clasificacion-btn-limpiar"
                                onClick={limpiarFormulario}
                            >
                                Limpiar
                            </button>
                        </div>

                    </form>

                </div>

                <div className="clasificacion-contenedor-lista">
                    <ListaClasificacion
                        clasificaciones={clasificaciones}
                        setClasificaciones={setClasificaciones}
                        setClasificacionEditar={setClasificacionEditar}
                    />
                </div>

            </div>
        </div>
    );

};

export default FormularioClasificacion;




