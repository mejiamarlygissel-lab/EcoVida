import clienteAxios from "./axiosConfig.js";

// Guarda un nuevo seguimiento
export const guardarSeguimiento = (seguimientoResiduo) => {
    return clienteAxios.post('/api/seguimiento', seguimientoResiduo);
};

// Actualiza un seguimiento existente
export const actualizarSeguimiento = (seguimientoResiduo) => {
    return clienteAxios.put(
        `/api/seguimiento/${seguimientoResiduo.idSeguimientoResiduo}`, 
        seguimientoResiduo
    );
};

// Lista todos los seguimientos
export const listarSeguimiento = async () => {
    // Le quitamos el parámetro que no se usaba y devolvemos la data limpia
    const respuesta = await clienteAxios.get('/api/seguimiento');
    return respuesta.data; 
};

// Elimina un seguimiento por su ID
export const eliminarSeguimiento = (id) => {
    return clienteAxios.delete(`/api/seguimiento/${id}`);
};