import { useEffect, useState } from "react";


import {
    actualizarSeguimiento,
    guardarSeguimiento,
    listarSeguimiento
} from "../services/SeguimientoResiduoService";

import { listarClasificaciones } from "../services/ClasificacionService";
import ListaSeguimiento from "./ListaSeguimiento";
import "./SeguimientoResiduo.css";

import { useNavigate, useLocation } from "react-router-dom";





function FormularioSeguimiento() {

    // Datos del formulario

    const [fechaSeguimiento, setFechaSeguimiento] = useState("");
    const [tipoProceso, setTipoProceso] = useState("");
    const [resultados, setResultados] = useState("");
    const [estado, setEstado] = useState("");


    const [idClasificacion, setIdClasificacion] = useState("");
    const [clasificaciones, setClasificaciones] = useState([]);


    const [clasificacionSeleccionada, setClasificacionSeleccionada] = useState(null);



    const [seguimientos, setSeguimientos] = useState([]);

    // Recolección seleccionada para editar
    const [seguimientoEditar, setSeguimientoEditar] = useState(null);

    const navigate = useNavigate();

    const location = useLocation();

    const [errores, setErrores] = useState({});

    const desdeDashboard = location.state?.desdeDashboard;





    // Cargar recolecciones
    async function cargarSeguimiento() {

        try {

            const respuesta = await listarSeguimiento();
            setSeguimientos(respuesta);

        } catch (error) {

            console.log(error);

        }
    }


    useEffect(() => {
        cargarClasificaciones();
    }, []);

    async function cargarClasificaciones() {
        try {
            const respuesta = await listarClasificaciones();



            setClasificaciones(respuesta);

        } catch (error) {
            console.error("Error cargando clasificaciones:", error);
        }
    }




    // Ejecutar al iniciar el componente
    useEffect(() => {


        cargarSeguimiento();

    }, []);



    // Cargar datos cuando se selecciona una recolección para editar
    useEffect(() => {
        if (seguimientoEditar) {
            setFechaSeguimiento(seguimientoEditar.fechaSeguimiento || "");
            setTipoProceso(seguimientoEditar.tipoProceso || "");
            setResultados(seguimientoEditar.resultado || "");
            setEstado(seguimientoEditar.estado || "");




            setIdClasificacion(
                seguimientoEditar.clasificacion?.idClasificacion || ""
            );



        }
    }, [seguimientoEditar]);

    // Guardar o actualizar
    async function guardar(e) {
        e.preventDefault();

        const nuevoError = {};

        if (!idClasificacion || idClasificacion === "Seleccione") {
            nuevoError.idClasificacion = "Debes seleccionar una clasicacion relacionada";

        }

        if (!tipoProceso || tipoProceso === "Seleccione") {
            nuevoError.tipoProceso = "Debes seleccionar una tipo de proceso";

        }

        if (!estado || estado === "Seleccione") {
            nuevoError.estado = "Debes seleccionar un estado de el residuo";

        }

        if (!resultados || resultados === "Completa") {
            nuevoError.resultados = "Agrega una noveda del seguimiento o residuo";

        }

        setErrores(nuevoError);

        if (Object.keys(nuevoError).length > 0) {

            return;

        }


        const seguimientoResiduo = {
            idSeguimientoResiduo: seguimientoEditar?.idSeguimientoResiduo,
            fechaSeguimiento: fechaSeguimiento,
            tipoProceso,
            resultado: resultados,
            estado,
            clasificacion: {
                idClasificacion: Number(idClasificacion)
            }

        };


        try {


            if (seguimientoEditar) {



                await actualizarSeguimiento(seguimientoResiduo);

                alert("Seguimiento actualizado correctamente");


            } else {

                await guardarSeguimiento(seguimientoResiduo);
                console.log(seguimientoResiduo);

                alert("Seguimientos guardadados correctamente");

            }


            // Actualizar tabla
            cargarSeguimiento();


            // Limpiar formulario
            limpiarFormulario();


        } catch (error) {

            console.error("Error real:", error);

            if (error.response?.status === 403) {
                alert("No tienes permisos para registrar una seguimiento.");
                return;
            }

            alert("Error al guardar el seguimiento");

        }

    }


    // Limpiar campos
    function limpiarFormulario() {

        setSeguimientoEditar(null);
        setIdClasificacion("");
        setFechaSeguimiento("");
        setTipoProceso("");
        setResultados("");
        setEstado("");

        setClasificacionSeleccionada(null);
    }




    function cambiarClasificacion(e) {

        const id = Number(e.target.value);

        console.log("ID Clasificación:", id);

        setIdClasificacion(id);

        const seleccionada = clasificaciones.find(
            c => c.idClasificacion === id
        );

        console.log("Clasificación seleccionada:", seleccionada);

        setClasificacionSeleccionada(seleccionada || null);
    }




    return (

        <div className="pagina-Seguimiento">

            <div className="contenedor-principal">

                {/* Tarjeta con el título y descripción del módulo */}
                <div className="card-modulo">

                    <h1>Gestión de Seguimiento del residuo </h1>

                    <p>
                        Registra, consulta y administra la información de los residuos.
                    </p>

                    {desdeDashboard && (
                        <button className="seguimiento-btn-regresar"
                            onClick={() => navigate("/Dashboard")}>
                            Volver
                        </button>
                    )}

                </div>

                {/* Formulario de registro y edición de recolecciones */}
                <div className="contenedor-formulario">

                    <h1 className="titulo">
                        ♻ SEGUIMIENTO DE RESIDUOS ♻
                    </h1>


                    <div className="campo">

                        <label>Clasificacion</label>

                        <select
                            value={idClasificacion}
                            onChange={cambiarClasificacion}
                            required
                        >

                            <option value="">
                                Seleccione una clasificación
                            </option>

                            {clasificaciones?.map((clasificacion) => (

                                <option
                                    key={clasificacion.idClasificacion}
                                    value={clasificacion.idClasificacion}
                                >
                                    Clasificación #{clasificacion.idClasificacion} -{" "}
                                    {clasificacion.categoria} -{" "}
                                    {clasificacion.fecha}
                                </option>

                            ))}
                        </select>

                        {errores.idClasificacion && (
                            <div className="seguimiento-mensaje-error">
                                ❌ {errores.idClasificacion}
                            </div>

                        )}

                    </div>


                    <div className="campo-imformacion">

                        <h2>Información de la Clasificacion</h2>

                        <div className="clasificacion-ss">

                            <div className="clasificacion-form-group">
                                <label>Categoria</label>
                                <input
                                    type="text"
                                    value={clasificacionSeleccionada?.categoria || ""}
                                    readOnly
                                />
                            </div>

                            <div className="clasificacion-form-group">
                                <label>Fecha de chasificacion</label>
                                <input
                                    type="date"
                                    value={clasificacionSeleccionada?.fecha || ""}
                                    readOnly
                                />
                            </div>

                            <div className="clasificacion-form-group">
                                <label>Tipo residupo</label>
                                <input
                                    type="text"
                                    value={
                                        clasificacionSeleccionada?.recoleccion?.tipoResiduo || ""
                                    }
                                    readOnly
                                />
                            </div>


                            <div className="clasificacion-form-group">
                                <label>Descripcion</label>
                                <input
                                    type="text"
                                    value={clasificacionSeleccionada?.descripcion || ""}
                                    readOnly
                                />
                            </div>


                        </div>

                    </div>


                    <div className="fila">

                        {/* Campo Fecha */}
                        <div className="campo">

                            <label>Fecha de Seguimiento</label>

                            <input
                                type="date"
                                value={fechaSeguimiento}
                                onChange={(e) => setFechaSeguimiento(e.target.value)}
                            />
                            {errores.fechaSeguimiento && (
                                <div className="seguimiento-mensaje-error">
                                    ❌ {errores.fechaSeguimiento}
                                </div>

                            )}

                        </div>

                    </div>


                    {/* Campo Tipo de Residuo */}
                    <div className="campo">

                        <label>Tipo de Proceso</label>

                        <select
                            value={tipoProceso}
                            onChange={(e) => setTipoProceso(e.target.value)}
                        >

                            <option value="">
                                Seleccione
                            </option>

                            <option value="Clasificacion">
                                Clasificacion
                            </option>

                            <option value="Reciclaje">
                                Reciclaje
                            </option>

                            <option value=" Reutilizacion">
                                Reutilizacion
                            </option>

                            <option value="Compostaje">
                                Compostaje
                            </option>

                            <option value=" Disposiciónfinal">
                                Disposiciónfinal
                            </option>

                        </select>

                        {errores.tipoProceso && (
                            <div className="seguimiento-mensaje-error">
                                ❌ {errores.tipoProceso}
                            </div>

                        )}

                    </div>


                    <div className="campo">

                        <label>Estado</label>

                        <select
                            value={estado}
                            onChange={(e) => setEstado(e.target.value)}
                        >

                            <option value="">
                                Seleccione
                            </option>

                            <option value="Pendiente">
                                Pendiente
                            </option>

                            <option value="Enproceso">
                                En proceso
                            </option>

                            <option value=" Finalizado">
                                Finalizado
                            </option>

                        </select>
                        {errores.estado && (
                            <div className="seguimiento-mensaje-error">
                                ❌ {errores.estado}
                            </div>

                        )}

                    </div>


                    {/* Campo Observaciones */}
                    <div className="campo">

                        <label>Resultados</label>

                        <textarea
                            value={resultados}
                            onChange={(e) => setResultados(e.target.value)}
                        />
                        {errores.resultados && (
                            <div className="seguimiento-mensaje-error">
                                ❌ {errores.resultados}
                            </div>

                        )}

                    </div>


                    {/* Botón para guardar o actualizar la recolección */}
                    <div className="seguimiento-form-buttons">

                        <button
                            type="button"
                            className="seguimiento-btn-guardar"
                            onClick={guardar}
                        >
                            Guardar Seguimiento
                        </button>

                        <button
                            type="button"
                            className="seguimiento-btn-limpiar"
                            onClick={() => {
                                setIdClasificacion("");
                                setTipoProceso("");
                                setResultados("");
                                setFechaSeguimiento("");
                                setEstado("");
                                setErrores("")

                            }}
                        >
                            Limpiar formulario
                        </button>

                    </div>

                </div>


                {/* Componente que muestra la lista de recolecciones */}
                <div className="contenedor-lista">

                </div>


                {/* Lista */}

                <div className="contenedor-lista">

                    <ListaSeguimiento
                        seguimientos={seguimientos}
                        setSeguimientos={setSeguimientos}
                        setSeguimientoEditar={setSeguimientoEditar}
                    />

                </div>

            </div>

        </div>

    );
}

export default FormularioSeguimiento;