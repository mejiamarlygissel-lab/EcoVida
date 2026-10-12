import clienteAxios from "./axiosConfig.js";


export const registrarUsuario = (usuario) => {
    return clienteAxios.post('/api/usuarios/registro', usuario);
};


export const login = (usuario) => {
    return clienteAxios.post('/api/auth/login', usuario);
    
};


export const listarUsuarios = async () => {
    try {
        const respuesta = await clienteAxios.get('/api/usuarios');
        return respuesta.data;
    } catch (error) {
        console.error("Error al obtener usuarios:", error);
        throw error;
    }
};


export const editarUsuario = async (usuario) => {
    try {
        const respuesta = await clienteAxios.put(
            `/api/usuarios/${usuario.idUsuario}`,
            usuario
        );

        return respuesta.data;
    } catch (error) {
        console.error("Error al editar usuario:", error);
        throw error;
    }
};



// Eliminar usuario
export const eliminarUsuario = async (id) => {
    try {
       
        const respuesta = await clienteAxios.delete(`/api/usuarios/${id}`);
        return respuesta.data;
    } catch (error) {
        console.error("Error al eliminar usuarios", error);
        throw error;
    }
};



export const buscarUsuarioPorCorreo = (correo) => {
    return clienteAxios.get(
        `/api/usuarios/correo/${encodeURIComponent(correo)}`
    );
};