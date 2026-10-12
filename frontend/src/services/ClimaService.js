import { API_BASE_URL } from "../config/api";
import axios from "axios";

const API = `${API_BASE_URL}/api/publica`;


export const obtenerClima = async (lat, lon) => {

    const respuesta = await axios.get(
        `${API}/clima?lat=${lat}&lon=${lon}`
        
    );

    console.log("Consultando clima:", lat, lon);

    return respuesta.data;
    
};