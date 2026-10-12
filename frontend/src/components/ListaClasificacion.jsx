import React, { useState, useEffect } from "react";
import {
    listarClasificaciones,
    eliminarClasificacion

} from "../services/ClasificacionService";

import { listarUsuarios } from "../services/UsuarioService";
import "./ListaClasificacion.css";

function ListarClasificacion({

    clasificaciones,
    setClasificaciones,
    setClasificacionEditar,

}) {

    const [usuarios, setUsuarios] = useState([]);
    const [buscar, setBuscar] = useState("");




    async function cargarClasificaciones() {
        try {
            const respuesta = await listarClasificaciones();
            setClasificaciones(respuesta.data);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {

        cargarUsuarios();
        cargarClasificaciones();

    }, []);

    async function eliminar(id) {

        const confirmar = window.confirm(
            "¿Desea eliminar esta Clasificación?"
        );

        if (!confirmar) return;

        try {

            await eliminarClasificacion(id);


            setClasificaciones(prev =>
                prev.filter(
                    clasificacion =>
                        clasificacion.idClasificacion !== id
                )
            );

            alert("Clasificación eliminada correctamente");

        } catch (error) {

            console.error("Error al eliminar clasificación:", error);

            alert("No se pudo eliminar la clasificación");

        }
    }




    const filtros = (clasificaciones || []).filter((clasificacion) => {
        if (buscar.trim() === "") {
            return true;
        }

        return clasificacion.categoria
            ?.toLowerCase()
            .includes(buscar.toLowerCase());

    });



    async function cargarUsuarios() {
        try {
            const respuesta = await listarUsuarios();
            setUsuarios(respuesta.data);
        } catch (error) {
            console.log(error);
        }


    }




    return (

        <>

            <div className="una-card-modulo">


                <h1>Lista de registro de Clasificaciones</h1>

                <p>
                    Listado de clasificaciones guardadas
                </p>

            </div>


            <input
                type="text"
                className="busqueda-input"
                placeholder="Buscar por categoria"
                value={buscar}
                onChange={(e) => setBuscar(e.target.value)}
            />




            <table className="clasificacion-table">




                <thead>

                    <tr>



                        <th>Fecha</th>
                        <th>Categoria</th>
                        <th>Descripción</th>
                        <th>Acciones</th>

                    </tr>

                </thead>





                <tbody>

                    {filtros.map((clasificaciones) => {

                        const usuario = usuarios?.find(
                            (u) => u.idUsuario === clasificaciones.idUsuario
                        );

                        return (




                            <tr key={clasificaciones.idClasificacion}>
                                <td>{clasificaciones.fecha}</td>
                                <td>{clasificaciones.categoria}</td>
                                <td>{clasificaciones.descripcion}</td>

                                <td>


                                    <div className="acciones">



                                        <button
                                            className="btn-editar"






                                            onClick={() => {
                                                setClasificacionEditar(clasificaciones);
                                                console.log(clasificaciones);
                                            }}
                                        >



                                            Editar


                                        </button>

                                        <button
                                            className="btn-eliminar"

                                            onClick={() => eliminar(clasificaciones.idClasificacion)}
                                        >
                                            Eliminar
                                        </button>

                                    </div>

                                </td>

                            </tr>
                        );

                    })}

                </tbody>



            </table>


        </>

    );

}

export default ListarClasificacion;