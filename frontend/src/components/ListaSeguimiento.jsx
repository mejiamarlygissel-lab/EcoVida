import React, { useState, useEffect } from "react";
import {
    listarSeguimiento,
    guardarSeguimiento,
    actualizarSeguimiento,
    eliminarSeguimiento

} from "../services/SeguimientoResiduoService";

import { listarUsuarios } from "../services/UsuarioService";
import "./ListaSeguimiento.css";

function ListaSeguimiento({

    seguimientos,
    setSeguimientos,
    setSeguimientoEditar,

}) {

    const [usuarios, setUsuarios] = useState([]);
    const [buscar, setBuscar] = useState("");




    const cargarSeguimientos = async () => {
        const respuesta = await listarSeguimiento();
        setSeguimientos(respuesta);
    };

    useEffect(() => {


        cargarSeguimientos();

    }, []);

    async function eliminar(id) {

        const confirmar = window.confirm(
            "¿Desea eliminar este seguimiento?"
        );

        if (!confirmar) return;

        try {

            await eliminarSeguimiento(id);


            await cargarSeguimientos();

            alert("Seguimiento eliminado correctamente");

            cargarSeguimiento();

        } catch (error) {

            console.log(error);

        }



    }

    const filtros = (seguimientos || []).filter((seguimiento) => {
        if (buscar.trim() === "") {
            return true;
        }

        return seguimiento.estado
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

            <div className="card-modulo">


                <h1>Lista de seguimientos residuos</h1>

                <p>
                    Listado de seguimientos guardadas
                </p>

            </div>

            <input
                type="text"
                className="busqueda-input"
                placeholder="Buscar por estado de residuo"
                value={buscar}
                onChange={(e) => setBuscar(e.target.value)}
            />



            <table className="seguimiento-table">


                <thead>

                    <tr>



                        <th>Fecha</th>
                        <th>TipoProceso</th>
                        <th>Estado</th>
                        <th>Resultado</th>
                        <th>Acciones</th>

                    </tr>

                </thead>



                <tbody>
                    {filtros.map((seguimientos) => (
                        <tr key={seguimientos.idSeguimientoResiduo}>
                            <td>{seguimientos.fechaSeguimiento}</td>
                            <td>{seguimientos.tipoProceso}</td>
                            <td>{seguimientos.estado}</td>
                            <td>{seguimientos.resultado}</td>

                            <td>
                                <div className="acciones">
                                    <button
                                        className="btn-editar"
                                        onClick={() => {
                                            setSeguimientoEditar(seguimientos);
                                        }}
                                    >
                                        Editar
                                    </button>

                                    <button
                                        className="btn-eliminar"
                                        onClick={() => eliminar(seguimientos.idSeguimientoResiduo)}
                                    >
                                        Eliminar
                                    </button>
                                </div>
                            </td>
                        </tr>

                    ))}


                </tbody>



            </table>


        </>

    );

}

export default ListaSeguimiento;