// Importa el modo estricto de React
import { StrictMode } from "react";

// Permite renderizar la aplicación en el navegador
import { createRoot } from "react-dom/client";

// Importa BrowserRouter para manejar las rutas
import { BrowserRouter } from "react-router-dom";

// Componente principal
import App from "./App.jsx";

// Estilos de Leaflet
import "leaflet/dist/leaflet.css";



// Busca el elemento con id="root" del index.html
createRoot(document.getElementById("root")).render(

  <StrictMode>

    {/* Activa React Router en toda la aplicación */}
    <BrowserRouter>

      {/* Carga la aplicación */}
      <App />

    </BrowserRouter>

  </StrictMode>

);