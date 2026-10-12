import '../pages/Perfil.css';
import axios from "axios";
import React, { useState, useEffect } from 'react';
import { listarUsuarios, buscarUsuarioPorCorreo, editarUsuario } from '../services/UsuarioService'; // <-- 1. Agregado 'editarUsuario' aquí

function Perfil() {

    const [nombre, setNombre] = useState("");
    const [apellido, setApellido] = useState("");
    const [correo, setCorreo] = useState("");
    const [telefono, setTelefono] = useState("")
    const [rol, setRol] = useState("");
    const [editar, setEditar] = useState(false);

    const [idUsuario, setIdUsuario] = useState("");

    

    const correoUsuario = localStorage.getItem("correo");


    useEffect(() => {
        const obtenerDatosUsuario = async () => {
            try {
                const response = await buscarUsuarioPorCorreo(correoUsuario);
                const data = response.data;

                setIdUsuario(data.idUsuario);
                setNombre(data.nombre);
                setApellido(data.apellido);
                setCorreo(data.correo);
                setTelefono(data.telefono);
                setRol(data.rol);

            } catch (error) {
                console.error("Error al obtener la información del usuario:", error);
            }
        };

        if (correoUsuario) {
            obtenerDatosUsuario();
        }
    }, [correoUsuario]);




    function editarPerfil() {
        setEditar(true);
    }

    const actualizarPerfil = async () => {
        if (nombre.trim() === "") {
            alert("Debe ingresar el nombre.");
            return;
        }
        if (apellido.trim() === "") {
            alert("Debe ingresar el apellido.");
            return;
        }

        if (correo.trim() === "") {
            alert("Debe ingresar el correo.");
            return;
        }

        if (telefono.trim() === "") {
            alert("Debe ingresar el teléfono.");
            return;
        }

        const usuarioActualizado = {
            idUsuario: idUsuario,
            nombre,
            apellido,
            correo,
            telefono,
            rol
        };

        try {
       
            await editarUsuario( usuarioActualizado);

            alert("¡Perfil actualizado correctamente!");
            setEditar(false);
        } catch (error) {
            console.error("Error al actualizar:", error);
            
            if (error.response?.status === 403) {
                alert("Acceso denegado (403): Tu token pudo haber expirado. Prueba cerrando sesión y volviendo a entrar.");
            } else {
                alert("Hubo un error al actualizar el perfil.");
            }
        }
    };

    const inicial = nombre ? nombre.charAt(0).toUpperCase() : "U";


    return (
        <div className="perfil-body">
            <div className="popover">
                <div className="gestion-banner">
                    <strong>MI PERFIL</strong>
                </div>

                <div className="perfil-content">
                    <div className="perfil-content">
                        <div className="avatar-box">
                            {/* Círculo con la inicial dinámica */}
                            <div className="avatar-iniciales">
                                {inicial}
                            </div>
                        </div>


                    </div>

                    <div className="info-box">
                        <label>Nombre</label>
                        <input
                            type="text"
                            value={nombre}
                            disabled={!editar}
                            onChange={(e) => setNombre(e.target.value)}
                        />

                        <label>Apellido</label>
                        <input
                            type="text"
                            value={apellido}
                            disabled={!editar}
                            onChange={(e) => setApellido(e.target.value)}
                        />

                        <label>Teléfono</label>
                        <input
                            type="telefono"
                            value={telefono}
                            disabled={!editar}
                            onChange={(e) => setTelefono(e.target.value)}
                        />

                        <label>Correo</label>
                        <input
                            type="email"
                            value={correo}
                            disabled
                            onChange={(e) => setCorreo(e.target.value)}
                        />



                        <label>Rol</label>
                        <input
                            type="text"
                            value={rol}
                            disabled
                        />
                    </div>

                
                </div>

                <div className="footer-buttons">
                    <button
                        className="btn-gris"
                        onClick={editarPerfil}
                    >
                        Editar Perfil
                    </button>

                    <button
                        className="btn-gris"
                        onClick={actualizarPerfil}
                    >
                        Actualizar Cuenta
                    </button>


                </div>


            </div>
        </div>



    );
}

export default Perfil;