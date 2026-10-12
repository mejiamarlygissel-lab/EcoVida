import clienteAxios from "./axiosConfig.js";

// Guarda una nueva clasificación
export const guardarClasificacion = (clasificacion) => {
    return clienteAxios.post('/api/clasificacion', clasificacion);
};

// Actualiza una clasificación existente
export const actualizarClasificacion = (clasificacion) => {
    return clienteAxios.put(`/api/clasificacion/${clasificacion.idClasificacion}`, clasificacion);
};

// Lista todas las clasificaciones
export const listarClasificaciones = async () => {
    const respuesta = await clienteAxios.get('/api/clasificacion');
    return respuesta.data;
};

// Elimina una clasificación por su ID
export const eliminarClasificacion = (id) => {
    return clienteAxios.delete(`/api/clasificacion/${id}`);
};