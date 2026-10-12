import React, { useState, useEffect } from 'react';
import "./DashboardInicio.css";
import { Link } from 'react-router-dom';
import {
    FaRecycle,
    FaClipboardList,
    FaChartLine,
    FaUsers

} from "react-icons/fa";

import { Sun, Moon } from "lucide-react";
import { ClipboardList, Tags, RefreshCw } from "lucide-react";


import { listarUsuarios } from "../services/UsuarioService";
import { listarRecolecciones } from "../services/RecoleccionService";
import { listarClasificaciones } from "../services/ClasificacionService";
import { listarSeguimiento } from "../services/SeguimientoResiduoService";
import { buscarUsuarioPorCorreo } from "../services/UsuarioService";
import { obtenerClima } from '../services/ClimaService';

function DashboardInicio() {


    const [totalUsuarios, setTotalUsuarios] = useState(0);
    const [totalRecolecciones, setTotalRecolecciones] = useState(0);
    const [totalClasificaciones, setTotalClasificaciones] = useState(0);
    const [totalSeguimientos, setTotalSeguimientos] = useState(0);
    const correoUsuario = localStorage.getItem("correo");
    const [actividadReciente, setActividadReciente] = useState([]);


    const [recoleccionesPendientes, setRecoleccionesPendientes] = useState(0);
    const [clasificacionesPendientes, setClasificacionesPendientes] = useState(0);
    const [seguimientosEnProceso, setSeguimientosEnProceso] = useState(0);
    const [clima, setClima] = useState(null);

    const [rolUsuario, setRolUsuario] = useState("");

    useEffect(() => {
        const obtenerRolDelUsuario = async () => {
            const correo = localStorage.getItem("correo");
            if (correo) {
                try {
                    const response = await buscarUsuarioPorCorreo(correo);
                    const rolDelServidor = response.data.rol; // O como se llame la propiedad en tu backend
                    if (rolDelServidor) {
                        setRolUsuario(rolDelServidor.toUpperCase());
                        localStorage.setItem("rolUsuario", rolDelServidor); // Lo guardamos para futuras ocasiones
                    }
                } catch (error) {
                    console.error("No se pudo obtener el rol del usuario", error);
                }
            }
        };

        obtenerRolDelUsuario();
        cargarEstadisticas();
    }, []);

    const cargarEstadisticas = async () => {
        try {
            const resUsuarios = await listarUsuarios();
            const resRecolecciones = await listarRecolecciones();
            const resClasificaciones = await listarClasificaciones();
            const resSeguimientos = await listarSeguimiento();

            const usuarios = Array.isArray(resUsuarios) ? resUsuarios : (resUsuarios?.data || []);
            const recolecciones = Array.isArray(resRecolecciones) ? resRecolecciones : (resRecolecciones?.data || []);
            const clasificaciones = Array.isArray(resClasificaciones) ? resClasificaciones : (resClasificaciones?.data || []);
            const seguimientos = Array.isArray(resSeguimientos) ? resSeguimientos : (resSeguimientos?.data || []);

            // ==========================================
            // RECOLECCIONES PENDIENTES DE CLASIFICACIÓN
            // ==========================================
            const idsRecoleccionesClasificadas = clasificaciones.map(
                (c) => c.recoleccion?.idRecoleccion
            );

            const pendientesClasificacion = recolecciones.filter(
                (r) => !idsRecoleccionesClasificadas.includes(r.idRecoleccion)
            );

            setRecoleccionesPendientes(pendientesClasificacion.length);

            // ==========================================
            // CLASIFICACIONES PENDIENTES DE SEGUIMIENTO
            // ==========================================
            const idsClasificacionesConSeguimiento = seguimientos.map(
                (s) => s.clasificacion?.idClasificacion
            );

            const pendientesSeguimiento = clasificaciones.filter(
                (c) => !idsClasificacionesConSeguimiento.includes(c.idClasificacion)
            );

            setClasificacionesPendientes(pendientesSeguimiento.length);

            // ==========================================
            // SEGUIMIENTOS EN PROCESO Y TOTALES GENERALES
            // ==========================================
            setSeguimientosEnProceso(seguimientos.length);

            
            setTotalUsuarios(usuarios.length);
            setTotalRecolecciones(recolecciones.length);
            setTotalClasificaciones(clasificaciones.length);
            setTotalSeguimientos(seguimientos.length);

        } catch (error) {
            console.error("Error al cargar estadísticas:", error);
        }
    




    };

    useEffect(() => {
        const climaGuardado = sessionStorage.getItem("clima");

        if (climaGuardado) {
            setClima(JSON.parse(climaGuardado));
            return;
        }

        navigator.geolocation.getCurrentPosition(async (position) => {
            const lat = position.coords.latitude;
            const lon = position.coords.longitude;

            try {
                const respuesta = await obtenerClima(lat, lon);

                setClima(respuesta);

                sessionStorage.setItem(
                    "clima",
                    JSON.stringify(respuesta)
                );

            } catch (error) {
                console.error("Error al obtener clima", error);
            }
        });
    }, []);

    console.log("DATOS DEL CLIMA:", clima);
    console.log("IS_DAY:", clima?.current?.is_day);



    return (

        <div className="dashboard-inicio">

            <section className="welcome">

                <h1>
                    Bienvenido a EcoVida 🌱
                </h1>


                <p>
                    Sistema de Gestión de Residuos Reciclables
                </p>

            </section>


            <h3 className="section-title">Indicadores principales</h3>

            <section className="cards">

                <div className="card">
                    <div className="card-header-content">
                        <FaUsers className="card-icon" />
                        <h3>Usuarios</h3>
                    </div>
                    <p>Total de usuarios registrados</p>
                    <strong>{totalUsuarios}</strong>
                </div>

                <div className="card">
                    <div className="card-header-content">
                        <FaRecycle className="card-icon" />
                        <h3>Recolecciones</h3>
                    </div>
                    <p>Total de recolecciones</p>
                    <strong>{totalRecolecciones}</strong>
                </div>

                <div className="card">
                    <div className="card-header-content">
                        <FaClipboardList className="card-icon" />
                        <h3>Clasificaciones</h3>
                    </div>
                    <p>Total de clasificaciones</p>
                    <strong>{totalClasificaciones}</strong>
                </div>

                <div className="card">
                    <div className="card-header-content">
                        <FaChartLine className="card-icon" />
                        <h3>Seguimientos</h3>
                    </div>
                    <p>Total de seguimientos</p>
                    <strong>{totalSeguimientos}</strong>
                </div>

            </section>



            <h3 className="section-title">Ingresa directamente a los módulos disponibles de su rol</h3>

            <section className="cards-dos">

                {(rolUsuario === "ADMINISTRADOR" || rolUsuario === "JEFE") && (
                    <div className="targeta">

                        <FaUsers className="card-icon" />
                        <h3>Usuarios</h3>
                        <p>Consultar y administrar los usuarios registrados</p>

                        <Link to="/Usuarios"
                        state={{ desdeDashboard: true }}
                        
                        >Abrir modulo</Link>


                    </div>
                )}





                <div className="targeta">
                    <FaRecycle className="card-icon" />
                    <h3>Recolecciones</h3>
                    <p>Consultar y guardar recolecciones</p>

                    <Link to="/recolecciones"
                     state={{ desdeDashboard: true }}
                    
                    >Abrir modulo</Link>
                    
                </div>

                <div className="targeta">
                    <FaClipboardList className="card-icon" />
                    <h3>Clasificaciones</h3>
                    <p>Consultar y Gestionar clasificaciones</p>

                    <Link to="/Clasificacion"
                    state={{ desdeDashboard: true }}
                    
                    >Abrir modulo</Link>
                </div>

                <div className="targeta">
                    <FaChartLine className="card-icon" />
                    <h3>Seguimientos</h3>
                    <p>Gestionar el seguimiento de cada residuo</p>

                    <Link to="/seguimiento"
                    state={{desdeDashboard:true}}
                    
                    >Abrir modulo</Link>
                </div>

            </section>


            <h2 className='clima-titulo'>Clima</h2>


            <div className='clima-targeta'>

                <span className="icono-sol">
                    {new Date().getHours() >= 6 && new Date().getHours() < 18
                        ? <Sun className='sol' size={40} />
                        : <Moon className='luna' size={40} />
                    }

                </span>

                <h3> Clima actualmente </h3>

                {clima ? (

                    <>
                        <p>Temperatura: {clima.current.temperature_2m}°C</p>
                        <p>Humedad: {clima.current.relative_humidity_2m}%</p>

                    </>

                ) : (


                    <p>Consultando el clima...</p>

                )}

            </div>

            {(rolUsuario === "OPERARIO") && (


                <section className="estado-proceso">

                    <h2>Estado del proceso</h2>

                    <div className="avisos">





                        <div className="aviso">
                            <ClipboardList />
                            <div className="aviso-texto">
                                <h3>Recolecciones pendientes</h3>
                                <strong>{recoleccionesPendientes}</strong>
                                <p>Pendientes de clasificación</p>
                            </div>
                            <Link to="/Clasificacion">Revisar →</Link>
                        </div>





                        <div className="aviso">
                            <Tags />
                            <div className="aviso-texto">
                                <h3>Pendientes de seguimiento</h3>
                                <strong>{clasificacionesPendientes}</strong>
                                <p>Clasificaciones pendientes</p>
                            </div>
                            <Link to="/seguimiento">Revisar →</Link>
                        </div>






                        <div className="aviso">
                            <RefreshCw />
                            <div className="aviso-texto">
                                <h3>Seguimientos en proceso</h3>
                                <strong>{seguimientosEnProceso}</strong>
                                <p>Seguimientos registrados</p>
                            </div>
                            <Link to="/seguimiento">Ver →</Link>
                        </div>



                    </div>
                </section>
            )}

        </div >
    );


};

export default DashboardInicio;