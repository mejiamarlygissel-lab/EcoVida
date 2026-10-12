import React, { useState } from "react";
import Axios from "axios";
import '../components/Navbar.css';
import { Link } from "react-router-dom";

import {
    FaUser,
    FaHome,
    FaSignInAlt,
    FaSignOutAlt,
    FaBars,
    FaUserPlus,
} from "react-icons/fa";

function Navbar() {
    const [menuAbierto, setMenuAbierto] = useState(false);

    return (
        <nav className="Navbar">
            <div>
                <h2 className="titulo-ecovida">
                    EcoVida
                </h2>

                {/* Contenedor principal con los eventos de mouse */}
                <div
                    className="menu-container"
                    onMouseEnter={() => setMenuAbierto(true)}
                    onMouseLeave={() => setMenuAbierto(false)}
                >
                    {/* Botón o icono que despliega el menú al pasar el mouse */}
                    <button className="menu-btn">
                        <FaBars /> Menú
                    </button>

                    {/* El menú desplegable condicional */}
                    {menuAbierto && (
                        <div className="menu">
                            <ul>
                                <li>
                                    <Link to="/Index" onClick={() => setMenuAbierto(false)}>
                                        <FaHome /> Inicio
                                    </Link>
                                </li>
                                <li>
                                    <Link to="/usuarios" onClick={() => setMenuAbierto(false)}>
                                        <FaUserPlus /> Registrarse
                                    </Link>
                                </li>
                                <li>
                                    <Link to="/Login" onClick={() => setMenuAbierto(false)}>
                                        <FaSignOutAlt /> Cerrar sesión
                                    </Link>
                                </li>
                            </ul>
                        </div>
                    )}
                </div>
            </div>
        </nav>
    );
}

export default Navbar;