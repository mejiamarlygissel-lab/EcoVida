import clienteAxios from "./axiosConfig.js";

const API = '/api/recoleccion';


// Guarda una nueva recolección
export const guardarRecoleccion = (recoleccion) => {
    return clienteAxios.post(API, recoleccion);
};


// Actualiza una recolección existente
export const editarRecoleccion = (recoleccion) => {
    return clienteAxios.put(
        `${API}/${recoleccion.idRecoleccion}`,
        recoleccion
    );
};


// Lista todas las recolecciones
export const listarRecolecciones = () => {
    return clienteAxios.get(API);
};


// Elimina una recolección por ID
export const eliminarRecoleccion = (id) => {
    return clienteAxios.delete(`${API}/${id}`);
};