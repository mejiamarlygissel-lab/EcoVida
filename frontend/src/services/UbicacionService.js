import { API_BASE_URL } from "../config/api";
import axios from "axios";

const API = `${API_BASE_URL}/api/publica/ubicacion`;


export const obtenerUbicacion = (lat, lon) =>
    axios.get(`${API}?lat=${lat}&lon=${lon}`);