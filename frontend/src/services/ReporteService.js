import { API_BASE_URL } from "../config/api";
import axios from "axios";

const API = `${API_BASE_URL}/api/reportes`;


// Obtiene el token JWT almacenado después del login
const obtenerToken = () => {
    return localStorage.getItem("token");
};




export const generarReportePorIdUsuario = async (idUsuario) => {

    const token = localStorage.getItem("token");

    return await axios.get(`${API}/usuario/${idUsuario}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};


