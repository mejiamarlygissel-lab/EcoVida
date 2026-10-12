import { Route, Routes } from "react-router-dom";



import Index from "./pages/Index";
import Login from "./pages/Login";
import Perfil from "./pages/Perfil";

import Formulario from "./components/Formulario";
import FormularioClasificacion from "./components/FormularioClasificacion";
import FormularioRecoleccion from "./components/FormularioRecoleccion";
import ListaRecoleccion from "./components/ListaRecoleccion";
import SeguimientoResiduo from "./components/SeguimientoResiduo";
import Servicios from "./components/Servicios";
import Dashboard from "./pages/Dashboard";
import Reporte from "./pages/Reporte";

import ListaClasificacion from "./components/ListaClasificacion";
import protecionRutas from "./components/ProtecionRutas";

function App() {

    return (

        <>


            <Routes>

                <Route path="/" element={<Index />} />

                <Route path="/index" element={<Index />} />

                <Route path="/login" element={<Login />} />

                <Route path="/usuarios" element={<Formulario />} />

                <Route path="/formulario" element={<Formulario />} />

                <Route path="/servicios" element={<Servicios />} />

                <Route path="/perfil" element={<Perfil />} />

                <Route path="dashboard" element={<protecionRutas><Dashboard /> </protecionRutas>} />



                <Route path="recolecciones"

                    element={<FormularioRecoleccion />}

                />

                <Route path="clasificacion"
                    element={<FormularioClasificacion />}

                />

                <Route path="seguimiento"

                    element={<SeguimientoResiduo />}
                />

                <Route path="reporte"

                    element={<Reporte />}
                />

                <Route path="lista-recoleccion"

                    element={<ListaRecoleccion />}
                />


                <Route path="ListaClasificacion"

                    element={<ListaClasificacion />}
                />

            </Routes>


        </>

    );

}

export default App;