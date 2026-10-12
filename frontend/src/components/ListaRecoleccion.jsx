import React, { useState, useEffect } from "react";
import {

    eliminarRecoleccion

} from "../services/RecoleccionService";
import "./ListaRecoleccion.css";
import { listarUsuarios } from "../services/UsuarioService";

function ListarRecoleccion({
    recolecciones,
    setRecolecciones,
    setRecoleccionEditar,
    cargarRecolecciones
}) {


    const [usuarios, setUsuarios] = useState([]);
    const [buscar, setBuscar] = useState("");




    useEffect(() => {

        cargarUsuarios();

    }, []);

    async function eliminar(id) {

        const confirmar = window.confirm(
            "¿Desea eliminar esta recolección?"
        );

        if (!confirmar) return;

        try {

            await eliminarRecoleccion(id);

            alert("Recolección eliminada");

            await cargarRecolecciones();

        } catch (error) {

            console.log(error);

        }

    }



    const filtros = recolecciones.filter((recoleccion) => {

        if (buscar.trim() === "") {
            return true;
        }

        return recoleccion.tipoResiduo
        ?.toLowerCase()
        .includes(buscar.toLowerCase());
    });



    async function cargarUsuarios() {
        try {
            const respuesta = await listarUsuarios();
            setUsuarios(respuesta);
        } catch (error) {
            console.log(error);
        }


    }




    return (

        <>

            <div className="card-modulo">


                <h1>Lista de registro de recolecciones</h1>

                <p>
                    Listado de recolecciones guardadas
                </p>

            </div>


            <input
                type="text"
                className="busqueda-input"
                placeholder="Buscar por tipo de residuo"
                value={buscar}
                onChange={(e) => setBuscar(e.target.value)}
            />




            <table className="recoleccion-table">




                <thead>

                    <tr>



                        <th>Usuario</th>
                        <th>Fecha</th>
                        <th>Hora</th>
                        <th>Ubicación</th>
                        <th>Tipo Residuo</th>
                        <th>Cantidades</th>
                        <th>Descripción</th>
                        <th>Acciones</th>

                    </tr>

                </thead>





                <tbody>

                    {filtros.map((recoleccion) => {
                        

                        const usuario = usuarios?.find(
                            (u) => Number(u.idUsuario) === Number(recoleccion.idUsuario)
                        );

                        return (




                            <tr key={recoleccion.idRecoleccion}>

                                <td>
                                    {usuario 
                                        ? `${usuario.nombre} ${usuario.apellido}`
                                        : "Sin usuario"}
                                </td>

                                <td>{recoleccion.fecha}</td>
                                <td>{recoleccion.hora}</td>
                                <td>{recoleccion.ubicacion}</td>
                                <td>{recoleccion.tipoResiduo}</td>
                                <td>{Number(recoleccion.cantidadKg).toFixed(2)} kg</td>
                                <td>{recoleccion.descripcion}</td>

                                <td>

                                    <div className="acciones">

                                        <button
                                            className="btn-editar"
                                            onClick={() => setRecoleccionEditar(recoleccion)}
                                        >
                                            Editar
                                        </button>

                                        <button
                                            className="btn-eliminar"
                                            onClick={() => eliminar(recoleccion.idRecoleccion)}
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

export default ListarRecoleccion;