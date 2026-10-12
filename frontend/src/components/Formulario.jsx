import React, { useState, useEffect } from "react";
import "./Formulario.css";
import {
    registrarUsuario,
    editarUsuario,
    listarUsuarios,
    buscarUsuarioPorCorreo
} from "../services/UsuarioService";
import ListaUsuario from "./ListaUsuario";
import { useNavigate, useLocation } from "react-router-dom";

function Formulario() {



    // Estados para almacenar la información del formulario
    const [nombre, setNombre] = useState("");
    const [apellido, setApellido] = useState("");
    const [correo, setCorreo] = useState("");
    const [telefono, setTelefono] = useState("");
    const [password, setPassword] = useState("");
    const [rol, setRol] = useState("");

    // Estado que almacena el usuario seleccionado para editar
    const [usuarioEditar, setUsuarioEditar] = useState(null);

    // Lista de usuarios
    const [usuarios, setUsuarios] = useState([]);

    const navigate = useNavigate();

    const location = useLocation();

    const [error, setError] = useState("");

    const [exitoso, setExitoso] = useState(false);

    const desdeDashboard = location.state?.desdeDashboard;

    const [rolUsuario, setRolUsuario] = useState("");



    // Función para cargar los usuarioss
    async function cargarUsuarios() {


        try {

            const respuesta = await listarUsuarios();



            setUsuarios(respuesta);

        } catch (error) {

            console.log(error);

        }

    }

    // Se ejecuta una sola vez al iniciar el componente
    useEffect(() => {
        cargarUsuarios();
    }, []);

    useEffect(() => {
        async function cargarRol() {
            try {
                const correo = localStorage.getItem("correo");

                if (!correo) {
                    console.log("No hay correo guardado");
                    return;
                }

                const respuesta = await buscarUsuarioPorCorreo(correo);

                console.log("DATOS DEL USUARIO:", respuesta.data);
                console.log("ROL OBTENIDO:", respuesta.data.rol);

                setRolUsuario(respuesta.data.rol);

            } catch (error) {
                console.error("Error al obtener el rol:", error);
            }
        }

        cargarRol();
    }, []);

    // Cuando se selecciona un usuario para editar
    useEffect(() => {

        if (rolUsuario === "JEFE") {
            alert("No tienes permisos para editar un usuario.");
            return;
        }

        if (usuarioEditar) {

            setNombre(usuarioEditar.nombre);
            setApellido(usuarioEditar.apellido);
            setCorreo(usuarioEditar.correo);
            setTelefono(usuarioEditar.telefono);
            setPassword("");
            setRol(usuarioEditar.rol);

        }

    }, [usuarioEditar]);

    
    async function guardar(e) {
        e.preventDefault();

        const nuevosErrores = {};



        if (!nombre.trim()) {
            nuevosErrores.nombre = "El nombre es un campo obligatorio.";

        }

        if (!apellido.trim()) {
            nuevosErrores.apellido = "El apellido es un campo obligatorio.";

        }


        const regexCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!regexCorreo.test(correo)) {
            nuevosErrores.correo = "Ingrese una dirección de correo electrónico válida.";

        }

        // Validación para que el teléfono tenga exactamente 10 números
        const regexTelefono = /^\d{10}$/;
        if (!regexTelefono.test(telefono)) {
            nuevosErrores.telefono = "El número de teléfono debe contener exactamente 10 dígitos.";

        }


        if (!usuarioEditar && (!password || password.length < 8)) {
            nuevosErrores.password = "La contraseña debe contener al menos 8 caracteres.";

        }



        if (!rol || rol.trim() === "" || rol === "Seleccione") {
            nuevosErrores.rol = "Seleccione un rol para continuar.";

        }

        setError(nuevosErrores);

        if (Object.keys(nuevosErrores).length > 0) {

            return;

        }

        const usuario = {
            idUsuario: usuarioEditar?.idUsuario,
            nombre,
            apellido,
            correo,
            telefono,
            password,
            rol
        };

        try {
            if (usuarioEditar) {

                const id = usuarioEditar.idUsuario;
                await editarUsuario(usuario);
                alert("Usuario actualizado correctamente");


            } else {

                await registrarUsuario(usuario);
                setExitoso(true)
                return;
            }
        } catch (error) {


            console.error("Error al guardar:", error);
            alert("Hubo un error al procesar la solicitud.");
        }




        // Actualizar la lista
        await cargarUsuarios();

        // Limpiar formulario
        setUsuarioEditar(null);
        setNombre("");
        setApellido("");
        setCorreo("");
        setTelefono("");
        setPassword("");
        setRol("");


    }



    return (

        <div className="usuario-pagina">

            <div className="usuario-contenedor-principal">

                {(!rolUsuario || rolUsuario === "ADMINISTRADOR") && (

                    <div className="usuario-card-modulo">

                        <span className="usuario-etiqueta">
                            MÓDULO USUARIOS
                        </span>

                        <h1>Gestión de Usuarios</h1>

                        <p>
                            Registra, consulta, actualiza y administra
                            la información de los usuarios del sistema.
                        </p>
                        {desdeDashboard && (

                            <button className="usuario-btn-regresar"
                                onClick={() => navigate("/Dashboard")}>
                                Volver
                            </button>
                        )}


                    </div>


                )}


                {(!rolUsuario || rolUsuario === "ADMINISTRADOR") && (
                    <form
                        className="usuario-formulario-caja"
                        onSubmit={guardar}
                    >


                        <h2>Información del Usuario</h2>


                        <div className="usuario-form-grid">

                            {/* NOMBRE */}

                            <div className="usuario-form-group">

                                <label>Nombre</label>

                                <input
                                    type="text"
                                    value={nombre}
                                    onChange={(e) => setNombre(e.target.value)}
                                    placeholder="Ingrese el nombre"


                                />

                                {error.nombre && (
                                    <div className="usuario-mensaje-error">
                                        ! {error.nombre}
                                    </div>
                                )}


                            </div>



                            {/* APELLIDO */}
                            <div className="usuario-form-group">

                                <label>Apellido</label>

                                <input
                                    type="text"
                                    value={apellido}
                                    onChange={(e) => setApellido(e.target.value)}
                                    placeholder="Ingrese el apellido"

                                />

                                {error.apellido && (
                                    <div className="usuario-mensaje-error">
                                        ! {error.apellido}
                                    </div>
                                )}

                            </div>


                            {/* CORREO */}
                            <div className="usuario-form-group">

                                <label>Correo Electrónico</label>

                                <input
                                    type="email"
                                    value={correo}
                                    onChange={(e) => setCorreo(e.target.value)}
                                    placeholder="Ingrese el correo"

                                />
                                {error.correo && (
                                    <div className="usuario-mensaje-error">
                                        ! {error.correo}
                                    </div>
                                )}


                            </div>


                            {/* TELÉFONO */}
                            <div className="usuario-form-group">

                                <label>Teléfono</label>

                                <input
                                    type="tel"
                                    value={telefono}
                                    maxLength={10}
                                    onChange={(e) => setTelefono(e.target.value)}
                                    placeholder="Ingrese el teléfono"

                                />

                                {error.telefono && (
                                    <div className="usuario-mensaje-error">
                                        ! {error.telefono}
                                    </div>
                                )}

                            </div>


                            {/* CONTRASEÑA */}

                            {!usuarioEditar && (
                                <div className="usuario-form-group">

                                    <label>Contraseña</label>

                                    <input
                                        type="password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        placeholder="Ingrese una nueva  contraseña"
                                        maxLength={8}

                                    />

                                    {error.password && (
                                        <div className="usuario-mensaje-error">
                                            ! {error.password}
                                        </div>
                                    )}

                                </div>
                            )}


                            {/* ROL */}
                            <div className="usuario-form-group">

                                <label>Rol</label>

                                <select
                                    value={rol}
                                    onChange={(e) => setRol(e.target.value)}
                                >

                                    <option value="">
                                        Seleccione un Rol
                                    </option>

                                    <option value="ADMINISTRADOR">
                                        ADMINISTRADOR
                                    </option>

                                    <option value="OPERARIO">
                                        OPERARIO
                                    </option>

                                    <option value="JEFE">
                                        JEFE
                                    </option>

                                </select>

                                {error.rol && (
                                    <div className="usuario-mensaje-error">
                                        ! {error.rol}
                                    </div>
                                )}


                            </div>



                        </div>


                        {exitoso && (
                            <div className="alerta-exito">
                                <p>¡Registro exitoso! Bienvenido a EcoVida 🌱</p>
                                <button
                                    onClick={() => {
                                        setExitoso(false)
                                        navigate("/login");
                                    }}>Aceptar

                                </button>
                            </div>
                        )}




                        {/* BOTONES */}

                        <div className="usuario-form-buttons">

                            <button
                                type="submit"
                                className="usuario-btn-guardar"
                            >
                                Registrar usuario
                            </button>


                            <button
                                type="button"
                                className="usuario-btn-limpiar"
                                onClick={() => {
                                    setNombre("");
                                    setApellido("");
                                    setCorreo("");
                                    setTelefono("");
                                    setPassword("");
                                    setRol("");
                                    setError
                                }}
                            >
                                Limpiar formulario
                            </button>

                        </div>


                    </form>
                )}


                {/* LISTA */}
                <ListaUsuario

                    usuarios={usuarios}
                    setUsuarios={setUsuarios}
                    setUsuarioEditar={setUsuarioEditar}
                    cargarUsuarios={cargarUsuarios}
                    rolUsuario={rolUsuario}

                />




            </div>

        </div>

    );
}

export default Formulario;