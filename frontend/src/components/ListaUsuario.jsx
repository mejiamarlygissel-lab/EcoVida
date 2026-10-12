import React, { useState, useEffect } from "react";
import {
    listarUsuarios as listarUsuariosService,
    editarUsuario,
    eliminarUsuario
} from "../services/UsuarioService";
import "./ListaUsuario.css";




function ListaUsuario({
    usuarios,
    setUsuarios,
    setUsuarioEditar,
    cargarUsuarios,
    rolUsuario
}) {

    const [buscar, setBuscar] = useState("");


    async function listarUsuarios() {

        try {

            const respuesta = await listarUsuariosService();

            setUsuarios(respuesta);

        } catch (error) {

            console.log(error);

        }

    }




    function editar(usuario) {

        setUsuarioEditar(usuario);
    }


    async function eliminar(id) {

        if (window.confirm("¿Desea eliminar este usuario?")) {

            try {

                console.log("ENVIANDO DELETE:", id);

                await eliminarUsuario(id);

                alert("Usuario eliminado correctamente");

                listarUsuarios();

            } catch (error) {

                console.error("Error al eliminar usuario:", error);

                if (error.response?.status === 403) {
                    alert("No tienes permisos para eliminar este usuario.");
                    return;
                }

                if (error.response?.status === 409) {
                    alert(
                        "No se puede eliminar el usuario porque tiene registros relacionados."
                    );
                    return;
                }

                alert("No fue posible eliminar el usuario.");
            }
        }
    }

    const filtros = usuarios?.filter((item) =>
        item.nombre.toLowerCase().includes(buscar.toLowerCase()) ||
        item.apellido.toLowerCase().includes(buscar.toLowerCase())
    );



    return (



        <>




            <div className="card-modulo">


                <h1>Lista de usuarios</h1>

                <p>
                    Listado de usuarios registrados
                </p>

            </div>


            {(!rolUsuario === "ADMINISTRADOR") && (

                <input
                    type="text"
                    className="busqueda-input"
                    placeholder="Buscar por nombre del usuario"
                    value={buscar}
                    onChange={(e) => setBuscar(e.target.value)}
                />
            )}



            <table className="usuarios-table">

                <thead>

                    <tr>


                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Correo Electronico</th>
                        <th>Teléfono</th>
                        <th>Rol</th>
                        <th>Acciones</th>


                    </tr>

                </thead>

                <tbody>

                    {filtros?.map((usuario) => (

                        <tr key={usuario.idUsuario}>


                            <td>{usuario.nombre}</td>
                            <td>{usuario.apellido}</td>
                            <td>{usuario.correo}</td>
                            <td>{usuario.telefono}</td>

                            <td>{usuario.rol}</td>


                            <td>
                                {rolUsuario === "ADMINISTRADOR" && (
                                    <div className="acciones">

                                        <button
                                            className="btn-editar"
                                            onClick={() => setUsuarioEditar(usuario)}
                                        >
                                            Editar
                                        </button>

                                        <button
                                            className="btn-eliminar"
                                            onClick={() => eliminar(usuario.idUsuario)}
                                        >
                                            Eliminar
                                        </button>

                                    </div>
                                )}

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>





        </>

    );

}

export default ListaUsuario;