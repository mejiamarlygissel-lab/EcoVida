import { useState, useEffect } from "react";
import Navbar from "../components/Navbar";
import "../pages/Dashboard.css";
import { Link, useNavigate } from "react-router-dom";
import {
    FaUser,
    FaHome,
    FaSignInAlt,
    FaSignOutAlt,
    FaBars,
    FaUserPlus,
    FaRecycle,
    FaThLarge,
    FaMapMarkerAlt,
    FaChartBar,

} from "react-icons/fa";
import Index from "../pages/Index";
import Formulario from "../components/Formulario";
import FormularioRecoleccion from "../components/FormularioRecoleccion";
import FormularioClasificacion from "../components/FormularioClasificacion";
import SeguimientoResiduo from "../components/SeguimientoResiduo";
import { rectangle } from "leaflet";
import DashboardInicio from "../components/DashboardInicio";
import Perfil from "../pages/Perfil";
import { buscarUsuarioPorCorreo } from "../services/UsuarioService";
import Reporte from "./Reporte";




function Dashboard() {


    const [modulo, setModulo] = useState("inicio");
    const navigate = useNavigate();
    const [nombre, setNombre] = useState("");
    const [apellido, setApellido] = useState("");


    const cerrarPagina = () => {
        localStorage.clear();
        sessionStorage.clear();
        navigate("/",{replace:true});
    };


    useEffect(() => {

        const obtenerDatosPersonal = async () => {

            try {


                const correo = localStorage.getItem("correo");

                console.log("CORREO:", correo);

                if (!correo) {
                    console.log("NO EXISTE CORREO");
                    return;
                }

                const response = await buscarUsuarioPorCorreo(correo);

                console.log("DATOS USUARIO:", response.data);

                setNombre(response.data.nombre);
                setApellido(response.data.apellido);

            } catch (error) {

                console.error("ERROR USUARIO:", error);

            }

        };

        obtenerDatosPersonal();

    }, []);



    const mostrarModulos = () => {

        switch (modulo) {


            case "inicio":
                return <DashboardInicio />;



            case "recoleciones":
                return <FormularioRecoleccion />;


            case "clasificacion":
                return <FormularioClasificacion />;


            case "seguimiento":
                return <SeguimientoResiduo />;


            case "perfil":
                return <Perfil />;

            case "reporte":
                return <Reporte />;

            default:
                return <DashboardInicio />;

        }



    };

    return (

        <div className="dashboard-container">

            {/* =========================
            MENÚ LATERAL
        ========================= */}

            <aside className="sidebar">

                <h2 className="logo">
                    EcoVida 🌱
                </h2>

                <nav>

                    <a onClick={() => setModulo("inicio")}>
                        <FaHome />
                        Inicio
                    </a>



                    <a onClick={() => setModulo("recoleciones")}>
                        <FaRecycle />
                        Recolección
                    </a>

                    <a onClick={() => setModulo("clasificacion")}>
                        <FaThLarge />
                        Clasificación
                    </a>

                    <a onClick={() => setModulo("seguimiento")}>
                        <FaMapMarkerAlt />
                        Seguimiento Residuo
                    </a>

                    <a onClick={() => setModulo("reporte")}>
                        <FaChartBar />
                        Reporte
                    </a>

                    <a onClick={() => setModulo("perfil")}>
                        <FaUser />
                        Perfil
                    </a>

                </nav>

            </aside>


            {/* =========================
            CONTENIDO PRINCIPAL
        ========================= */}

            <main className="main-content">

                {/* HEADER */}

                <header className="topbar">

                    <h3>
                        Bienvenido(a): {nombre} {apellido}  👋
                    </h3>

                    <div>

                        <button onClick={cerrarPagina}>
                            Cerrar sesión

                        </button>

                    </div>

                </header>


                {/* =========================
                AQUÍ APARECEN LOS MÓDULOS
            ========================= */}

                <section className="dashboard-content">

                    {mostrarModulos()}

                </section>

            </main>

        </div>

    );


};

export default Dashboard;